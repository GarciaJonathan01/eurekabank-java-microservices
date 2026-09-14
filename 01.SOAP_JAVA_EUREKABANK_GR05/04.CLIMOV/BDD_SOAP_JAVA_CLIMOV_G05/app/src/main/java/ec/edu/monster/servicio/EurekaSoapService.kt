package ec.edu.monster.servicio

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import ec.edu.monster.modelo.Movimiento
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EurekaSoapService(private val context: Context? = null) {
    companion object {
        private const val TAG = "EurekaSoapService"
        private const val NAMESPACE = "http://servicios.monster.edu.ec/"
        private const val SOAP_ACTION_PREFIX = "http://servicios.monster.edu.ec/"
        private const val PREF_NAME = "EurekaServicePrefs"
        private const val KEY_SERVER_IP = "server_ip"
        private const val DEFAULT_IP = "127.0.0.1"
        private const val PORT = "8080"
        
        private const val PATH_AUTH = "/WS_EUREKABANK_AUTENTICACION/AutenticacionService"
        private const val PATH_CUENTAS = "/WS_EUREKABANK_CUENTAS/CuentasService"
        private const val PATH_OPERACIONES = "/WS_EUREKABANK_OPERACIONES/OperacionesService"
        private const val PATH_TRANSFERENCIAS = "/WS_EUREKABANK_TRANSFERENCIAS/TransferenciasService"
        private const val PATH_MONOLITO = "/WS_EUREKABANK_SERVICIO/EurekaService"

        private const val COD_EMP_DEFAULT = "0001"
        private const val CONNECT_TIMEOUT = 10L
        private const val READ_TIMEOUT = 10L
        private val SOAP_MEDIA_TYPE = "text/xml; charset=utf-8".toMediaType()
    }

    private val client: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, "OkHttp: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()
    }
    
    private fun getServerIp(): String {
        return if (context != null) {
            val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            prefs.getString(KEY_SERVER_IP, DEFAULT_IP) ?: DEFAULT_IP
        } else {
            DEFAULT_IP
        }
    }
    
    private fun getUrl(path: String): String {
        return "http://${getServerIp()}:$PORT$path"
    }

    interface SoapCallback<T> {
        fun onSuccess(result: T)
        fun onError(error: String)
    }

    fun autenticar(usuario: String, clave: String, callback: SoapCallback<Boolean>) {
        AutenticarTask(this, callback).execute(usuario, clave)
    }

    fun leerMovimientos(cuenta: String, callback: SoapCallback<List<Movimiento>>) {
        LeerMovimientosTask(this, callback).execute(cuenta)
    }

    fun registrarDeposito(cuenta: String, importe: Double, callback: SoapCallback<Boolean>) {
        RegistrarDepositoTask(this, callback).execute(cuenta, importe.toString())
    }

    fun registrarRetiro(cuenta: String, importe: Double, callback: SoapCallback<Boolean>) {
        RegistrarRetiroTask(this, callback).execute(cuenta, importe.toString())
    }

    fun registrarTransferencia(
        cuentaOrigen: String,
        cuentaDestino: String,
        importe: Double,
        callback: SoapCallback<Boolean>
    ) {
        RegistrarTransferenciaTask(this, callback).execute(cuentaOrigen, cuentaDestino, importe.toString())
    }

    private fun createSoapRequest(methodName: String, vararg params: Pair<String, Any>): String {
        val soapBody = StringBuilder()
        soapBody.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        soapBody.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
        soapBody.append("xmlns:ser=\"$NAMESPACE\">")
        soapBody.append("<soapenv:Header/>")
        soapBody.append("<soapenv:Body>")
        soapBody.append("<ser:$methodName>")
        
        params.forEach { (name, value) ->
            soapBody.append("<$name>")
            soapBody.append(escapeXml(value.toString()))
            soapBody.append("</$name>")
        }
        
        soapBody.append("</ser:$methodName>")
        soapBody.append("</soapenv:Body>")
        soapBody.append("</soapenv:Envelope>")
        return soapBody.toString()
    }

    private fun escapeXml(text: String): String {
        return text.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;")
    }

    private fun executeSoapRequestToPath(pathMs: String, soapBody: String): String {
        val url = getUrl(pathMs)
        Log.d(TAG, "Ejecutando petición SOAP a: $url")
        val requestBody = soapBody.toRequestBody(SOAP_MEDIA_TYPE)
        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "text/xml; charset=utf-8")
            .addHeader("Accept", "text/xml")
            .post(requestBody)
            .build()
            
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw Exception("HTTP Error ${response.code}")
        }
        return response.body?.string() ?: ""
    }

    private fun executeSoapWithFallback(pathMs: String, soapBody: String): String {
        return try {
            executeSoapRequestToPath(pathMs, soapBody)
        } catch (e: Exception) {
            Log.w(TAG, "Fallo microservicio en $pathMs, intentando monolito...")
            executeSoapRequestToPath(PATH_MONOLITO, soapBody)
        }
    }

    private fun parseMovimientosResponse(xmlResponse: String): List<Movimiento> {
        val movimientos = mutableListOf<Movimiento>()
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(StringReader(xmlResponse))

            var eventType = parser.eventType
            var currentMovimiento: Movimiento? = null
            var currentTag: String? = null
            var dentroDeResponse = false

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = parser.name
                        if (tagName == "leerMovimientosResponse" || tagName.endsWith("leerMovimientosResponse")) {
                            dentroDeResponse = true
                        }
                        if (dentroDeResponse) {
                            if (tagName == "movimiento") {
                                if (currentMovimiento == null) currentMovimiento = Movimiento()
                            } else if (currentMovimiento != null) {
                                currentTag = tagName
                            }
                        }
                    }
                    XmlPullParser.TEXT -> {
                        if (currentMovimiento != null && currentTag != null) {
                            val text = parser.text.trim()
                            if (text.isNotEmpty()) {
                                when (currentTag) {
                                    "cuencodigo", "chr_cuencodigo" -> currentMovimiento.cuencodigo = text
                                    "movinumero", "int_movinumero" -> currentMovimiento.movinumero = text.toIntOrNull() ?: 0
                                    "movifecha", "dtt_movifecha" -> {
                                        try {
                                            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                            currentMovimiento.movifecha = format.parse(text)
                                        } catch (e: Exception) {}
                                    }
                                    "emplcodigo", "chr_emplcodigo" -> currentMovimiento.emplcodigo = text
                                    "tipocodigo", "chr_tipocodigo" -> currentMovimiento.tipocodigo = text
                                    "moviimporte", "dec_moviimporte" -> currentMovimiento.moviimporte = text.toDoubleOrNull() ?: 0.0
                                    "cuenreferencia", "chr_cuenreferencia" -> currentMovimiento.cuenreferencia = text
                                }
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        val tagName = parser.name
                        if (tagName == "movimiento" && currentMovimiento != null) {
                            movimientos.add(currentMovimiento)
                            currentMovimiento = null
                        }
                        if (tagName == "leerMovimientosResponse" || tagName.endsWith("leerMovimientosResponse")) {
                            dentroDeResponse = false
                        }
                        currentTag = null
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parseando movimientos XML", e)
        }
        return movimientos
    }

    private class AutenticarTask(
        private val service: EurekaSoapService,
        private val callback: SoapCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val usuario = params[0] ?: return Pair(false, "Usuario nulo")
                val clave = params[1] ?: return Pair(false, "Clave nula")
                val soapBody = service.createSoapRequest("autenticar", "usuario" to usuario, "clave" to clave)
                val resp = service.executeSoapRequestToPath(PATH_AUTH, soapBody)
                if (resp.contains("<empleado>") || resp.contains("<codigo>")) {
                    Pair(true, null)
                } else {
                    Pair(false, "Credenciales incorrectas")
                }
            } catch (e: Exception) {
                Pair(false, "Microservicio de Autenticación fuera de línea o falló: ${e.message}")
            }
        }

        override fun onPostExecute(result: Pair<Boolean?, String?>) {
            if (result.first == true) {
                callback.onSuccess(true)
            } else {
                callback.onError(result.second ?: "Error de autenticación")
            }
        }
    }

    private class LeerMovimientosTask(
        private val service: EurekaSoapService,
        private val callback: SoapCallback<List<Movimiento>>
    ) : AsyncTask<String, Void, Pair<List<Movimiento>?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<List<Movimiento>?, String?> {
            return try {
                val cuenta = params[0] ?: return Pair(null, "Cuenta no válida")
                val soapBody = service.createSoapRequest("leerMovimientos", "cuenta" to cuenta)
                val response = service.executeSoapWithFallback(PATH_CUENTAS, soapBody)
                val movimientos = service.parseMovimientosResponse(response)
                Pair(movimientos, null)
            } catch (e: Exception) {
                Pair(null, e.message ?: "Error desconocido")
            }
        }

        override fun onPostExecute(result: Pair<List<Movimiento>?, String?>) {
            if (result.first != null) {
                callback.onSuccess(result.first!!)
            } else {
                callback.onError(result.second ?: "Error desconocido")
            }
        }
    }

    private class RegistrarDepositoTask(
        private val service: EurekaSoapService,
        private val callback: SoapCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val cuenta = params[0] ?: return Pair(false, "Cuenta no válida")
                val importe = params[1]?.toDoubleOrNull() ?: return Pair(false, "Importe no válido")
                val soapBody = service.createSoapRequest("registrarDeposito", "cuenta" to cuenta, "importe" to importe, "codEmp" to COD_EMP_DEFAULT)
                service.executeSoapWithFallback(PATH_OPERACIONES, soapBody)
                Pair(true, null)
            } catch (e: Exception) {
                Pair(false, e.message ?: "Error desconocido")
            }
        }

        override fun onPostExecute(result: Pair<Boolean?, String?>) {
            if (result.first == true) {
                callback.onSuccess(true)
            } else {
                callback.onError(result.second ?: "Error desconocido")
            }
        }
    }

    private class RegistrarRetiroTask(
        private val service: EurekaSoapService,
        private val callback: SoapCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val cuenta = params[0] ?: return Pair(false, "Cuenta no válida")
                val importe = params[1]?.toDoubleOrNull() ?: return Pair(false, "Importe no válido")
                val soapBody = service.createSoapRequest("registrarRetiro", "cuenta" to cuenta, "importe" to importe, "codEmp" to COD_EMP_DEFAULT)
                service.executeSoapWithFallback(PATH_OPERACIONES, soapBody)
                Pair(true, null)
            } catch (e: Exception) {
                Pair(false, e.message ?: "Error desconocido")
            }
        }

        override fun onPostExecute(result: Pair<Boolean?, String?>) {
            if (result.first == true) {
                callback.onSuccess(true)
            } else {
                callback.onError(result.second ?: "Error desconocido")
            }
        }
    }

    private class RegistrarTransferenciaTask(
        private val service: EurekaSoapService,
        private val callback: SoapCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val cuentaOrigen = params[0] ?: return Pair(false, "Cuenta origen no válida")
                val cuentaDestino = params[1] ?: return Pair(false, "Cuenta destino no válida")
                val importe = params[2]?.toDoubleOrNull() ?: return Pair(false, "Importe no válido")
                val soapBody = service.createSoapRequest("registrarTransferencia", "cuentaOrigen" to cuentaOrigen, "cuentaDestino" to cuentaDestino, "importe" to importe, "codEmp" to COD_EMP_DEFAULT)
                service.executeSoapWithFallback(PATH_TRANSFERENCIAS, soapBody)
                Pair(true, null)
            } catch (e: Exception) {
                Pair(false, e.message ?: "Error desconocido")
            }
        }

        override fun onPostExecute(result: Pair<Boolean?, String?>) {
            if (result.first == true) {
                callback.onSuccess(true)
            } else {
                callback.onError(result.second ?: "Error desconocido")
            }
        }
    }
}
