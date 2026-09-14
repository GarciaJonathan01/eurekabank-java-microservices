/*
 * Servlet para manejar el login
 */
package ec.edu.monster.vista;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * Servlet que maneja el proceso de login
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar página de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        
        System.out.println("Login intent - Usuario: " + usuario + ", Password: " + (password != null ? "***" : "null"));
        
        // Validación remota con el Microservicio de Autenticación REST
        if (usuario != null && password != null) {
            ec.edu.monster.controlador.Web_Controlador controlador = new ec.edu.monster.controlador.Web_Controlador();
            boolean authOk = controlador.autenticar(usuario, password);
            if (authOk) {
                System.out.println("Login exitoso en Microservicio REST - Creando sesión");
                
                // Crear sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setAttribute("autenticado", true);
                
                // Redirigir al menú principal
                String contextPath = request.getContextPath();
                System.out.println("Redirigiendo a: " + contextPath + "/menu");
                response.sendRedirect(contextPath + "/menu");
                return;
            } else {
                System.out.println("Error de Autenticación en Microservicio REST");
            }
        } else {
            System.out.println("Usuario o password nulos");
        }
        
        // Si las credenciales son incorrectas o el servicio está fuera de línea, mostrar error
        request.setAttribute("error", "Error de Autenticación: Credenciales incorrectas o Microservicio REST fuera de línea (Undeployed)");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    /**
     * Genera el hash MD5 de una cadena
     */
    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }
}


