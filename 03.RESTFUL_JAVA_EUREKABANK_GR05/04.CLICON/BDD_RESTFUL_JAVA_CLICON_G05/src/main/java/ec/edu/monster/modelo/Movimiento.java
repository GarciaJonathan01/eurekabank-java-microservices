/*
 * Modelo Movimiento para el cliente REST
 * Compatible con la vista del cliente consola
 */
package ec.edu.monster.modelo;

import java.util.Date;

public class Movimiento {
    
    private String cuencodigo;
    private int movinumero;
    private Date movifecha;
    private String emplcodigo;
    private String tipocodigo;
    private double moviimporte;
    private String cuenreferencia;
    
    public Movimiento() {
    }

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

    // Auxiliares para compatibilidad con la vista existente
    public String getCuenta() { return cuencodigo; }
    public int getNromov() { return movinumero; }
    public double getImporte() { return moviimporte; }
    public String getTipo() { return tipocodigo; }
    public String getAccion() {
        if (tipocodigo == null) return "N/A";
        switch (tipocodigo) {
            case "001":
            case "003":
            case "005":
            case "008":
                return "INGRESO";
            case "002":
            case "004":
            case "006":
            case "007":
            case "009":
            case "010":
                return "SALIDA";
            default:
                return "N/A";
        }
    }
}

