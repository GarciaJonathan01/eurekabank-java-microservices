/*
 * Vista para mostrar movimientos bancarios
 * Diseño mejorado similar al cliente .NET
 */
package ec.edu.monster.vista;

import ec.edu.monster.modelo.Movimiento;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Clase para mostrar movimientos en formato de tabla
 * @author Grupo Monster G09
 */
public class MovimientoView {
    
    private static final DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    // Colores (usando los mismos del cliente)
    public static final String COLOR_1 = "\033[38;2;58;180;217m"; // Azul claro
    public static final String COLOR_3 = "\033[38;2;246;222;136m"; // Amarillo claro
    public static final String BLANCO = "\033[97m";
    public static final String RESET = "\033[0m";
    
    /**
     * Obtiene la descripción del tipo de movimiento basado en el código
     */
    private static String obtenerDescripcionTipo(String tipocodigo) {
        if (tipocodigo == null) return "";
        switch (tipocodigo) {
            case "001": return "Apertura de Cuenta";
            case "002": return "Cancelar Cuenta";
            case "003": return "Deposito";
            case "004": return "Retiro";
            case "005": return "Interes";
            case "006": return "Mantenimiento";
            case "007": return "ITF";
            case "008": return "Transferencia";
            case "009": return "Transferencia";
            case "010": return "Cargo por Movimiento";
            default: return tipocodigo;
        }
    }
    
    /**
     * Muestra los movimientos en formato de tabla
     * @param movimientos Lista de movimientos a mostrar
     * @param anchoCuadro Ancho del cuadro para centrar
     */
    public static void mostrarMovimientos(List<Movimiento> movimientos, int anchoCuadro) {
        if (movimientos == null || movimientos.isEmpty()) {
            return;
        }
        
        int width = 120; // Ancho estándar de consola
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        // Asignación de anchos para columnas
        int anchoCuenta = 15;
        int anchoNroMov = 8;
        int anchoFecha = 12;
        int anchoTipo = 20;
        int anchoAccion = 10;
        int anchoImporte = anchoCuadro - (anchoCuenta + anchoNroMov + anchoFecha + anchoTipo + anchoAccion + 6);
        
        // Línea superior
        System.out.print(" ".repeat(startPos));
        System.out.println(COLOR_1 + "=".repeat(anchoCuadro) + RESET);
        
        // Encabezado
        System.out.print(" ".repeat(startPos));
        String encabezado = String.format(
            "%-" + anchoCuenta + "s %-" + anchoNroMov + "s %-" + anchoFecha + "s %-" + anchoTipo + "s %-" + anchoAccion + "s %" + anchoImporte + "s",
            "Cuenta", "Nro Mov.", "Fecha", "Tipo", "Acción", "Importe"
        );
        System.out.println(COLOR_1 + encabezado + RESET);
        
        // Línea separadora
        System.out.print(" ".repeat(startPos));
        System.out.println(COLOR_1 + "=".repeat(anchoCuadro) + RESET);
        
        // Mostrar cada movimiento
        for (Movimiento mov : movimientos) {
            String tipoDescripcion = obtenerDescripcionTipo(mov.getTipocodigo());
            String tipo = tipoDescripcion.length() > anchoTipo 
                ? tipoDescripcion.substring(0, anchoTipo) 
                : tipoDescripcion;
            
            String accion = mov.getAccion() != null && mov.getAccion().length() > anchoAccion 
                ? mov.getAccion().substring(0, anchoAccion) 
                : (mov.getAccion() != null ? mov.getAccion() : "");
            
            String fechaStr = mov.getMovifecha() != null 
                ? formatoFecha.format(mov.getMovifecha()) 
                : "N/A";
            
            String importeStr = "$" + formatoMoneda.format(mov.getMoviimporte());
            
            System.out.print(" ".repeat(startPos));
            String linea = String.format(
                "%-" + anchoCuenta + "s %-" + anchoNroMov + "d %-" + anchoFecha + "s %-" + anchoTipo + "s %-" + anchoAccion + "s %" + anchoImporte + "s",
                mov.getCuencodigo() != null ? mov.getCuencodigo() : "",
                mov.getMovinumero(),
                fechaStr,
                tipo,
                accion,
                importeStr
            );
            System.out.println(BLANCO + linea + RESET);
        }
        
        // Línea inferior
        System.out.print(" ".repeat(startPos));
        System.out.println(COLOR_1 + "=".repeat(anchoCuadro) + RESET);
    }
}

