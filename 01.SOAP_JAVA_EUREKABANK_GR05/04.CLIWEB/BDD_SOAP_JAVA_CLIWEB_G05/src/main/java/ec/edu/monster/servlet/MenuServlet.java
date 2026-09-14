/*
 * Servlet para mostrar el menú principal
 */
package ec.edu.monster.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet que muestra el menú principal
 */
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar autenticación
        HttpSession session = request.getSession();
        Boolean autenticado = (Boolean) session.getAttribute("autenticado");
        
        if (autenticado == null || !autenticado) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Mostrar menú
        request.getRequestDispatcher("/menu.jsp").forward(request, response);
    }
}

