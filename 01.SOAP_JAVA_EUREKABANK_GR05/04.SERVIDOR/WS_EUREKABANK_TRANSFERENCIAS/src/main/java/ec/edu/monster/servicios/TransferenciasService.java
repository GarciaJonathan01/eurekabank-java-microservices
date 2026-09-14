package ec.edu.monster.servicios;

import ec.edu.monster.bd.AccesoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferenciasService {

    public void registrarTransferencia(String cuentaOrigen, String cuentaDestino, double importe, String codEmp) {
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
            if (!rs.next()) {
                throw new SQLException("ERROR, cuenta origen no existe o no está activa");
            }
            double saldoOrigen = rs.getDouble("dec_cuensaldo");
            int contOrigen = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();
            
            if (saldoOrigen < importe) {
                throw new SQLException("ERROR, saldo insuficiente en la cuenta origen");
            }
            
            sql = "SELECT dec_cuensaldo, int_cuencontmov FROM Cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, cuenta destino no existe o no está activa");
            }
            double saldoDestino = rs.getDouble("dec_cuensaldo");
            int contDestino = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();
            
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS siguiente_numero FROM Movimiento WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            rs = pstm.executeQuery();
            int siguienteNumeroOrigen = 1;
            if (rs.next()) {
                siguienteNumeroOrigen = rs.getInt("siguiente_numero");
                if (rs.wasNull()) siguienteNumeroOrigen = 1;
            }
            rs.close();
            pstm.close();
            
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS siguiente_numero FROM Movimiento WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            rs = pstm.executeQuery();
            int siguienteNumeroDestino = 1;
            if (rs.next()) {
                siguienteNumeroDestino = rs.getInt("siguiente_numero");
                if (rs.wasNull()) siguienteNumeroDestino = 1;
            }
            rs.close();
            pstm.close();
            
            saldoOrigen -= importe;
            contOrigen++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoOrigen);
            pstm.setInt(2, contOrigen);
            pstm.setString(3, cuentaOrigen);
            pstm.executeUpdate();
            pstm.close();
            
            saldoDestino += importe;
            contDestino++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoDestino);
            pstm.setInt(2, contDestino);
            pstm.setString(3, cuentaDestino);
            pstm.executeUpdate();
            pstm.close();
            
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) VALUES(?, ?, CURDATE(), ?, '009', ?, ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            pstm.setInt(2, siguienteNumeroOrigen);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.setString(5, cuentaDestino);
            pstm.executeUpdate();
            pstm.close();
            
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) VALUES(?, ?, CURDATE(), ?, '008', ?, ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            pstm.setInt(2, siguienteNumeroDestino);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.setString(5, cuentaOrigen);
            pstm.executeUpdate();
            pstm.close();
            
            cn.commit();
        } catch (SQLException e) {
            try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException("ERROR en transferencia: " + e.getMessage());
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }
    }
}
