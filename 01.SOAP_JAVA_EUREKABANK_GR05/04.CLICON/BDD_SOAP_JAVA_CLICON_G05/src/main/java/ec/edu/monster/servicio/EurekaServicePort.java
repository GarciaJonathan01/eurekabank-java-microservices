/*
 * Interfaz del servicio SOAP EurekaBank
 * Define los métodos del servicio web
 */
package ec.edu.monster.servicio;

import ec.edu.monster.modelo.Movimiento;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

/**
 * Interfaz del servicio SOAP EurekaBank
 * @author Grupo Monster G09
 */
@WebService(targetNamespace = "http://servicios.monster.edu.ec/", 
           name = "EurekaServicePort")
public interface EurekaServicePort {
    
    @WebMethod(operationName = "leerMovimientos")
    @WebResult(name = "movimiento")
    Movimiento[] leerMovimientos(@WebParam(name = "cuenta") String cuenta);
    
    @WebMethod(operationName = "registrarDeposito")
    void registrarDeposito(
        @WebParam(name = "cuenta") String cuenta,
        @WebParam(name = "importe") double importe,
        @WebParam(name = "codEmp") String codEmp);
    
    @WebMethod(operationName = "registrarTransferencia")
    void registrarTransferencia(
        @WebParam(name = "cuentaOrigen") String cuentaOrigen,
        @WebParam(name = "cuentaDestino") String cuentaDestino,
        @WebParam(name = "importe") double importe,
        @WebParam(name = "codEmp") String codEmp);
    
    @WebMethod(operationName = "registrarRetiro")
    void registrarRetiro(
        @WebParam(name = "cuenta") String cuenta,
        @WebParam(name = "importe") double importe,
        @WebParam(name = "codEmp") String codEmp);
}
