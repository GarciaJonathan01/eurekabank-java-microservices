package ec.edu.monster.ws;

import ec.edu.monster.db.AccesoDB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("corebancario")
public class OperacionesResource {

    @POST
    @Path("deposito")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarDeposito(@QueryParam("cuenta") String cuenta, @QueryParam("importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            ejecutarDeposito(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return Response.ok("{\"estado\": " + estado + "}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }

    @POST
    @Path("retiro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarRetiro(@QueryParam("cuenta") String cuenta, @QueryParam("importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            ejecutarRetiro(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return Response.ok("{\"estado\": " + estado + "}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }

    private void ejecutarDeposito(String cuenta, double importe, String codEmp) throws Exception {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            String sql = "SELECT dec_cuensaldo, int_cuencontmov FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) throw new SQLException("ERROR, cuenta no existe o no activa");
            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close(); pstm.close();

            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS sig FROM Movimiento WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            rs = pstm.executeQuery();
            int sig = rs.next() ? rs.getInt("sig") : 1;
            rs.close(); pstm.close();

            saldo += importe; cont++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo); pstm.setInt(2, cont); pstm.setString(3, cuenta);
            pstm.executeUpdate(); pstm.close();

            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) VALUES(?, ?, CURDATE(), ?, '003', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta); pstm.setInt(2, sig); pstm.setString(3, codEmp); pstm.setDouble(4, importe);
            pstm.executeUpdate(); pstm.close();

            cn.commit();
        } catch (Exception e) {
            if (cn != null) cn.rollback();
            throw e;
        } finally {
            if (cn != null) cn.close();
        }
    }

    private void ejecutarRetiro(String cuenta, double importe, String codEmp) throws Exception {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            String sql = "SELECT dec_cuensaldo, int_cuencontmov FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) throw new SQLException("ERROR, cuenta no existe o no activa");
            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close(); pstm.close();

            if (saldo < importe) throw new SQLException("ERROR, saldo insuficiente");

            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS sig FROM Movimiento WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            rs = pstm.executeQuery();
            int sig = rs.next() ? rs.getInt("sig") : 1;
            rs.close(); pstm.close();

            saldo -= importe; cont++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo); pstm.setInt(2, cont); pstm.setString(3, cuenta);
            pstm.executeUpdate(); pstm.close();

            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) VALUES(?, ?, CURDATE(), ?, '004', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta); pstm.setInt(2, sig); pstm.setString(3, codEmp); pstm.setDouble(4, importe);
            pstm.executeUpdate(); pstm.close();

            cn.commit();
        } catch (Exception e) {
            if (cn != null) cn.rollback();
            throw e;
        } finally {
            if (cn != null) cn.close();
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
