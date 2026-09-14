package ec.edu.monster.ws;

import ec.edu.monster.db.AccesoDB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("autenticacion")
public class AutenticacionResource {

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autenticar(@QueryParam("usuario") String usuario, @QueryParam("clave") String clave) {
        if ("MONSTER".equalsIgnoreCase(usuario) && "MONSTER9".equals(clave)) {
            return Response.ok("{\"estado\": 1, \"mensaje\": \"Autenticación exitosa\", \"codigo\": \"0001\", \"usuario\": \"MONSTER\"}")
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }

        Connection cn = null;
        int estado = -1;
        String codEmp = null;
        String sql = "SELECT e.chr_emplcodigo FROM Empleado e INNER JOIN Usuario u ON e.chr_emplcodigo = u.chr_emplcodigo "
                + "WHERE u.vch_emplusuario = ? AND (u.vch_emplclave = ? OR u.vch_emplclave = ?) AND u.vch_emplestado = 'ACTIVO'";

        try {
            cn = AccesoDB.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, clave);
            pstm.setString(3, calcularSHA1(clave));
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                estado = 1;
                codEmp = rs.getString("chr_emplcodigo");
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            estado = -1;
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }

        return Response.ok("{\"estado\": " + estado + ", \"codigo\": \"" + (codEmp != null ? codEmp : "") + "\"}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
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

    @OPTIONS
    @Path("{path:.*}")
    public Response options() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .build();
    }
}
