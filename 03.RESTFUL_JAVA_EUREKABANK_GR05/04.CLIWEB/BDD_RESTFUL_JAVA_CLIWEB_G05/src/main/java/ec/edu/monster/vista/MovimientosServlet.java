/*
 * Servlet para consultar movimientos
 */
package ec.edu.monster.vista;

import ec.edu.monster.controlador.Web_Controlador;
import ec.edu.monster.modelo.Movimiento;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet que maneja la consulta de movimientos
 */
public class MovimientosServlet extends HttpServlet {

    private final Web_Controlador controlador = new Web_Controlador();

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
        
        // Obtener cuenta del parámetro
        String cuenta = request.getParameter("cuenta");
        
        if (cuenta != null && !cuenta.trim().isEmpty()) {
            // Consultar movimientos
            List<Movimiento> movimientos = controlador.traerMovimientos(cuenta);
            request.setAttribute("movimientos", movimientos);
            request.setAttribute("cuenta", cuenta);
        }
        
        request.getRequestDispatcher("/movimientos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}


