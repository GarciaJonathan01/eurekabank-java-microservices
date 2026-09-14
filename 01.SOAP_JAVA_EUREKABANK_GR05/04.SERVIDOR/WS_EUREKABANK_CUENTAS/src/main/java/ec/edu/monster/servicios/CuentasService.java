package ec.edu.monster.servicios;

import ec.edu.monster.bd.AccesoDB;
import ec.edu.monster.modelo.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentasService {

    public List<Movimiento> leerMovimientos(String cuenta) {
        Connection cn = null;
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT "
                + " m.chr_cuencodigo AS cuencodigo, "
                + " m.int_movinumero AS movinumero, "
                + " m.dtt_movifecha AS movifecha, "
                + " m.chr_emplcodigo AS emplcodigo, "
                + " m.chr_tipocodigo AS tipocodigo, "
                + " m.dec_moviimporte AS moviimporte, "
                + " m.chr_cuenreferencia AS cuenreferencia "
                + "FROM Movimiento m "
                + "WHERE m.chr_cuencodigo = ? "
                + "ORDER BY m.int_movinumero DESC";
        try {
            cn = AccesoDB.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Movimiento rec = new Movimiento();
                rec.setCuencodigo(rs.getString("cuencodigo"));
                rec.setMovinumero(rs.getInt("movinumero"));
                rec.setMovifecha(rs.getDate("movifecha"));
                rec.setEmplcodigo(rs.getString("emplcodigo"));
                rec.setTipocodigo(rs.getString("tipocodigo"));
                rec.setMoviimporte(rs.getDouble("moviimporte"));
                rec.setCuenreferencia(rs.getString("cuenreferencia"));
                lista.add(rec);
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer movimientos: " + e.getMessage());
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }
        return lista;
    }
}
