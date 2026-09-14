/*
 * Modelo Movimiento para el cliente SOAP
 * Adaptado al servicio EurekaBank
 */
package ec.edu.monster.modelo;

import java.util.Date;

/**
 * Clase modelo que representa un movimiento bancario
 * @author Grupo Monster G09
 */
public class Movimiento {
    
    private String cuencodigo;
    private int movinumero;
    private Date movifecha;
    private String emplcodigo;
    private String tipocodigo;
    private double moviimporte;
    private String cuenreferencia;
    
    // Constructor vacío
    public Movimiento() {
    }
    
    // Constructor con parámetros
    public Movimiento(String cuencodigo, int movinumero, Date movifecha, 
                     String emplcodigo, String tipocodigo, double moviimporte, 
                     String cuenreferencia) {
        this.cuencodigo = cuencodigo;
        this.movinumero = movinumero;
        this.movifecha = movifecha;
        this.emplcodigo = emplcodigo;
        this.tipocodigo = tipocodigo;
        this.moviimporte = moviimporte;
        this.cuenreferencia = cuenreferencia;
    }
    
    // Getters y Setters
    public String getCuencodigo() {
        return cuencodigo;
    }

    public void setCuencodigo(String cuencodigo) {
        this.cuencodigo = cuencodigo;
    }

    public int getMovinumero() {
        return movinumero;
    }

    public void setMovinumero(int movinumero) {
        this.movinumero = movinumero;
    }

    public Date getMovifecha() {
        return movifecha;
    }

    public void setMovifecha(Date movifecha) {
        this.movifecha = movifecha;
    }

    public String getEmplcodigo() {
        return emplcodigo;
    }

    public void setEmplcodigo(String emplcodigo) {
        this.emplcodigo = emplcodigo;
    }

    public String getTipocodigo() {
        return tipocodigo;
    }

    public void setTipocodigo(String tipocodigo) {
        this.tipocodigo = tipocodigo;
    }

    public double getMoviimporte() {
        return moviimporte;
    }

    public void setMoviimporte(double moviimporte) {
        this.moviimporte = moviimporte;
    }

    public String getCuenreferencia() {
        return cuenreferencia;
    }

    public void setCuenreferencia(String cuenreferencia) {
        this.cuenreferencia = cuenreferencia;
    }
    
    // Métodos auxiliares para compatibilidad con la vista
    public String getCuenta() {
        return cuencodigo;
    }
    
    public int getNromov() {
        return movinumero;
    }
    
    public String getFecha() {
        if (movifecha != null) {
            return movifecha.toString();
        }
        return "";
    }
    
    public String getTipo() {
        return tipocodigo;
    }
    
    public String getAccion() {
        // Determinar acción basado en el tipo de movimiento
        if (tipocodigo != null) {
            switch (tipocodigo) {
                case "001": // Apertura
                case "003": // Depósito
                case "005": // Interés
                case "008": // Transferencia entrada
                    return "INGRESO";
                case "002": // Cancelar
                case "004": // Retiro
                case "006": // Mantenimiento
                case "007": // ITF
                case "009": // Transferencia salida
                case "010": // Cargo por movimiento
                    return "SALIDA";
                default:
                    return "N/A";
            }
        }
        return "N/A";
    }
    
    public double getImporte() {
        return moviimporte;
    }
}

