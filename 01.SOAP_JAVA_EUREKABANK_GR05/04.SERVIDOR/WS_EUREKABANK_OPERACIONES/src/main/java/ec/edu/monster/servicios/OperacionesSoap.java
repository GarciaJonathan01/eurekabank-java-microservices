package ec.edu.monster.servicios;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(serviceName = "OperacionesService", 
           targetNamespace = "http://servicios.monster.edu.ec/",
           portName = "OperacionesServicePort")
public class OperacionesSoap {

    private final OperacionesService service = new OperacionesService();

    @WebMethod(operationName = "registrarDeposito")
    public void registrarDeposito(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "codEmp") String codEmp) {
        System.out.println("MS Operaciones SOAP - Registrando depósito en cuenta: " + cuenta + " por " + importe);
        service.registrarDeposito(cuenta, importe, codEmp);
    }
    
    @WebMethod(operationName = "registrarRetiro")
    public void registrarRetiro(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "codEmp") String codEmp) {
        System.out.println("MS Operaciones SOAP - Registrando retiro en cuenta: " + cuenta + " por " + importe);
        service.registrarRetiro(cuenta, importe, codEmp);
    }
}
