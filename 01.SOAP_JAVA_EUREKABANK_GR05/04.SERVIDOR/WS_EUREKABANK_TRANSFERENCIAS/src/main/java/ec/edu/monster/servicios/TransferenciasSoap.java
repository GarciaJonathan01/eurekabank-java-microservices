package ec.edu.monster.servicios;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(serviceName = "TransferenciasService", 
           targetNamespace = "http://servicios.monster.edu.ec/",
           portName = "TransferenciasServicePort")
public class TransferenciasSoap {

    private final TransferenciasService service = new TransferenciasService();

    @WebMethod(operationName = "registrarTransferencia")
    public void registrarTransferencia(
            @WebParam(name = "cuentaOrigen") String cuentaOrigen,
            @WebParam(name = "cuentaDestino") String cuentaDestino,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "codEmp") String codEmp) {
        System.out.println("MS Transferencias SOAP - Registrando transferencia de " + cuentaOrigen + " a " + cuentaDestino + " por " + importe);
        service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
    }
}
