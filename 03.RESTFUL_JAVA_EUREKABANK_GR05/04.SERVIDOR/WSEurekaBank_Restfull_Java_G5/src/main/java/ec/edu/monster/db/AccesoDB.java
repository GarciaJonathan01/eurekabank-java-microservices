package ec.edu.monster.db;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException; 
/**
 *
 * @author Jonathan Garc�a, Richard Gualotu�a, Jos� Proa�o
 */
public class AccesoDB {
    
    // AWS RDS MySQL
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASSWORD");

    public AccesoDB() { 

    }  

    public static Connection getConnection() throws SQLException {
        Connection cn = null;
        try {
            //datos MYSQL
            String driver = "com.mysql.cj.jdbc.Driver";
            //Cargar el driver a memoria
            Class.forName(driver).newInstance();
            //Obtener el objeto Connection
            cn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw new SQLException("ERROR, no se encuentra el dirver");
        } catch (Exception e) {
            throw new SQLException("ERROR, no se tiene acceso el servidor");
        }
        return cn;
    }
} 