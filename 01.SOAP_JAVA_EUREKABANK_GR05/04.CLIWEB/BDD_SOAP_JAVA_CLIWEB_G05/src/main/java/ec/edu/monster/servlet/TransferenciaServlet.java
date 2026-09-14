/*
 * Servlet para realizar transferencias
 */
package ec.edu.monster.servlet;

import ec.edu.monster.controlador.Web_Controlador;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet que maneja las transferencias
 */
public class TransferenciaServlet extends HttpServlet {

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
        
        request.getRequestDispatcher("/transferencia.jsp").forward(request, response);
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
        
        String cuentaOrigen = request.getParameter("cuentaOrigen");
        String cuentaDestino = request.getParameter("cuentaDestino");
        String importeStr = request.getParameter("importe");
        
        if (cuentaOrigen != null && cuentaDestino != null && importeStr != null 
                && !cuentaOrigen.trim().isEmpty() && !cuentaDestino.trim().isEmpty() 
                && !importeStr.trim().isEmpty()) {
            
            if (cuentaOrigen.equals(cuentaDestino)) {
                request.setAttribute("mensaje", "No se puede transferir a la misma cuenta");
                request.setAttribute("tipoMensaje", "error");
            } else {
                try {
                    double importe = Double.parseDouble(importeStr.replace(",", "."));
                    
                    if (importe > 0) {
                        int resultado = controlador.regTransferencia(cuentaOrigen, cuentaDestino, importe);
                        
                        if (resultado == 1) {
                            request.setAttribute("mensaje", "Transferencia realizada exitosamente");
                            request.setAttribute("tipoMensaje", "success");
                        } else {
                            request.setAttribute("mensaje", "Error al procesar la transferencia. Verifique los datos.");
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
            }
        } else {
            request.setAttribute("mensaje", "Por favor, complete todos los campos");
            request.setAttribute("tipoMensaje", "error");
        }
        
        request.getRequestDispatcher("/transferencia.jsp").forward(request, response);
    }
}

