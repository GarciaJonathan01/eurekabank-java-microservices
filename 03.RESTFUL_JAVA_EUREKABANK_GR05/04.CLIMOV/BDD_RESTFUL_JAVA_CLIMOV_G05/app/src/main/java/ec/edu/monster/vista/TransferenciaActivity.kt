package ec.edu.monster.vista

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ec.edu.monster.servicio.EurekaRestService
import ec.edu.eb_restful_java.R

class TransferenciaActivity : AppCompatActivity() {
    private lateinit var etCuentaOrigen: TextInputEditText
    private lateinit var etCuentaDestino: TextInputEditText
    private lateinit var etImporte: TextInputEditText
    private lateinit var btnRealizarTransferencia: MaterialButton

    private lateinit var restService: EurekaRestService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transferencia)
        
        restService = EurekaRestService(this)

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        etCuentaOrigen = findViewById(R.id.etCuentaOrigen)
        etCuentaDestino = findViewById(R.id.etCuentaDestino)
        etImporte = findViewById(R.id.etImporte)
        btnRealizarTransferencia = findViewById(R.id.btnRealizarTransferencia)

        btnRealizarTransferencia.setOnClickListener {
            val cuentaOrigen = etCuentaOrigen.text?.toString() ?: ""
            val cuentaDestino = etCuentaDestino.text?.toString() ?: ""
            val importeStr = etImporte.text?.toString() ?: ""

            if (cuentaOrigen.isEmpty() || cuentaDestino.isEmpty() || importeStr.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (cuentaOrigen == cuentaDestino) {
                Toast.makeText(this, "No se puede transferir a la misma cuenta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val importe = importeStr.toDoubleOrNull()
            if (importe == null || importe <= 0) {
                Toast.makeText(this, "El importe debe ser mayor a $0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnRealizarTransferencia.isEnabled = false
            btnRealizarTransferencia.text = "Procesando..."

            restService.registrarTransferencia(
                cuentaOrigen,
                cuentaDestino,
                importe,
                object : EurekaRestService.RestCallback<Boolean> {
                    override fun onSuccess(result: Boolean) {
                        runOnUiThread {
                            Toast.makeText(this@TransferenciaActivity, "Transferencia realizada exitosamente", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }

                    override fun onError(error: String) {
                        runOnUiThread {
                            Toast.makeText(this@TransferenciaActivity, "Error: $error", Toast.LENGTH_LONG).show()
                            btnRealizarTransferencia.isEnabled = true
                            btnRealizarTransferencia.text = "Realizar Transferencia"
                        }
                    }
                })
        }
    }
}



