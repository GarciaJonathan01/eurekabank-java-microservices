package ec.edu.monster.ws;

import ec.edu.monster.db.AccesoDB;
import ec.edu.monster.modelo.Movimiento;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Path("corebancario")
public class CuentasResource {

    @GET
    @Path("movimientos/{cuenta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMovimientos(@PathParam("cuenta") String cuenta) {
        Connection cn = null;
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT m.chr_cuencodigo cuenta, m.int_movinumero nromov, m.dtt_movifecha fecha, "
                + "m.chr_tipocodigo tipocodigo, t.vch_tipodescripcion tipo, t.vch_tipoaccion accion, "
                + "m.dec_moviimporte importe, m.chr_cuenreferencia referencia "
                + "FROM TipoMovimiento t INNER JOIN Movimiento m ON t.chr_tipocodigo = m.chr_tipocodigo "
                + "WHERE m.chr_cuencodigo = ? ORDER BY m.int_movinumero DESC";

        try {
            cn = AccesoDB.getConnection();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Movimiento rec = new Movimiento();
                rec.setCuenta(rs.getString("cuenta"));
                rec.setNromov(rs.getInt("nromov"));
                rec.setFecha(rs.getDate("fecha"));
                String tipocodigo = rs.getString("tipocodigo");
                rec.setTipo(tipocodigo != null ? tipocodigo : rs.getString("tipo"));
                rec.setAccion(rs.getString("accion"));
                rec.setImporte(rs.getDouble("importe"));
                rec.setReferencia(rs.getString("referencia"));
                lista.add(rec);
            }
            rs.close();
            pstm.close();

            return Response.ok(lista)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
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
