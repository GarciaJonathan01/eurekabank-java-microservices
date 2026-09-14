package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.List;

public class CliCon_Controlador {
    
    public boolean autenticar(String usuario, String clave) {
        EurekaService service = new EurekaService();
        return service.autenticar(usuario, clave);
    }

    public List<Movimiento> traerMovimientos(String cuenta) {
        EurekaService service = new EurekaService();
        return service.traerMovimientos(cuenta);
    }
    
    public int regDeposito(String cuenta, double importe) {
        EurekaService service = new EurekaService();
        return service.regDeposito(cuenta, importe);
    }
    
    public int regRetiro(String cuenta, double importe) {
        EurekaService service = new EurekaService();
        return service.regRetiro(cuenta, importe);
    }
    
    public int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        EurekaService service = new EurekaService();
        return service.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }
}
