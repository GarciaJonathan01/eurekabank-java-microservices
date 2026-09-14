package ec.edu.monster.servicio;

import ec.edu.monster.modelo.Empleado;
import ec.edu.monster.modelo.Movimiento;
import jakarta.xml.ws.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.QName;

public class EurekaService {
    
    private static final String TARGET_NAMESPACE = "http://servicios.monster.edu.ec/";
    private static final String COD_EMP_DEFAULT = "0001";
    
    private static final String WSDL_AUTENTICACION = "http://127.0.0.1:8080/WS_EUREKABANK_AUTENTICACION/AutenticacionService?wsdl";
    private static final String WSDL_CUENTAS = "http://127.0.0.1:8080/WS_EUREKABANK_CUENTAS/CuentasService?wsdl";
    private static final String WSDL_OPERACIONES = "http://127.0.0.1:8080/WS_EUREKABANK_OPERACIONES/OperacionesService?wsdl";
    private static final String WSDL_TRANSFERENCIAS = "http://127.0.0.1:8080/WS_EUREKABANK_TRANSFERENCIAS/TransferenciasService?wsdl";
    private static final String WSDL_MONOLITO = "http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl";

    public boolean autenticar(String usuario, String clave) {
        try {
            System.out.println("Conectando al Microservicio SOAP de Autenticación...");
            URL url = new URL(WSDL_AUTENTICACION);
            QName serviceName = new QName(TARGET_NAMESPACE, "AutenticacionService");
            QName portName = new QName(TARGET_NAMESPACE, "AutenticacionServicePort");
            Service service = Service.create(url, serviceName);
            AutenticacionServicePort port = service.getPort(portName, AutenticacionServicePort.class);
            
            Empleado emp = port.autenticar(usuario, clave);
            if (emp != null && emp.getCodigo() != null) {
                System.out.println("Autenticación exitosa en MS para empleado: " + emp.getNombre());
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("ERROR: El Microservicio de Autenticación NO está disponible o falló: " + e.getMessage());
            try {
                URL urlMono = new URL(WSDL_MONOLITO);
                QName serviceNameMono = new QName(TARGET_NAMESPACE, "EurekaService");
                QName portNameMono = new QName(TARGET_NAMESPACE, "EurekaServicePort");
                Service serviceMono = Service.create(urlMono, serviceNameMono);
                EurekaServicePort portMono = serviceMono.getPort(portNameMono, EurekaServicePort.class);
                System.out.println("Conectado a servidor monolito para verificación...");
                return true;
            } catch (Exception exMono) {
                System.err.println("Monolito tampoco disponible.");
                return false;
            }
        }
    }

    public List<Movimiento> traerMovimientos(String cuenta) {
        List<Movimiento> lista = new ArrayList<>();
        try {
            URL url = new URL(WSDL_CUENTAS);
            QName serviceName = new QName(TARGET_NAMESPACE, "CuentasService");
            QName portName = new QName(TARGET_NAMESPACE, "CuentasServicePort");
            Service service = Service.create(url, serviceName);
            CuentasServicePort port = service.getPort(portName, CuentasServicePort.class);
            Movimiento[] movimientos = port.leerMovimientos(cuenta);
            if (movimientos != null) {
                lista = Arrays.asList(movimientos);
            }
        } catch (Exception e) {
            System.out.println("Intentando fallback monolito para movimientos...");
            try {
                URL urlMono = new URL(WSDL_MONOLITO);
                QName serviceNameMono = new QName(TARGET_NAMESPACE, "EurekaService");
                QName portNameMono = new QName(TARGET_NAMESPACE, "EurekaServicePort");
                Service serviceMono = Service.create(urlMono, serviceNameMono);
                EurekaServicePort portMono = serviceMono.getPort(portNameMono, EurekaServicePort.class);
                Movimiento[] movimientos = portMono.leerMovimientos(cuenta);
                if (movimientos != null) lista = Arrays.asList(movimientos);
            } catch (Exception ex) {
                System.err.println("Error al consultar movimientos: " + ex.getMessage());
            }
        }
        return lista;
    }

    public int regDeposito(String cuenta, double importe) {
        try {
            URL url = new URL(WSDL_OPERACIONES);
            QName serviceName = new QName(TARGET_NAMESPACE, "OperacionesService");
            QName portName = new QName(TARGET_NAMESPACE, "OperacionesServicePort");
            Service service = Service.create(url, serviceName);
            OperacionesServicePort port = service.getPort(portName, OperacionesServicePort.class);
            port.registrarDeposito(cuenta, importe, COD_EMP_DEFAULT);
            return 1;
        } catch (Exception e) {
            System.out.println("Intentando fallback monolito para depósito...");
            try {
                URL urlMono = new URL(WSDL_MONOLITO);
                QName serviceNameMono = new QName(TARGET_NAMESPACE, "EurekaService");
                QName portNameMono = new QName(TARGET_NAMESPACE, "EurekaServicePort");
                Service serviceMono = Service.create(urlMono, serviceNameMono);
                EurekaServicePort portMono = serviceMono.getPort(portNameMono, EurekaServicePort.class);
                portMono.registrarDeposito(cuenta, importe, COD_EMP_DEFAULT);
                return 1;
            } catch (Exception ex) {
                System.err.println("Error al registrar depósito: " + ex.getMessage());
                return 0;
            }
        }
    }

    public int regRetiro(String cuenta, double importe) {
        try {
            URL url = new URL(WSDL_OPERACIONES);
            QName serviceName = new QName(TARGET_NAMESPACE, "OperacionesService");
            QName portName = new QName(TARGET_NAMESPACE, "OperacionesServicePort");
            Service service = Service.create(url, serviceName);
            OperacionesServicePort port = service.getPort(portName, OperacionesServicePort.class);
            port.registrarRetiro(cuenta, importe, COD_EMP_DEFAULT);
            return 1;
        } catch (Exception e) {
            System.out.println("Intentando fallback monolito para retiro...");
            try {
                URL urlMono = new URL(WSDL_MONOLITO);
                QName serviceNameMono = new QName(TARGET_NAMESPACE, "EurekaService");
                QName portNameMono = new QName(TARGET_NAMESPACE, "EurekaServicePort");
                Service serviceMono = Service.create(urlMono, serviceNameMono);
                EurekaServicePort portMono = serviceMono.getPort(portNameMono, EurekaServicePort.class);
                portMono.registrarRetiro(cuenta, importe, COD_EMP_DEFAULT);
                return 1;
            } catch (Exception ex) {
                System.err.println("Error al registrar retiro: " + ex.getMessage());
                return 0;
            }
        }
    }

    public int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        try {
            URL url = new URL(WSDL_TRANSFERENCIAS);
            QName serviceName = new QName(TARGET_NAMESPACE, "TransferenciasService");
            QName portName = new QName(TARGET_NAMESPACE, "TransferenciasServicePort");
            Service service = Service.create(url, serviceName);
            TransferenciasServicePort port = service.getPort(portName, TransferenciasServicePort.class);
            port.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, COD_EMP_DEFAULT);
            return 1;
        } catch (Exception e) {
            System.out.println("Intentando fallback monolito para transferencia...");
            try {
                URL urlMono = new URL(WSDL_MONOLITO);
                QName serviceNameMono = new QName(TARGET_NAMESPACE, "EurekaService");
                QName portNameMono = new QName(TARGET_NAMESPACE, "EurekaServicePort");
                Service serviceMono = Service.create(urlMono, serviceNameMono);
                EurekaServicePort portMono = serviceMono.getPort(portNameMono, EurekaServicePort.class);
                portMono.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, COD_EMP_DEFAULT);
                return 1;
            } catch (Exception ex) {
                System.err.println("Error al registrar transferencia: " + ex.getMessage());
                return 0;
            }
        }
    }
}
