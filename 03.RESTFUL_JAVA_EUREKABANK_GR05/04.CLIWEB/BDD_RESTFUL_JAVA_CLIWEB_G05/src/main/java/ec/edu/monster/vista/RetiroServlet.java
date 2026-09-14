/*
 * Servlet para realizar retiros
 */
package ec.edu.monster.vista;

import ec.edu.monster.controlador.Web_Controlador;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet que maneja los retiros
 */
public class RetiroServlet extends HttpServlet {

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
        
        request.getRequestDispatcher("/retiro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar autenticación
        HttpSession session = request.getSession();
        Boolean autenticado = (Boolean) session.getAttribute("autenticado");
        
        if (autenticado == null || !autenticado) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String cuenta = request.getParameter("cuenta");
        String importeStr = request.getParameter("importe");
        
        if (cuenta != null && importeStr != null && !cuenta.trim().isEmpty() && !importeStr.trim().isEmpty()) {
            try {
                double importe = Double.parseDouble(importeStr.replace(",", "."));
                
                if (importe > 0) {
                    int resultado = controlador.regRetiro(cuenta, importe);
                    
                    if (resultado == 1) {
                        request.setAttribute("mensaje", "Retiro realizado exitosamente");
                        request.setAttribute("tipoMensaje", "success");
                    } else {
                        request.setAttribute("mensaje", "Error al procesar el retiro. Verifique los datos y el saldo disponible.");
                        request.setAttribute("tipoMensaje", "error");
                    }
                } else {
                    request.setAttribute("mensaje", "El importe debe ser mayor a cero");
                    request.setAttribute("tipoMensaje", "error");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "Formato inválido. Ingrese un número válido.");
                request.setAttribute("tipoMensaje", "error");
            }
        } else {
            request.setAttribute("mensaje", "Por favor, complete todos los campos");
            request.setAttribute("tipoMensaje", "error");
        }
        
        request.getRequestDispatcher("/retiro.jsp").forward(request, response);
    }
}


