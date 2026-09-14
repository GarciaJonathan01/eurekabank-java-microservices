package ec.edu.monster.modelo

import java.util.Date

data class Movimiento(
    var cuencodigo: String = "",
    var movinumero: Int = 0,
    var movifecha: Date? = null,
    var emplcodigo: String = "",
    var tipocodigo: String = "",
    var moviimporte: Double = 0.0,
    var cuenreferencia: String = ""
) {
    fun getAccion(): String {
        return when (tipocodigo) {
            "001", "003", "005", "008" -> "INGRESO"
            "002", "004", "006", "007", "009", "010" -> "SALIDA"
            else -> "N/A"
        }
    }
}



