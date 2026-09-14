/*
 * Prueba de lectura de movimientos por cuenta
 */
package ec.edu.monster.prueba;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicios.EurekaService;
import java.util.List;

public class PruebaMovimiento {
    public static void main(String[] args) {
        try {
            // dato de la prueba
            String cuenta = "00100001";

            // proceso
            EurekaService service = new EurekaService();
            List<Movimiento> lista = service.leerMovimientos(cuenta);

            // reporte (usando los nombres del modelo actual)
            for (Movimiento r : lista) {
                System.out.println(
                        r.getCuencodigo() + " - "
                        + r.getMovinumero() + " - "
                        + r.getMovifecha() + " - "
                        + r.getTipocodigo() + " - "
                        + r.getMoviimporte()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


