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
public class TransferenciasResource {

    @POST
    @Path("transferencia")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarTransferencia(@QueryParam("cuentaOrigen") String cuentaOrigen,
                                            @QueryParam("cuentaDestino") String cuentaDestino,
                                            @QueryParam("importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            ejecutarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
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

    private void ejecutarTransferencia(String cuentaOrigen, String cuentaDestino, double importe, String codEmp) throws Exception {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            
            if (cuentaOrigen.equals(cuentaDestino)) {
                throw new SQLException("ERROR, no se puede transferir a la misma cuenta");
            }
            
            String sql = "SELECT dec_cuensaldo, int_cuencontmov FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) throw new SQLException("ERROR, cuenta origen no existe o no activa");
            double saldoOrigen = rs.getDouble("dec_cuensaldo");
            int contOrigen = rs.getInt("int_cuencontmov");
            rs.close(); pstm.close();
            
            if (saldoOrigen < importe) throw new SQLException("ERROR, saldo insuficiente en cuenta origen");
            
            sql = "SELECT dec_cuensaldo, int_cuencontmov FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            rs = pstm.executeQuery();
            if (!rs.next()) throw new SQLException("ERROR, cuenta destino no existe o no activa");
            double saldoDestino = rs.getDouble("dec_cuensaldo");
            int contDestino = rs.getInt("int_cuencontmov");
            rs.close(); pstm.close();
            
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS sig FROM Movimiento WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            rs = pstm.executeQuery();
            int sigOrigen = rs.next() ? rs.getInt("sig") : 1;
            rs.close(); pstm.close();
            
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS sig FROM Movimiento WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            rs = pstm.executeQuery();
            int sigDestino = rs.next() ? rs.getInt("sig") : 1;
            rs.close(); pstm.close();
            
            saldoOrigen -= importe; contOrigen++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoOrigen); pstm.setInt(2, contOrigen); pstm.setString(3, cuentaOrigen);
            pstm.executeUpdate(); pstm.close();
            
            saldoDestino += importe; contDestino++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoDestino); pstm.setInt(2, contDestino); pstm.setString(3, cuentaDestino);
            pstm.executeUpdate(); pstm.close();
            
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) VALUES(?, ?, CURDATE(), ?, '009', ?, ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen); pstm.setInt(2, sigOrigen); pstm.setString(3, codEmp); pstm.setDouble(4, importe); pstm.setString(5, cuentaDestino);
            pstm.executeUpdate(); pstm.close();
            
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) VALUES(?, ?, CURDATE(), ?, '008', ?, ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino); pstm.setInt(2, sigDestino); pstm.setString(3, codEmp); pstm.setDouble(4, importe); pstm.setString(5, cuentaOrigen);
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
