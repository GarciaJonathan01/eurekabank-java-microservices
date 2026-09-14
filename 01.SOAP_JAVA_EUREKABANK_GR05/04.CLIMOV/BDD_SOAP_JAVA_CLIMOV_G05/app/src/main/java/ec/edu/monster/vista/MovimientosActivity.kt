package ec.edu.monster.vista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import ec.edu.monster.modelo.Movimiento
import ec.edu.bd_soap_java.R
import ec.edu.monster.servicio.EurekaSoapService
import java.text.SimpleDateFormat
import java.util.*

class MovimientosActivity : AppCompatActivity() {
    private lateinit var etCuenta: TextInputEditText
    private lateinit var btnBuscar: MaterialButton
    private lateinit var rvMovimientos: RecyclerView
    private lateinit var adapter: MovimientosAdapter

    private lateinit var soapService: EurekaSoapService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos)
        
        soapService = EurekaSoapService(this)

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }

        etCuenta = findViewById(R.id.etCuenta)
        btnBuscar = findViewById(R.id.btnBuscar)
        rvMovimientos = findViewById(R.id.rvMovimientos)

        rvMovimientos.layoutManager = LinearLayoutManager(this)
        adapter = MovimientosAdapter(emptyList())
        rvMovimientos.adapter = adapter

        btnBuscar.setOnClickListener {
            val cuenta = etCuenta.text?.toString() ?: ""
            if (cuenta.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un número de cuenta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnBuscar.isEnabled = false
            btnBuscar.text = "Buscando..."

            soapService.leerMovimientos(cuenta, object : EurekaSoapService.SoapCallback<List<Movimiento>> {
                override fun onSuccess(result: List<Movimiento>) {
                    runOnUiThread {
                        adapter.updateMovimientos(result)
                        btnBuscar.isEnabled = true
                        btnBuscar.text = "Buscar"
                        if (result.isEmpty()) {
                            Toast.makeText(this@MovimientosActivity, "No se encontraron movimientos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@MovimientosActivity, "Error: $error", Toast.LENGTH_LONG).show()
                        btnBuscar.isEnabled = true
                        btnBuscar.text = "Buscar"
                    }
                }
            })
        }
    }

    class MovimientosAdapter(private var movimientos: List<Movimiento>) :
        RecyclerView.Adapter<MovimientosAdapter.ViewHolder>() {

        fun updateMovimientos(newMovimientos: List<Movimiento>) {
            movimientos = newMovimientos
            notifyDataSetChanged()
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
            val tvFecha: TextView = view.findViewById(R.id.tvFecha)
            val tvImporte: TextView = view.findViewById(R.id.tvImporte)
            val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
            val iconContainer: View = view.findViewById(R.id.iconContainer)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movimiento, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val movimiento = movimientos[position]
            val accion = movimiento.getAccion()
            val esIngreso = accion == "INGRESO"

            // Descripción basada en el tipo
            val descripcion = when (movimiento.tipocodigo) {
                "003" -> "Depósito"
                "004" -> "Retiro"
                "008" -> "Transferencia recibida"
                "009" -> "Transferencia enviada"
                else -> "Movimiento ${movimiento.tipocodigo}"
            }
            holder.tvDescripcion.text = descripcion

            // Fecha
            val fechaStr = if (movimiento.movifecha != null) {
                val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
                sdf.format(movimiento.movifecha)
            } else {
                "Fecha no disponible"
            }
            holder.tvFecha.text = fechaStr

            // Importe
            val signo = if (esIngreso) "+" else "-"
            val color = if (esIngreso) "#4CAF50" else "#F67E80"
            holder.tvImporte.text = "$signo $ ${String.format("%.2f", movimiento.moviimporte)}"
            holder.tvImporte.setTextColor(android.graphics.Color.parseColor(color))

            // Icono
            val icono = if (esIngreso) {
                android.R.drawable.ic_menu_upload
            } else {
                android.R.drawable.ic_menu_myplaces
            }
            holder.ivIcon.setImageResource(icono)
            holder.ivIcon.setColorFilter(android.graphics.Color.parseColor(color))

            val colorFondo = if (esIngreso) {
                android.graphics.Color.parseColor("#30afe0f8")
            } else {
                android.graphics.Color.parseColor("#20f67e80")
            }
            holder.iconContainer.setBackgroundColor(colorFondo)
        }

        override fun getItemCount() = movimientos.size
    }
}

