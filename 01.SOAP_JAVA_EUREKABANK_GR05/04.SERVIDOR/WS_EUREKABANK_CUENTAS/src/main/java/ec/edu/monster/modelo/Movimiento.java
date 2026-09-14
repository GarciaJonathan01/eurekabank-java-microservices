package ec.edu.monster.modelo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name="movimiento")
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
    
    @XmlElement(name="cuencodigo")
    public String getCuencodigo() {
        return cuencodigo;
    }

    public void setCuencodigo(String cuencodigo) {
        this.cuencodigo = cuencodigo;
    }

    @XmlElement(name="movinumero")
    public int getMovinumero() {
        return movinumero;
    }

    public void setMovinumero(int movinumero) {
        this.movinumero = movinumero;
    }

    @XmlElement(name="movifecha")
    public Date getMovifecha() {
        return movifecha;
    }

    public void setMovifecha(Date movifecha) {
        this.movifecha = movifecha;
    }

    @XmlElement(name="emplcodigo")
    public String getEmplcodigo() {
        return emplcodigo;
    }

    public void setEmplcodigo(String emplcodigo) {
        this.emplcodigo = emplcodigo;
    }

    @XmlElement(name="tipocodigo")
    public String getTipocodigo() {
        return tipocodigo;
    }

    public void setTipocodigo(String tipocodigo) {
        this.tipocodigo = tipocodigo;
    }

    @XmlElement(name="moviimporte")
    public double getMoviimporte() {
        return moviimporte;
    }

    public void setMoviimporte(double moviimporte) {
        this.moviimporte = moviimporte;
    }

    @XmlElement(name="cuenreferencia")
    public String getCuenreferencia() {
        return cuenreferencia;
    }

    public void setCuenreferencia(String cuenreferencia) {
        this.cuenreferencia = cuenreferencia;
    }
}
