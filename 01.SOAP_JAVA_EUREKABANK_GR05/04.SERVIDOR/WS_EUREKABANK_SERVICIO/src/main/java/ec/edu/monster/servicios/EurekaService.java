/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.servicios;

import ec.edu.monster.bd.AccesoDB;
import ec.edu.monster.modelo.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER NITRO V15
 */
public class EurekaService {
    
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
            System.out.println("==========================================");
            System.out.println("leerMovimientos - Iniciando consulta");
            System.out.println("Cuenta recibida: " + cuenta);
            System.out.println("SQL: " + sql);
            
            cn = AccesoDB.getConnection();
            System.out.println("Conexión a BD establecida correctamente");
            
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            System.out.println("PreparedStatement creado con cuenta: " + cuenta);
            
            ResultSet rs = pstm.executeQuery();
            System.out.println("Query ejecutado, procesando resultados...");
            
            int contador = 0;
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
                contador++;
                System.out.println("Movimiento " + contador + " agregado: " + rec.getCuencodigo() + " - " + rec.getMovinumero());
            }
            System.out.println("Total movimientos encontrados: " + contador);
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            System.err.println("ERROR SQL en leerMovimientos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al leer movimientos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERROR GENERAL en leerMovimientos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error general al leer movimientos: " + e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    System.out.println("Conexión cerrada");
                }
            } catch (Exception e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
            System.out.println("leerMovimientos - Finalizado. Total en lista: " + lista.size());
            System.out.println("==========================================");
        }
        return lista;
    }

    public void registrarDeposito(String cuenta, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Paso 1: Leer datos de la cuenta
            String sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, cuenta no existe o no está activa");
            }
            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();

            // Paso 2: Obtener el siguiente número de movimiento
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS siguiente_numero "
                    + "FROM Movimiento "
                    + "WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            rs = pstm.executeQuery();
            int siguienteNumero = 1;
            if (rs.next()) {
                siguienteNumero = rs.getInt("siguiente_numero");
                if (rs.wasNull()) {
                    siguienteNumero = 1;
                }
            }
            rs.close();
            pstm.close();

            // Paso 3: Actualizar la cuenta
            saldo += importe;
            cont++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo);
            pstm.setInt(2, cont);
            pstm.setString(3, cuenta);
            pstm.executeUpdate();
            pstm.close();

            // Paso 4: Registrar movimiento (tipo '003' como depósito)
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES(?, ?, CURDATE(), ?, '003', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            pstm.setInt(2, siguienteNumero);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException("ERROR, en el proceso registrar depósito, intentelo más tarde.");
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }
    }
    
    public void registrarTransferencia(String cuentaOrigen, String cuentaDestino, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            
            // Validar que las cuentas sean diferentes
            if (cuentaOrigen.equals(cuentaDestino)) {
                throw new SQLException("ERROR, no se puede transferir a la misma cuenta");
            }
            
            // Paso 1: Leer datos de la cuenta origen
            String sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
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
            
            // Validar saldo suficiente
            if (saldoOrigen < importe) {
                throw new SQLException("ERROR, saldo insuficiente en la cuenta origen");
            }
            
            // Paso 2: Leer datos de la cuenta destino
            sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
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
            
            // Paso 3: Obtener siguiente número de movimiento para cuenta origen
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS siguiente_numero "
                    + "FROM Movimiento "
                    + "WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            rs = pstm.executeQuery();
            int siguienteNumeroOrigen = 1;
            if (rs.next()) {
                siguienteNumeroOrigen = rs.getInt("siguiente_numero");
                if (rs.wasNull()) {
                    siguienteNumeroOrigen = 1;
                }
            }
            rs.close();
            pstm.close();
            
            // Paso 4: Obtener siguiente número de movimiento para cuenta destino
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS siguiente_numero "
                    + "FROM Movimiento "
                    + "WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaDestino);
            rs = pstm.executeQuery();
            int siguienteNumeroDestino = 1;
            if (rs.next()) {
                siguienteNumeroDestino = rs.getInt("siguiente_numero");
                if (rs.wasNull()) {
                    siguienteNumeroDestino = 1;
                }
            }
            rs.close();
            pstm.close();
            
            // Paso 5: Actualizar cuenta origen (debitar)
            saldoOrigen -= importe;
            contOrigen++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoOrigen);
            pstm.setInt(2, contOrigen);
            pstm.setString(3, cuentaOrigen);
            pstm.executeUpdate();
            pstm.close();
            
            // Paso 6: Actualizar cuenta destino (acreditar)
            saldoDestino += importe;
            contDestino++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldoDestino);
            pstm.setInt(2, contDestino);
            pstm.setString(3, cuentaDestino);
            pstm.executeUpdate();
            pstm.close();
            
            // Paso 7: Registrar movimiento en cuenta origen (tipo '009' - Transferencia SALIDA)
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) "
                    + "VALUES(?, ?, CURDATE(), ?, '009', ?, ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuentaOrigen);
            pstm.setInt(2, siguienteNumeroOrigen);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.setString(5, cuentaDestino);
            pstm.executeUpdate();
            pstm.close();
            
            // Paso 8: Registrar movimiento en cuenta destino (tipo '008' - Transferencia INGRESO)
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte, chr_cuenreferencia) "
                    + "VALUES(?, ?, CURDATE(), ?, '008', ?, ?)";
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
            throw new RuntimeException("ERROR, en el proceso registrar transferencia, intentelo más tarde.");
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }
    }
    
    public void registrarRetiro(String cuenta, double importe, String codEmp) {
        Connection cn = null;
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);

            // Paso 1: Leer datos de la cuenta
            String sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM Cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                throw new SQLException("ERROR, cuenta no existe o no está activa");
            }
            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();

            // Validar saldo suficiente
            if (saldo < importe) {
                throw new SQLException("ERROR, saldo insuficiente");
            }

            // Paso 2: Obtener el siguiente número de movimiento
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) + 1 AS siguiente_numero "
                    + "FROM Movimiento "
                    + "WHERE chr_cuencodigo = ?";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            rs = pstm.executeQuery();
            int siguienteNumero = 1;
            if (rs.next()) {
                siguienteNumero = rs.getInt("siguiente_numero");
                if (rs.wasNull()) {
                    siguienteNumero = 1;
                }
            }
            rs.close();
            pstm.close();

            // Paso 3: Actualizar la cuenta (debitar)
            saldo -= importe;
            cont++;
            sql = "UPDATE Cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo);
            pstm.setInt(2, cont);
            pstm.setString(3, cuenta);
            pstm.executeUpdate();
            pstm.close();

            // Paso 4: Registrar movimiento (tipo '004' como retiro)
            sql = "INSERT INTO Movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES(?, ?, CURDATE(), ?, '004', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            pstm.setInt(2, siguienteNumero);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();
            pstm.close();

            cn.commit();
        } catch (SQLException e) {
            try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
            throw new RuntimeException("ERROR, en el proceso registrar retiro, intentelo más tarde.");
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception ignore) {}
        }
    }
}
