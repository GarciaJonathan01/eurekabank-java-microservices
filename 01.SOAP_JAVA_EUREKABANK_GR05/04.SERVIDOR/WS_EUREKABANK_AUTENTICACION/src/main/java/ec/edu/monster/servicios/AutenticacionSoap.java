package ec.edu.monster.servicios;

import ec.edu.monster.modelo.Empleado;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(serviceName = "AutenticacionService", 
           targetNamespace = "http://servicios.monster.edu.ec/",
           portName = "AutenticacionServicePort")
public class AutenticacionSoap {

    private final AutenticacionService service = new AutenticacionService();

    @WebMethod(operationName = "autenticar")
    @WebResult(name = "empleado")
    public Empleado autenticar(
            @WebParam(name = "usuario") String usuario,
            @WebParam(name = "clave") String clave) {
        System.out.println("MS Autenticación SOAP - Petición para usuario: " + usuario);
        return service.autenticar(usuario, clave);
    }
}
