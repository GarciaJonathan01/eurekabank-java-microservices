package ec.edu.monster.servicios;

import ec.edu.monster.bd.AccesoDB;
import ec.edu.monster.modelo.Empleado;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutenticacionService {

    public Empleado autenticar(String usuario, String clave) {
        // Soporte para usuario demo 'MONSTER' / 'MONSTER9'
        if ("MONSTER".equalsIgnoreCase(usuario) && "MONSTER9".equals(clave)) {
            Empleado empMonster = new Empleado();
            empMonster.setCodigo("0001");
            empMonster.setPaterno("Romero");
            empMonster.setMaterno("Castillo");
            empMonster.setNombre("Carlos Alberto");
            empMonster.setUsuario("MONSTER");
            return empMonster;
        }

        Connection cn = null;
        Empleado emp = null;
        String sql = "SELECT e.chr_emplcodigo, e.vch_emplpaterno, e.vch_emplmaterno, e.vch_emplnombre, u.vch_emplusuario "
                + "FROM Empleado e INNER JOIN Usuario u ON e.chr_emplcodigo = u.chr_emplcodigo "
                + "WHERE u.vch_emplusuario = ? AND (u.vch_emplclave = ? OR u.vch_emplclave = ?) AND u.vch_emplestado = 'ACTIVO'";
        try {
            cn = AccesoDB.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, clave);
            pstm.setString(3, calcularSHA1(clave));
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                emp = new Empleado();
                emp.setCodigo(rs.getString("chr_emplcodigo"));
                emp.setPaterno(rs.getString("vch_emplpaterno"));
                emp.setMaterno(rs.getString("vch_emplmaterno"));
                emp.setNombre(rs.getString("vch_emplnombre"));
                emp.setUsuario(rs.getString("vch_emplusuario"));
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error en autenticación: " + e.getMessage());
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }
        return emp;
    }

    private String calcularSHA1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] result = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            return input;
        }
    }
}
