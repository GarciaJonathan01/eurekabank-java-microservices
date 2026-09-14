package ec.edu.monster.servicio;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://servicios.monster.edu.ec/", name = "TransferenciasSoap")
public interface TransferenciasServicePort {

    @WebMethod(operationName = "registrarTransferencia")
    void registrarTransferencia(
        @WebParam(name = "cuentaOrigen") String cuentaOrigen,
        @WebParam(name = "cuentaDestino") String cuentaDestino,
        @WebParam(name = "importe") double importe,
        @WebParam(name = "codEmp") String codEmp);
}
