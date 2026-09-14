package ec.edu.monster.vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ec.edu.monster.servicio.EurekaSoapService
import ec.edu.bd_soap_java.R

class RetiroActivity : AppCompatActivity() {
    private lateinit var etCuenta: TextInputEditText
    private lateinit var etImporte: TextInputEditText
    private lateinit var btnContinuar: MaterialButton
    private lateinit var llError: android.view.View
    private lateinit var tvError: android.widget.TextView

    private lateinit var soapService: EurekaSoapService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retiro)
        
        soapService = EurekaSoapService(this)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        etCuenta = findViewById(R.id.etCuenta)
        etImporte = findViewById(R.id.etImporte)
        btnContinuar = findViewById(R.id.btnContinuar)
        llError = findViewById(R.id.llError)
        tvError = findViewById(R.id.tvError)

        btnContinuar.setOnClickListener {
            val cuenta = etCuenta.text?.toString() ?: ""
            val importeStr = etImporte.text?.toString() ?: ""

            if (cuenta.isEmpty() || importeStr.isEmpty()) {
                mostrarError("Por favor, complete todos los campos")
                return@setOnClickListener
            }

            val importe = importeStr.toDoubleOrNull()
            if (importe == null || importe <= 0) {
                mostrarError("El valor debe ser mayor a $0")
                return@setOnClickListener
            }

            if (importe > 5000) {
                mostrarError("El valor debe ser menor a $5,000.00")
                return@setOnClickListener
            }

            ocultarError()
            btnContinuar.isEnabled = false
            btnContinuar.text = "Procesando..."

            soapService.registrarRetiro(cuenta, importe, object : EurekaSoapService.SoapCallback<Boolean> {
                override fun onSuccess(result: Boolean) {
                    runOnUiThread {
                        Toast.makeText(this@RetiroActivity, "Retiro realizado exitosamente", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }

                override fun onError(error: String) {
                    runOnUiThread {
                        mostrarError(error)
                        btnContinuar.isEnabled = true
                        btnContinuar.text = "Continuar"
                    }
                }
            })
        }
    }

    private fun mostrarError(mensaje: String) {
        tvError.text = mensaje
        llError.visibility = android.view.View.VISIBLE
    }

    private fun ocultarError() {
        llError.visibility = android.view.View.GONE
    }
}

