package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.List;

public class Desktop_Controlador {
    
    private final EurekaService service;
    
    public Desktop_Controlador() {
        this.service = new EurekaService();
    }
    
    public boolean autenticar(String usuario, String clave) {
        return service.autenticar(usuario, clave);
    }

    public List<Movimiento> traerMovimientos(String cuenta) {
        return service.traerMovimientos(cuenta);
    }
    
    public int regDeposito(String cuenta, double importe) {
        return service.regDeposito(cuenta, importe);
    }
    
    public int regRetiro(String cuenta, double importe) {
        return service.regRetiro(cuenta, importe);
    }
    
    public int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        return service.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }
}