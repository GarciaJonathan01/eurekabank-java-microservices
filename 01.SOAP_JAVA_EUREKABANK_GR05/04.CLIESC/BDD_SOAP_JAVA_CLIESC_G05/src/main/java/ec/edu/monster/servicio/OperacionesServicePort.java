package ec.edu.monster.servicio;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://servicios.monster.edu.ec/", name = "OperacionesSoap")
public interface OperacionesServicePort {

    @WebMethod(operationName = "registrarDeposito")
    void registrarDeposito(
        @WebParam(name = "cuenta") String cuenta,
        @WebParam(name = "importe") double importe,
        @WebParam(name = "codEmp") String codEmp);

    @WebMethod(operationName = "registrarRetiro")
    void registrarRetiro(
        @WebParam(name = "cuenta") String cuenta,
        @WebParam(name = "importe") double importe,
        @WebParam(name = "codEmp") String codEmp);
}
