/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.prueba;

import ec.edu.monster.bd.AccesoDB;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ACER NITRO V15
 */
public class PruebaConexion {
    
    public static void main(String[] args) {
        Connection con = null;
        
        try {
            con = AccesoDB.getConnection();
            if (con != null && !con.isClosed()) {
                System.out.println("OK");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        } finally {
            AccesoDB.closeConnection(con);
        }
    }
}
