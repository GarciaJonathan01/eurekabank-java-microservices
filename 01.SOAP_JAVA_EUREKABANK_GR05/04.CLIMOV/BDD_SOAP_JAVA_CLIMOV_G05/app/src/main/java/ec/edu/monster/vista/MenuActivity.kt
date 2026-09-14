package ec.edu.monster.vista

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ec.edu.bd_soap_java.R
import java.text.SimpleDateFormat
import java.util.*

data class Operacion(
    val titulo: String,
    val icono: Int,
    val colorFondo: Int,
    val colorIcono: Int,
    val actividad: Class<*>
)

class MenuActivity : AppCompatActivity() {
    private lateinit var rvOperaciones: RecyclerView
    private lateinit var tvUsuario: TextView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var btnCerrarSesion: com.google.android.material.button.MaterialButton
    private var usuario: String = "Usuario"

    private val operaciones = listOf(
        Operacion("Ver movimientos", android.R.drawable.ic_menu_recent_history, 0xFFE3F2FD.toInt(), 0xFF3AB4D9.toInt(), MovimientosActivity::class.java),
        Operacion("Realizar depósitos", android.R.drawable.ic_menu_upload, 0xFFFFF9C4.toInt(), 0xFFF6DE88.toInt(), DepositoActivity::class.java),
        Operacion("Retirar dinero", android.R.drawable.ic_menu_myplaces, 0xFFFFEBEE.toInt(), 0xFFF67E80.toInt(), RetiroActivity::class.java),
        Operacion("Hacer transferencias", android.R.drawable.ic_menu_sort_by_size, 0xFFF3E5F5.toInt(), 0xFF9E7CC5.toInt(), TransferenciaActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        usuario = intent.getStringExtra("usuario") ?: "Usuario"

        tvUsuario = findViewById(R.id.tvUsuario)
        tvUsuario.text = "Hola, $usuario"

        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }

        rvOperaciones = findViewById(R.id.rvOperaciones)
        rvOperaciones.layoutManager = GridLayoutManager(this, 2)
        rvOperaciones.adapter = OperacionesAdapter(operaciones) { operacion ->
            val intent = Intent(this, operacion.actividad)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.selectedItemId = R.id.nav_home
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Ya estamos en home
                    true
                }
                R.id.nav_operations -> {
                    mostrarModalOperaciones()
                    true
                }
                R.id.nav_profile -> {
                    mostrarModalPerfil()
                    true
                }
                else -> false
            }
        }
    }

    private fun cerrarSesion() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                // Limpiar preferencias
                val prefs: SharedPreferences = getSharedPreferences("EurekaBankPrefs", MODE_PRIVATE)
                prefs.edit().clear().apply()
                
                // Volver al login
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun mostrarModalOperaciones() {
        val dialogView = layoutInflater.inflate(R.layout.modal_operaciones, null)
        val btnCerrar = dialogView.findViewById<ImageButton>(R.id.btnCerrarModal)
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()
        
        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }

    private fun mostrarModalPerfil() {
        val dialogView = layoutInflater.inflate(R.layout.modal_perfil, null)
        val btnCerrar = dialogView.findViewById<ImageButton>(R.id.btnCerrarPerfil)
        val tvNombreUsuario = dialogView.findViewById<TextView>(R.id.tvNombreUsuario)
        val tvUsuarioPerfil = dialogView.findViewById<TextView>(R.id.tvUsuarioPerfil)
        val tvFechaSesion = dialogView.findViewById<TextView>(R.id.tvFechaSesion)
        
        tvNombreUsuario.text = usuario
        tvUsuarioPerfil.text = usuario.uppercase()
        
        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        tvFechaSesion.text = fecha
        
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()
        
        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }

    class OperacionesAdapter(
        private val operaciones: List<Operacion>,
        private val onItemClick: (Operacion) -> Unit
    ) : RecyclerView.Adapter<OperacionesAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
            val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
            val iconContainer: View = view.findViewById(R.id.iconContainer)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_operacion, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val operacion = operaciones[position]
            holder.tvTitulo.text = operacion.titulo
            holder.ivIcon.setImageResource(operacion.icono)
            holder.ivIcon.setColorFilter(operacion.colorIcono)
            holder.iconContainer.setBackgroundColor(operacion.colorFondo)
            
            holder.itemView.setOnClickListener {
                onItemClick(operacion)
            }
        }

        override fun getItemCount() = operaciones.size
    }
}

