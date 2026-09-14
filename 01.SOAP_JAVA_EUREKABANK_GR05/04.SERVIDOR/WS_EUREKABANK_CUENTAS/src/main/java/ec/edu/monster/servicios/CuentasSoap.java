package ec.edu.monster.servicios;

import ec.edu.monster.modelo.Movimiento;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import java.util.List;

@WebService(serviceName = "CuentasService", 
           targetNamespace = "http://servicios.monster.edu.ec/",
           portName = "CuentasServicePort")
public class CuentasSoap {

    private final CuentasService service = new CuentasService();

    @WebMethod(operationName = "leerMovimientos")
    @WebResult(name = "movimiento")
    public Movimiento[] leerMovimientos(@WebParam(name = "cuenta") String cuenta) {
        System.out.println("MS Cuentas SOAP - Leyendo movimientos para cuenta: " + cuenta);
        if (cuenta == null || cuenta.trim().isEmpty()) {
            return new Movimiento[0];
        }
        List<Movimiento> lista = service.leerMovimientos(cuenta.trim());
        return lista.toArray(new Movimiento[0]);
    }
}
