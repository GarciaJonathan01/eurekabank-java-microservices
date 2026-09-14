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

    public Movimiento(String cuencodigo, int movinumero, Date movifecha, String emplcodigo, String tipocodigo, double moviimporte, String cuenreferencia) {
        this.cuencodigo = cuencodigo;
        this.movinumero = movinumero;
        this.movifecha = movifecha;
        this.emplcodigo = emplcodigo;
        this.tipocodigo = tipocodigo;
        this.moviimporte = moviimporte;
        this.cuenreferencia = cuenreferencia;
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

    public boolean isIngreso() {
        if (tipocodigo == null) return false;
        String t = tipocodigo.trim().toUpperCase();
        // 001: Apertura, 003: Depósito, 008: Transferencia A Favor (Abono)
        // Note: 009 is Transferencia Cargo (Salida), 002 is Retiro (Salida)
        return t.equals("001") || t.equals("003") || t.equals("008") || 
               t.contains("INGRESO") || t.contains("DEP") || t.contains("APERTURA") || t.contains("ABONO");
    }

    public String getAccion() {
        return isIngreso() ? "INGRESO" : "SALIDA";
    }
}