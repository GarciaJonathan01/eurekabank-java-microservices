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
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EurekaRestService(private val context: Context? = null) {
    companion object {
        private const val TAG = "EurekaRestService"
        private const val PREF_NAME = "EurekaServicePrefs"
        private const val KEY_SERVER_IP = "server_ip"
        private const val DEFAULT_IP = "127.0.0.1"
        private const val PORT = "8080"
        
        private const val PATH_AUTH = "/WSEurekaBank_Autenticacion_Rest/resources/autenticacion/login"
        private const val PATH_CUENTAS = "/WSEurekaBank_Cuentas_Rest/resources/corebancario/movimientos/"
        private const val PATH_OPERACIONES_DEP = "/WSEurekaBank_Operaciones_Rest/resources/corebancario/deposito"
        private const val PATH_OPERACIONES_RET = "/WSEurekaBank_Operaciones_Rest/resources/corebancario/retiro"
        private const val PATH_TRANSF = "/WSEurekaBank_Transferencias_Rest/resources/corebancario/transferencia"

        private const val MONOLITO_BASE = "/WSEurekaBank_Restfull_Java_G5/resources/corebancario"

        private const val CONNECT_TIMEOUT = 10L
        private const val READ_TIMEOUT = 10L
        private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
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

    interface RestCallback<T> {
        fun onSuccess(result: T)
        fun onError(error: String)
    }

    fun autenticar(usuario: String, clave: String, callback: RestCallback<Boolean>) {
        AutenticarTask(this, callback).execute(usuario, clave)
    }

    fun leerMovimientos(cuenta: String, callback: RestCallback<List<Movimiento>>) {
        LeerMovimientosTask(this, callback).execute(cuenta)
    }

    fun registrarDeposito(cuenta: String, importe: Double, callback: RestCallback<Boolean>) {
        RegistrarDepositoTask(this, callback).execute(cuenta, importe.toString())
    }

    fun registrarRetiro(cuenta: String, importe: Double, callback: RestCallback<Boolean>) {
        RegistrarRetiroTask(this, callback).execute(cuenta, importe.toString())
    }

    fun registrarTransferencia(
        cuentaOrigen: String,
        cuentaDestino: String,
        importe: Double,
        callback: RestCallback<Boolean>
    ) {
        RegistrarTransferenciaTask(this, callback).execute(cuentaOrigen, cuentaDestino, importe.toString())
    }

    private fun executeRest(fullUrl: String, method: String = "GET", body: RequestBody? = null): String {
        Log.d(TAG, "Ejecutando REST: $method $fullUrl")
        val reqBuilder = Request.Builder().url(fullUrl).addHeader("Accept", "application/json")
        val finalBody = if (method == "POST" || method == "PUT") (body ?: "{}".toRequestBody(JSON_MEDIA_TYPE)) else body
        reqBuilder.method(method, finalBody)
        val resp = client.newCall(reqBuilder.build()).execute()
        if (!resp.isSuccessful) {
            throw Exception("HTTP Error ${resp.code}")
        }
        return resp.body?.string() ?: ""
    }

    private fun executeRestWithFallback(urlMs: String, urlMono: String, method: String = "GET", body: RequestBody? = null): String {
        return try {
            executeRest(urlMs, method, body)
        } catch (e: Exception) {
            Log.w(TAG, "Fallo MS en $urlMs, intentando monolito $urlMono...")
            executeRest(urlMono, method, body)
        }
    }

    private fun parseMovimientosResponse(jsonResponse: String): List<Movimiento> {
        val movimientos = mutableListOf<Movimiento>()
        try {
            val jsonArray = JSONArray(jsonResponse)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val movimiento = Movimiento()
                movimiento.cuencodigo = jsonObject.optString("cuenta", "")
                movimiento.movinumero = jsonObject.optInt("nromov", 0)
                movimiento.tipocodigo = jsonObject.optString("tipo", "")
                movimiento.moviimporte = jsonObject.optDouble("importe", 0.0)
                movimiento.cuenreferencia = jsonObject.optString("referencia", "")
                val fechaStr = jsonObject.optString("fecha", "")
                if (fechaStr.isNotEmpty()) {
                    try {
                        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        movimiento.movifecha = format.parse(fechaStr)
                    } catch (e: Exception) {}
                }
                movimientos.add(movimiento)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parseando JSON movimientos", e)
        }
        return movimientos
    }

    private class AutenticarTask(
        private val service: EurekaRestService,
        private val callback: RestCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val usuario = params[0] ?: return Pair(false, "Usuario nulo")
                val clave = params[1] ?: return Pair(false, "Clave nula")
                val url = service.getUrl(PATH_AUTH) + "?usuario=${URLEncoder.encode(usuario, "UTF-8")}&clave=${URLEncoder.encode(clave, "UTF-8")}"
                val resp = service.executeRest(url, "POST")
                val json = JSONObject(resp)
                val estado = json.optInt("estado", -1)
                Pair(estado == 1, null)
            } catch (e: Exception) {
                Pair(false, "Microservicio REST de Autenticación fuera de línea o falló: ${e.message}")
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
        private val service: EurekaRestService,
        private val callback: RestCallback<List<Movimiento>>
    ) : AsyncTask<String, Void, Pair<List<Movimiento>?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<List<Movimiento>?, String?> {
            return try {
                val cuenta = params[0] ?: return Pair(null, "Cuenta no válida")
                val urlMs = service.getUrl(PATH_CUENTAS) + cuenta
                val urlMono = service.getUrl(MONOLITO_BASE) + "/movimientos/" + cuenta
                val response = service.executeRestWithFallback(urlMs, urlMono, "GET")
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
        private val service: EurekaRestService,
        private val callback: RestCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val cuenta = params[0] ?: return Pair(false, "Cuenta no válida")
                val importe = params[1]?.toDoubleOrNull() ?: return Pair(false, "Importe no válido")
                val query = "?cuenta=$cuenta&importe=$importe"
                val response = service.executeRestWithFallback(service.getUrl(PATH_OPERACIONES_DEP) + query, service.getUrl(MONOLITO_BASE) + "/deposito" + query, "POST")
                val json = JSONObject(response)
                Pair(json.optInt("estado", -1) == 1, null)
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
        private val service: EurekaRestService,
        private val callback: RestCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val cuenta = params[0] ?: return Pair(false, "Cuenta no válida")
                val importe = params[1]?.toDoubleOrNull() ?: return Pair(false, "Importe no válido")
                val query = "?cuenta=$cuenta&importe=$importe"
                val response = service.executeRestWithFallback(service.getUrl(PATH_OPERACIONES_RET) + query, service.getUrl(MONOLITO_BASE) + "/retiro" + query, "POST")
                val json = JSONObject(response)
                Pair(json.optInt("estado", -1) == 1, null)
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
        private val service: EurekaRestService,
        private val callback: RestCallback<Boolean>
    ) : AsyncTask<String, Void, Pair<Boolean?, String?>>() {
        override fun doInBackground(vararg params: String?): Pair<Boolean?, String?> {
            return try {
                val cuentaOrigen = params[0] ?: return Pair(false, "Cuenta origen no válida")
                val cuentaDestino = params[1] ?: return Pair(false, "Cuenta destino no válida")
                val importe = params[2]?.toDoubleOrNull() ?: return Pair(false, "Importe no válido")
                val query = "?cuentaOrigen=$cuentaOrigen&cuentaDestino=$cuentaDestino&importe=$importe"
                val response = service.executeRestWithFallback(service.getUrl(PATH_TRANSF) + query, service.getUrl(MONOLITO_BASE) + "/transferencia" + query, "POST")
                val json = JSONObject(response)
                Pair(json.optInt("estado", -1) == 1, null)
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
