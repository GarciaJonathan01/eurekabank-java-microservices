package ec.edu.monster.servicio;

import ec.edu.monster.modelo.Movimiento;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://servicios.monster.edu.ec/", name = "CuentasSoap")
public interface CuentasServicePort {
    @WebMethod(operationName = "leerMovimientos")
    @WebResult(name = "movimiento")
    Movimiento[] leerMovimientos(@WebParam(name = "cuenta") String cuenta);
}
