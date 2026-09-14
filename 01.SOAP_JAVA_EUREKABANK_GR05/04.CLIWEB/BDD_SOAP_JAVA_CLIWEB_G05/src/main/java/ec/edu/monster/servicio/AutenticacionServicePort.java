package ec.edu.monster.servicio;

import ec.edu.monster.modelo.Empleado;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://servicios.monster.edu.ec/", name = "AutenticacionSoap")
public interface AutenticacionServicePort {
    @WebMethod(operationName = "autenticar")
    @WebResult(name = "empleado")
    Empleado autenticar(@WebParam(name = "usuario") String usuario, @WebParam(name = "clave") String clave);
}
