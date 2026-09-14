/*
 * Prueba de lectura de movimientos por cuenta
 * Cliente SOAP EurekaBank
 */
package ec.edu.monster.prueba;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.List;

/**
 * Clase de prueba para el servicio de movimientos
 * @author Grupo Monster G09
 */
public class PruebaMovimiento {
    
    public static void main(String[] args) {
        try {
            // Dato de la prueba
            String cuenta = "00100001";
            
            System.out.println("=== PRUEBA DE CONSULTA DE MOVIMIENTOS ===");
            System.out.println("Cuenta: " + cuenta);
            System.out.println();
            
            // Proceso
            EurekaService service = new EurekaService();
            List<Movimiento> lista = service.traerMovimientos(cuenta);
            
            // Reporte
            if (lista.isEmpty()) {
                System.out.println("No se encontraron movimientos para la cuenta: " + cuenta);
            } else {
                System.out.println("Total de movimientos: " + lista.size());
                System.out.println();
                System.out.println("CUENTA\t\tNRO MOV\t\tFECHA\t\t\tTIPO\t\tIMPORTE");
                System.out.println("────────────────────────────────────────────────────────────────────────────");
                
                for (Movimiento r : lista) {
                    System.out.println(
                            r.getCuencodigo() + "\t\t" +
                            r.getMovinumero() + "\t\t" +
                            (r.getMovifecha() != null ? r.getMovifecha().toString() : "N/A") + "\t\t" +
                            r.getTipocodigo() + "\t\t" +
                            r.getMoviimporte()
                    );
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

