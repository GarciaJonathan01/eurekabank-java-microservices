package ec.edu.monster.servicios;

import jakarta.xml.ws.Endpoint;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Clase para publicar el servicio SOAP EurekaBank
 * 
 * Esta clase publica el servicio SOAP de forma standalone.
 * El servicio estará disponible tanto en localhost como en la IP de red.
 * 
 * @author ACER NITRO V15
 */
public class EurekaServicePublisher {
    
    // Configuración del servicio
    private static final String PORT = "8080";
    private static final String CONTEXT_PATH = "WS_EUREKABANK_SERVICIO";
    private static final String SERVICE_PATH = "EurekaService";
    
    // IP de red (cambiar si tu PC tiene otra IP)
    private static final String NETWORK_IP = "127.0.0.1";
    
    /**
     * Obtiene la IP de red real de la máquina
     */
    private static String obtenerIpRed() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                // Ignorar interfaces loopback y no activas
                if (ni.isLoopback() || !ni.isUp()) {
                    continue;
                }
                
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // Buscar IPv4 que no sea loopback
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().contains(".")) {
                        String ip = addr.getHostAddress();
                        // Preferir IPs en el rango 192.168.x.x o 10.x.x.x
                        if (ip.startsWith("192.168.") || ip.startsWith("10.")) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener IP de red: " + e.getMessage());
        }
        return NETWORK_IP; // Fallback a la IP configurada
    }
    
    public static void main(String[] args) {
        // Detectar IP real de la máquina
        String ipReal = obtenerIpRed();
        System.out.println("IP detectada: " + ipReal);
        
        String localhostUrl = "http://localhost:" + PORT + "/" + CONTEXT_PATH + "/" + SERVICE_PATH;
        String networkUrl = "http://" + ipReal + ":" + PORT + "/" + CONTEXT_PATH + "/" + SERVICE_PATH;
        
        System.out.println("==========================================");
        System.out.println("Publicando servicio SOAP EurekaBank");
        System.out.println("==========================================");
        System.out.println("URL local: " + localhostUrl);
        System.out.println("URL de red: " + networkUrl);
        System.out.println("WSDL de red: " + networkUrl + "?wsdl");
        System.out.println("IP de red detectada: " + ipReal);
        System.out.println("==========================================");
        
        try {
            // Intentar publicar en la IP de red específica
            Endpoint endpoint = null;
            boolean publicadoEnRed = false;
            
            try {
                // Intentar con la IP de red detectada
                endpoint = Endpoint.publish(networkUrl, new EurekaSoap());
                publicadoEnRed = true;
                System.out.println("✓ Servicio publicado en IP de red: " + ipReal);
            } catch (Exception e1) {
                System.out.println("⚠ No se pudo publicar en IP de red, intentando localhost...");
                System.out.println("  Error: " + e1.getMessage());
                
                try {
                    // Fallback a localhost
                    endpoint = Endpoint.publish(localhostUrl, new EurekaSoap());
                    System.out.println("⚠ Servicio publicado en localhost SOLO");
                    System.out.println("⚠ ADVERTENCIA: El servicio NO será accesible desde la red");
                    System.out.println("⚠ Para acceso desde red, verifica:");
                    System.out.println("   1. Firewall de Windows (puerto " + PORT + ")");
                    System.out.println("   2. Configuración de red");
                    System.out.println("   3. Ejecutar como administrador");
                } catch (Exception e2) {
                    throw new RuntimeException("No se pudo publicar el servicio en ninguna interfaz", e2);
                }
            }
            
            System.out.println("✓ Servicio publicado exitosamente");
            if (publicadoEnRed) {
                System.out.println("✓ URL pública: " + networkUrl);
                System.out.println("\n⚠ IMPORTANTE:");
                System.out.println("  - El servicio está escuchando en la IP: " + ipReal);
                System.out.println("  - Asegúrate de que el firewall permita el puerto " + PORT);
                System.out.println("  - Verifica desde el celular: " + networkUrl + "?wsdl");
            } else {
                System.out.println("⚠ URL local: " + localhostUrl);
                System.out.println("⚠ El servicio NO es accesible desde la red");
            }
            System.out.println("\n✓ El servicio está corriendo y disponible");
            System.out.println("\nPresiona ENTER para detener el servicio...");
            
            // Mantener el servicio corriendo hasta que se presione Enter
            System.in.read();
            
            System.out.println("\nDeteniendo el servicio...");
            endpoint.stop();
            
        } catch (Exception e) {
            System.err.println("ERROR al publicar el servicio: " + e.getMessage());
            e.printStackTrace();
            System.err.println("\nPosibles soluciones:");
            System.err.println("1. Verifica que el puerto " + PORT + " no esté en uso");
            System.err.println("2. Verifica que el firewall permita el puerto " + PORT);
            System.err.println("3. Ejecuta como administrador si es necesario");
            System.exit(1);
        }
        
        System.exit(0);
    }
}

