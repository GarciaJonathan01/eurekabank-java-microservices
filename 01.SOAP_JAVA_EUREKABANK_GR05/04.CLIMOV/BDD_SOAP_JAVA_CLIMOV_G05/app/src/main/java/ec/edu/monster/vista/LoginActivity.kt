package ec.edu.monster.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ec.edu.bd_soap_java.R
import ec.edu.monster.servicio.EurekaSoapService

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var soapService: EurekaSoapService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        soapService = EurekaSoapService(this)

        btnLogin.setOnClickListener {
            val usuario = etUsername.text?.toString()?.trim() ?: ""
            val password = etPassword.text?.toString()?.trim() ?: ""

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnLogin.isEnabled = false
            Toast.makeText(this, "Validando credenciales en Microservicio...", Toast.LENGTH_SHORT).show()

            soapService.autenticar(usuario, password, object : EurekaSoapService.SoapCallback<Boolean> {
                override fun onSuccess(result: Boolean) {
                    btnLogin.isEnabled = true
                    if (result) {
                        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                        intent.putExtra("usuario", usuario)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Acceso Denegado", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(error: String) {
                    btnLogin.isEnabled = true
                    Toast.makeText(this@LoginActivity, "Error: $error", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
