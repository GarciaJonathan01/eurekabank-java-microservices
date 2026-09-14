package ec.edu.monster.servicios;

import ec.edu.monster.modelo.Movimiento;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import java.util.List;

@WebService(serviceName = "EurekaService", 
           targetNamespace = "http://servicios.monster.edu.ec/",
           portName = "EurekaServicePort")
public class EurekaSoap {

    private final EurekaService service = new EurekaService();

    @WebMethod(operationName = "leerMovimientos")
    @WebResult(name = "movimiento")
    public Movimiento[] leerMovimientos(@WebParam(name = "cuenta") String cuenta) {
        System.out.println("==========================================");
        System.out.println("EurekaSoap.leerMovimientos - RECIBIDO");
        System.out.println("Cuenta recibida en SOAP: [" + cuenta + "]");
        System.out.println("Cuenta es NULL: " + (cuenta == null));
        System.out.println("Cuenta está vacía: " + (cuenta != null && cuenta.trim().isEmpty()));
        System.out.println("Tipo de cuenta: " + (cuenta != null ? cuenta.getClass().getName() : "NULL"));
        System.out.println("Longitud de cuenta: " + (cuenta != null ? cuenta.length() : 0));
        
        // Validar que la cuenta no sea null o vacía
        if (cuenta == null || cuenta.trim().isEmpty()) {
            System.err.println("ERROR: Cuenta es NULL o vacía!");
            System.err.println("El servidor NO puede procesar la petición sin la cuenta");
            return new Movimiento[0];
        }
        
        try {
            List<Movimiento> lista = service.leerMovimientos(cuenta.trim());
            System.out.println("Lista obtenida del servicio: " + lista.size() + " movimientos");
            Movimiento[] array = lista.toArray(new Movimiento[0]);
            System.out.println("Array convertido: " + array.length + " elementos");
            
            // Log detallado de los primeros movimientos
            if (array.length > 0) {
                System.out.println("Primer movimiento: " + array[0].getCuencodigo() + " - " + array[0].getMovinumero());
            }
            
            System.out.println("EurekaSoap.leerMovimientos - FINALIZADO");
            System.out.println("==========================================");
            return array;
        } catch (Exception e) {
            System.err.println("ERROR en EurekaSoap.leerMovimientos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @WebMethod(operationName = "registrarDeposito")
    public void registrarDeposito(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "codEmp") String codEmp) {
        service.registrarDeposito(cuenta, importe, codEmp);
    }
    
    @WebMethod(operationName = "registrarTransferencia")
    public void registrarTransferencia(
            @WebParam(name = "cuentaOrigen") String cuentaOrigen,
            @WebParam(name = "cuentaDestino") String cuentaDestino,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "codEmp") String codEmp) {
        service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
    }
    
    @WebMethod(operationName = "registrarRetiro")
    public void registrarRetiro(
            @WebParam(name = "cuenta") String cuenta,
            @WebParam(name = "importe") double importe,
            @WebParam(name = "codEmp") String codEmp) {
        service.registrarRetiro(cuenta, importe, codEmp);
    }
}


