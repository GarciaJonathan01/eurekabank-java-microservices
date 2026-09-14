package ec.edu.monster.vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ec.edu.monster.servicio.EurekaRestService
import ec.edu.eb_restful_java.R

class DepositoActivity : AppCompatActivity() {
    private lateinit var etCuenta: TextInputEditText
    private lateinit var etImporte: TextInputEditText
    private lateinit var btnAceptar: MaterialButton
    private lateinit var llError: android.view.View
    private lateinit var tvError: android.widget.TextView

    private lateinit var restService: EurekaRestService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposito)
        
        restService = EurekaRestService(this)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        etCuenta = findViewById(R.id.etCuenta)
        etImporte = findViewById(R.id.etImporte)
        btnAceptar = findViewById(R.id.btnAceptar)
        llError = findViewById(R.id.llError)
        tvError = findViewById(R.id.tvError)

        btnAceptar.setOnClickListener {
            val cuenta = etCuenta.text?.toString() ?: ""
            val importeStr = etImporte.text?.toString() ?: ""

            if (cuenta.isEmpty() || importeStr.isEmpty()) {
                mostrarError("Por favor, complete todos los campos")
                return@setOnClickListener
            }

            val importe = importeStr.toDoubleOrNull()
            if (importe == null || importe <= 0) {
                mostrarError("El monto debe ser mayor a $0.")
                return@setOnClickListener
            }

            ocultarError()
            btnAceptar.isEnabled = false
            btnAceptar.text = "Procesando..."

            restService.registrarDeposito(cuenta, importe, object : EurekaRestService.RestCallback<Boolean> {
                override fun onSuccess(result: Boolean) {
                    runOnUiThread {
                        Toast.makeText(this@DepositoActivity, "Depósito realizado exitosamente", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }

                override fun onError(error: String) {
                    runOnUiThread {
                        mostrarError(error)
                        btnAceptar.isEnabled = true
                        btnAceptar.text = "Aceptar"
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



