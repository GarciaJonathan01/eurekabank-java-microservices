/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.monster.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ACER NITRO V15
 */
public class AccesoDB {
    
    // Datos de conexión - AWS RDS MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    
    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver
            Class.forName(DRIVER);
            // Retornar la conexión
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver: " + e.getMessage());
        }
    }
    
    // Método para cerrar la conexión
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    // Método de prueba
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = getConnection();
            if (con != null && !con.isClosed()) {
                System.out.println("Conexión establecida correctamente con MySQL");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            closeConnection(con);
        }
    }
}
