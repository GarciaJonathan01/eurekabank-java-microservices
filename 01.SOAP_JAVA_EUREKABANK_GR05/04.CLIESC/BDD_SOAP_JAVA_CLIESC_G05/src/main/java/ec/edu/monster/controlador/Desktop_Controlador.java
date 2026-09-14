/*
 * Controlador para el cliente de escritorio EurekaBank
 * @author Grupo Monster G09
 */
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.servicio.EurekaService;
import java.util.List;

/**
 * Controlador que actúa como intermediario entre la vista y el servicio
 */
public class Desktop_Controlador {
    
    private final EurekaService service;
    
    public Desktop_Controlador() {
        this.service = new EurekaService();
    }
    
    /**
     * Obtiene los movimientos de una cuenta
     * @param cuenta Número de cuenta
     * @return Lista de movimientos
     */
    public List<Movimiento> traerMovimientos(String cuenta) {
        return service.traerMovimientos(cuenta);
    }
    
    /**
     * Registra un depósito
     * @param cuenta Número de cuenta
     * @param importe Importe a depositar
     * @return 1 si fue exitoso, 0 en caso contrario
     */
    public int regDeposito(String cuenta, double importe) {
        return service.regDeposito(cuenta, importe);
    }
    
    /**
     * Registra un retiro
     * @param cuenta Número de cuenta
     * @param importe Importe a retirar
     * @return 1 si fue exitoso, 0 en caso contrario
     */
    public int regRetiro(String cuenta, double importe) {
        return service.regRetiro(cuenta, importe);
    }
    
    /**
     * Registra una transferencia
     * @param cuentaOrigen Cuenta origen
     * @param cuentaDestino Cuenta destino
     * @param importe Importe a transferir
     * @return 1 si fue exitoso, 0 en caso contrario
     */
    public int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        return service.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }
}

