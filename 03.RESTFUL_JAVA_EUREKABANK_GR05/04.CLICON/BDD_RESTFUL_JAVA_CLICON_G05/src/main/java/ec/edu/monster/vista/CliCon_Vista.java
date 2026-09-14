/*
 * Sistema Bancario EurekaBank - Cliente Console
 * Interfaz de Usuario Mejorada para Operaciones Bancarias
 * Jonathan García, Richard Gualotuña, José Proaño
 */
package ec.edu.monster.vista;

import ec.edu.monster.controlador.CliCon_Controlador;
import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.vista.MovimientoView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que maneja la interfaz de usuario para EurekaBank
 * @author Jonathan García, Richard Gualotuña, José Proaño
 */
public class CliCon_Vista {

    // Constantes de autenticaciÃ³n
    private static final String USUARIO = "MONSTER";
    private static final String PASS = "MONSTER9";
    
    // Logo GR9
        private static final String[] LOGO = {
        "  ███╗   ███╗ ██████╗ ███╗   ██╗███████╗████████╗███████╗██████╗ ",
        "  ████╗ ████║██╔═══██╗████╗  ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗",
        "  ██╔████╔██║██║   ██║██╔██╗ ██║███████╗   ██║   █████╗  ██████╔╝",
        "  ██║╚██╔╝██║██║   ██║██║╚██╗██║╚════██║   ██║   ██╔══╝  ██╔══██╗",
        "  ██║ ╚═╝ ██║╚██████╔╝██║ ╚████║███████║   ██║   ███████╗██║  ██║",
        "  ╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝"
    };
    
    // Utilidades de formato
    private static final DecimalFormat formatoMoneda = new DecimalFormat("#,##0.00");
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    
    private static Scanner scanner;
    
    /**
     * Punto de entrada principal de la aplicaciÃ³n
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        try {
            int anchoCuadro = calcularAnchoLogo();
            CliCon_Controlador controlador = new CliCon_Controlador();
            
            boolean acceso = false;
            
            while (!acceso) {
                limpiarPantalla();
                mostrarLogoYBienvenido();
                
                String[] credenciales = pedirCredenciales(anchoCuadro);
                String usuario = credenciales[0];
                String password = credenciales[1];
                
                if (controlador.autenticar(usuario, password)) {
                    acceso = true;
                    mostrarMensajeCentrado("Acceso exitoso", true);
                    mostrarMensajeCentrado("Presione cualquier tecla para continuar...", false);
                    scanner.nextLine();
                } else {
                    mostrarMensajeCentrado("Acceso denegado", false);
                    mostrarMensajeCentrado("Presione cualquier tecla para intentar nuevamente...", false);
                    scanner.nextLine();
                }
            }
            
            limpiarPantalla();
            mostrarLogoYBienvenido();
            ejecutarMenu(controlador, anchoCuadro);
        } finally {
            mostrarDespedida();
            scanner.close();
        }
    }
    
    /**
     * Calcula el ancho del logo
     */
    private static int calcularAnchoLogo() {
        int max = 0;
        for (String linea : LOGO) {
            if (linea.length() > max) {
                max = linea.length();
            }
        }
        return max;
    }
    
    /**
     * Muestra el logo y bienvenido (diseÃ±o del cliente .NET)
     */
    public static void mostrarLogoYBienvenido() {
        limpiarPantalla();
        
        int width = 120; // Ancho estÃ¡ndar
        int logoAncho = calcularAnchoLogo();
        int startPos = (width - logoAncho) / 2;
        if (startPos < 0) startPos = 0;
        
        // Imprimir logo con degradado cyan-verde
        int mitad = LOGO.length / 2;
        
        System.out.print("\033[36m"); // Cyan
        for (int i = 0; i < mitad; i++) {
            System.out.print(" ".repeat(startPos));
            System.out.println(LOGO[i]);
        }
        
        System.out.print("\033[32m"); // Verde
        for (int i = mitad; i < LOGO.length; i++) {
            System.out.print(" ".repeat(startPos));
            System.out.println(LOGO[i]);
        }
        
        // Imprimir cuadro BIENVENIDO
        String titulo = "BIENVENIDO";
        int cuadroAncho = logoAncho;
        
        System.out.print("\033[35m"); // Magenta
        System.out.print(" ".repeat(startPos));
        System.out.println("-".repeat(cuadroAncho));
        
        int paddingTotal = cuadroAncho - 2 - titulo.length();
        int paddingIzq = paddingTotal / 2;
        int paddingDer = paddingTotal - paddingIzq;
        
        System.out.print(" ".repeat(startPos));
        System.out.println("|" + " ".repeat(paddingIzq) + titulo + " ".repeat(paddingDer) + "|");
        
        System.out.print(" ".repeat(startPos));
        System.out.println("-".repeat(cuadroAncho));
        
        System.out.print("\033[0m"); // Reset
    }
    
    /**
     * Pide credenciales al usuario (diseÃ±o del cliente .NET)
     */
    private static String[] pedirCredenciales(int anchoCuadro) {
        int width = 120;
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        // LÃ­nea superior del cuadro - borde azul
        System.out.print("\033[34m"); // Azul
        System.out.print(" ".repeat(startPos));
        System.out.println("-".repeat(anchoCuadro));
        
        // Primera lÃ­nea interna vacÃ­a
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        System.out.print(" ".repeat(anchoCuadro - 2));
        System.out.println("|");
        
        // Segunda lÃ­nea interna con texto de usuario
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        System.out.print(" ".repeat(2));
        System.out.print("\033[97m"); // Blanco
        String textoUsuario = "Ingrese su usuario: ";
        System.out.print(textoUsuario);
        
        // Entrada usuario en verde
        System.out.print("\033[32m"); // Verde
        String usuario = scanner.nextLine();
        
        // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
        int longitudTotalUsuario = textoUsuario.length() + usuario.length();
        int espaciosRestantesUsuario = anchoCuadro - 2 - 2 - longitudTotalUsuario;
        if (espaciosRestantesUsuario > 0) {
            System.out.print(" ".repeat(espaciosRestantesUsuario));
        }
        System.out.print("\033[34m"); // Azul
        System.out.println("|");
        
        // Tercera lÃ­nea interna con texto de contraseÃ±a
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        System.out.print(" ".repeat(2));
        System.out.print("\033[97m"); // Blanco
        String textoContrasena = "Ingrese su contraseÃ±a: ";
        System.out.print(textoContrasena);
        
        // Entrada contraseÃ±a en verde, oculta con asteriscos
        System.out.print("\033[32m"); // Verde
        String password = leerPassword();
        
        // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
        int longitudTotalPass = textoContrasena.length() + password.length();
        int espaciosRestantesPass = anchoCuadro - 2 - 2 - longitudTotalPass;
        if (espaciosRestantesPass > 0) {
            System.out.print(" ".repeat(espaciosRestantesPass));
        }
        System.out.print("\033[34m"); // Azul
        System.out.println("|");
        
        // LÃ­nea inferior del cuadro - borde azul
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.println("-".repeat(anchoCuadro));
        System.out.print("\033[0m"); // Reset
        
        return new String[]{usuario, password};
    }
    
    /**
     * Lee la contraseÃ±a ocultando los caracteres
     */
    private static String leerPassword() {
        if (System.console() != null) {
            return new String(System.console().readPassword());
        } else {
            return scanner.nextLine();
        }
    }
    
    /**
     * Verifica si la entrada es para cancelar (0 o vacÃ­o)
     * @param entrada La entrada del usuario
     * @return true si se debe cancelar, false en caso contrario
     */
    private static boolean esCancelar(String entrada) {
        if (entrada == null) return true;
        String trim = entrada.trim();
        return trim.equals("0") || trim.equalsIgnoreCase("cancelar") || trim.equalsIgnoreCase("cancel");
    }
    
    /**
     * Muestra un mensaje centrado
     */
    private static void mostrarMensajeCentrado(String mensaje, boolean esExito) {
        int width = 120;
        int posX = (width - mensaje.length()) / 2;
        System.out.print(" ".repeat(posX));
        if (esExito) {
            System.out.print("\033[32m"); // Verde
        } else {
            System.out.print("\033[31m"); // Rojo
        }
        System.out.println(mensaje);
        System.out.print("\033[0m"); // Reset
    }
    
    /**
     * Ejecuta el menÃº principal (diseÃ±o del cliente .NET)
     */
    public static void ejecutarMenu(CliCon_Controlador controlador, int anchoCuadro) {
        int opcion;
        do {
            limpiarPantalla();
            mostrarLogoYBienvenido();
            
            opcion = mostrarMenu(anchoCuadro);
            
            switch (opcion) {
                case 1:
                    consultarMovimientos(controlador);
                    break;
                case 2:
                    realizarDeposito(controlador);
                    break;
                case 3:
                    realizarRetiro(controlador);
                    break;
                case 4:
                    realizarTransferencia(controlador);
                    break;
                case 5:
                    salir();
                    break;
                default:
                    System.out.println("OpciÃ³n no vÃ¡lida. Presione cualquier tecla para continuar...");
                    scanner.nextLine();
                    break;
            }
        } while (opcion != 5);
    }
    
    /**
     * Muestra el menÃº principal (diseÃ±o del cliente .NET)
     */
    public static int mostrarMenu(int anchoCuadro) {
        int width = 120;
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        String[] opciones = {
            "Consultar movimientos",
            "Realizar depÃ³sito",
            "Realizar retiro",
            "Realizar transferencia",
            "Salir"
        };
        
        // Dibujar lÃ­nea superior del cuadro azul
        System.out.print("\033[34m"); // Azul
        System.out.print(" ".repeat(startPos));
        System.out.println("-".repeat(anchoCuadro));
        
        // Primera lÃ­nea interna con tÃ­tulo
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        String titulo = "MENÃ PRINCIPAL";
        int tituloPos = (anchoCuadro - 2 - titulo.length()) / 2;
        System.out.print(" ".repeat(tituloPos));
        System.out.print("\033[34m"); // Azul
        System.out.print(titulo);
        int espaciosRestantesTitulo = anchoCuadro - 2 - tituloPos - titulo.length();
        System.out.print(" ".repeat(espaciosRestantesTitulo));
        System.out.println("|");
        
        // LÃ­nea vacÃ­a
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        System.out.print(" ".repeat(anchoCuadro - 2));
        System.out.println("|");
        
        // Desplazamiento para mover nÃºmeros y texto juntos a la izquierda
        int desplazamientoIzquierda = 10;
        
        // Centro del cuadro (relativo al cuadro, no a la consola)
        int centroCuadro = (anchoCuadro / 2) - desplazamientoIzquierda;
        
        // Ancho fijo para columna de nÃºmeros
        int anchoNumeros = 3;
        int numeroPosRelativo = centroCuadro - (anchoNumeros / 2);
        
        // PosiciÃ³n para texto, manteniendo separaciÃ³n con nÃºmeros
        int textoPosRelativo = numeroPosRelativo + anchoNumeros + 2;
        
        // LÃ­neas con opciones
        for (int i = 0; i < opciones.length; i++) {
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            
            // Espacios antes del nÃºmero (dentro del cuadro)
            System.out.print(" ".repeat(numeroPosRelativo));
            
            // NÃºmero
            System.out.print("\033[35m"); // Magenta
            System.out.print((i + 1) + ".");
            
            // Espacios entre nÃºmero y texto
            int espaciosEntre = textoPosRelativo - numeroPosRelativo - 2;
            System.out.print(" ".repeat(espaciosEntre));
            
            // Texto de opciÃ³n
            System.out.print("\033[37m"); // Gris
            System.out.print(opciones[i]);
            
            // Espacios restantes hasta el borde derecho
            int espaciosRestantes = anchoCuadro - 2 - numeroPosRelativo - 2 - espaciosEntre - opciones[i].length();
            if (espaciosRestantes > 0) {
                System.out.print(" ".repeat(espaciosRestantes));
            }
            
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
        }
        
        // LÃ­nea vacÃ­a
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        System.out.print(" ".repeat(anchoCuadro - 2));
        System.out.println("|");
        
        // LÃ­nea con texto de selecciÃ³n
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.print("|");
        String textoSeleccion = "Seleccione una opciÃ³n: ";
        int seleccionPos = (anchoCuadro - 2 - textoSeleccion.length()) / 2;
        System.out.print(" ".repeat(seleccionPos));
        System.out.print("\033[34m"); // Azul
        System.out.print(textoSeleccion);
        int espaciosRestantesSeleccion = anchoCuadro - 2 - seleccionPos - textoSeleccion.length();
        System.out.print(" ".repeat(espaciosRestantesSeleccion));
        System.out.println("|");
        
        // Dibujar lÃ­nea inferior del cuadro azul
        System.out.print(" ".repeat(startPos));
        System.out.print("\033[34m"); // Azul
        System.out.println("-".repeat(anchoCuadro));
        System.out.print("\033[0m"); // Reset
        
        // ValidaciÃ³n de la opciÃ³n ingresada
        int opcionSeleccionada;
        while (true) {
            String entrada = scanner.nextLine();
            try {
                opcionSeleccionada = Integer.parseInt(entrada);
                if (opcionSeleccionada >= 1 && opcionSeleccionada <= opciones.length) {
                    break;
                }
            } catch (NumberFormatException e) {
                // Continuar el loop
            }
            
            String errorMsg = "OpciÃ³n no vÃ¡lida. Intente de nuevo.";
            int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
            System.out.print(" ".repeat(errorPos));
            System.out.print("\033[31m"); // Rojo
            System.out.println(errorMsg);
            System.out.print("\033[0m"); // Reset
            
            // Reescribir texto selecciÃ³n para nuevo intento
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(seleccionPos));
            System.out.print("\033[34m"); // Azul
            System.out.print(textoSeleccion);
            System.out.print(" ".repeat(espaciosRestantesSeleccion));
            System.out.println("|");
        }
        
        return opcionSeleccionada;
    }
    
    /**
     * Muestra mensaje de salida
     */
    private static void salir() {
        limpiarPantalla();
        mostrarLogoYBienvenido();
        System.out.println();
        System.out.println("Â¡Gracias por usar EurekaBank! Hasta pronto.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Consulta y muestra los movimientos de una cuenta (diseÃ±o del cliente .NET)
     */
    public static void consultarMovimientos(CliCon_Controlador controlador) {
        int anchoCuadro = calcularAnchoLogo();
        int width = 120;
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        while (true) {
            limpiarPantalla();
            mostrarLogoYBienvenido();
            
            // Cuadro azul para el tÃ­tulo y entrada
            System.out.print("\033[34m"); // Azul
            System.out.print(" ".repeat(startPos));
            System.out.println("-".repeat(anchoCuadro));
            
            // Primera lÃ­nea interna con tÃ­tulo
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            String titulo = "MOVIMIENTOS";
            int tituloPos = (anchoCuadro - 2 - titulo.length()) / 2;
            System.out.print(" ".repeat(tituloPos));
            System.out.print("\033[35m"); // Magenta
            System.out.print(titulo);
            int espaciosRestantesTitulo = anchoCuadro - 2 - tituloPos - titulo.length();
            System.out.print(" ".repeat(espaciosRestantesTitulo));
            System.out.println("|");
            
            // LÃ­neas internas vacÃ­as
            for (int i = 0; i < 3; i++) {
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.print("|");
                System.out.print(" ".repeat(anchoCuadro - 2));
                System.out.println("|");
            }
            
            // LÃ­nea con texto de entrada de cuenta
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            String textoCuenta = "Ingrese el numero de cuenta (8 digitos) o 0 para cancelar: ";
            System.out.print(textoCuenta);
            
            System.out.print("\033[32m"); // Verde
            String cuenta = scanner.nextLine();
            
            // Si se ingresÃ³ 0 para cancelar, salir
            if (esCancelar(cuenta)) {
                break;
            }
            
            cuenta = cuenta.trim();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalCuenta = textoCuenta.length() + cuenta.length();
            int espaciosRestantesCuenta = anchoCuadro - 2 - 2 - longitudTotalCuenta;
            if (espaciosRestantesCuenta > 0) {
                System.out.print(" ".repeat(espaciosRestantesCuenta));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            // LÃ­neas internas vacÃ­as
            for (int i = 0; i < 2; i++) {
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.print("|");
                System.out.print(" ".repeat(anchoCuadro - 2));
                System.out.println("|");
            }
            
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.println("-".repeat(anchoCuadro));
            System.out.print("\033[0m"); // Reset
            
            // Validar entrada
            if (cuenta.length() != 8 || !cuenta.matches("\\d{8}")) {
                String errorMsg = "Numero invalido. Debe tener 8 digitos numericos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // Obtener movimientos
            List<Movimiento> movimientos = controlador.traerMovimientos(cuenta);
            
            limpiarPantalla();
            mostrarLogoYBienvenido();
            
            if (movimientos.isEmpty()) {
                String msgNoMovimientos = "No se encontraron movimientos para la cuenta ingresada.";
                int msgPos = startPos + (anchoCuadro - msgNoMovimientos.length()) / 2;
                System.out.print(" ".repeat(msgPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(msgNoMovimientos);
                System.out.print("\033[0m"); // Reset
            } else {
                // Mostrar nÃºmero de cuenta
                String cuentaMsg = "Cuenta consultada: " + cuenta;
                int cuentaMsgPos = startPos + (anchoCuadro - cuentaMsg.length()) / 2;
                System.out.print(" ".repeat(cuentaMsgPos));
                System.out.print("\033[36m"); // Cyan
                System.out.println(cuentaMsg);
                System.out.print("\033[0m"); // Reset
                System.out.println();
                
                // Calcular saldo sumando los movimientos
                double saldoActual = 0.0;
                for (Movimiento mov : movimientos) {
                    if ("INGRESO".equals(mov.getAccion())) {
                        saldoActual += mov.getMoviimporte();
                    } else if ("SALIDA".equals(mov.getAccion())) {
                        saldoActual -= mov.getMoviimporte();
                    }
                }
                
                // Mostrar saldo actual fuera de la tabla, centrado y en amarillo
                String saldoMsg = "Saldo actual: $" + formatoMoneda.format(saldoActual);
                int saldoMsgPos = startPos + (anchoCuadro - saldoMsg.length()) / 2;
                System.out.print(" ".repeat(saldoMsgPos));
                System.out.print("\033[33m"); // Amarillo
                System.out.println(saldoMsg);
                System.out.print("\033[0m"); // Reset
                System.out.println();
                
                MovimientoView.mostrarMovimientos(movimientos, anchoCuadro);
            }
            
            // LÃ­nea azul para separar
            System.out.print("\033[34m"); // Azul
            int cursorTop = 0;
            System.out.print(" ".repeat(startPos));
            System.out.println("-".repeat(anchoCuadro));
            
            String mensajeRegresar = "Presione ENTER para ingresar otra cuenta o 0 para salir...";
            int mensajePos = startPos + (anchoCuadro - mensajeRegresar.length()) / 2;
            System.out.print(" ".repeat(mensajePos));
            System.out.print("\033[0m"); // Reset
            System.out.println(mensajeRegresar);
            
            // Esperar entrada del usuario
            String entrada = scanner.nextLine();
            if (esCancelar(entrada)) {
                break;
            }
            // Si es Enter o cualquier otra cosa, continuar el loop para ingresar otra cuenta
        }
    }
    
    /**
     * Realiza un depÃ³sito en una cuenta (diseÃ±o mejorado estilo .NET)
     */
    public static void realizarDeposito(CliCon_Controlador controlador) {
        int anchoCuadro = calcularAnchoLogo();
        int width = 120;
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        while (true) {
            limpiarPantalla();
            mostrarLogoYBienvenido();
            
            // Cuadro azul para tÃ­tulo y marco
            System.out.print("\033[34m"); // Azul
            System.out.print(" ".repeat(startPos));
            System.out.println("-".repeat(anchoCuadro));
            
            // Primera lÃ­nea interna con tÃ­tulo
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            String titulo = "DEPÃSITO";
            int tituloPos = (anchoCuadro - 2 - titulo.length()) / 2;
            System.out.print(" ".repeat(tituloPos));
            System.out.print("\033[35m"); // Magenta
            System.out.print(titulo);
            int espaciosRestantesTitulo = anchoCuadro - 2 - tituloPos - titulo.length();
            System.out.print(" ".repeat(espaciosRestantesTitulo));
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: NÃºmero de cuenta
            String textoCuenta = "Ingrese el nÃºmero de cuenta: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoCuenta);
            
            System.out.print("\033[32m"); // Verde
            String cuenta = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalCuenta = textoCuenta.length() + cuenta.length();
            int espaciosRestantesCuenta = anchoCuadro - 2 - 2 - longitudTotalCuenta;
            if (espaciosRestantesCuenta > 0) {
                System.out.print(" ".repeat(espaciosRestantesCuenta));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(cuenta)) {
                break; // Cancelar
            }
            
            cuenta = cuenta.trim();
            
            // Validar cuenta
            if (cuenta.length() != 8 || !cuenta.matches("\\d{8}")) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "NÃºmero invÃ¡lido. Debe tener 8 dÃ­gitos numÃ©ricos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: Importe
            String textoImporte = "Ingrese el importe a depositar: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoImporte);
            
            System.out.print("\033[32m"); // Verde
            String importeStr = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalImporte = textoImporte.length() + importeStr.length();
            int espaciosRestantesImporte = anchoCuadro - 2 - 2 - longitudTotalImporte;
            if (espaciosRestantesImporte > 0) {
                System.out.print(" ".repeat(espaciosRestantesImporte));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(importeStr)) {
                break; // Cancelar
            }
            
            importeStr = importeStr.trim().replace(",", ".");
            double importe;
            try {
                importe = Double.parseDouble(importeStr);
                if (importe <= 0) {
                    // LÃ­neas internas vacÃ­as
                    for (int i = 0; i < 2; i++) {
                        System.out.print(" ".repeat(startPos));
                        System.out.print("\033[34m"); // Azul
                        System.out.print("|");
                        System.out.print(" ".repeat(anchoCuadro - 2));
                        System.out.println("|");
                    }
                    
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.println("-".repeat(anchoCuadro));
                    
                    String errorMsg = "Importe invÃ¡lido. Debe ser un nÃºmero mayor a cero.";
                    int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                    System.out.print(" ".repeat(errorPos));
                    System.out.print("\033[31m"); // Rojo
                    System.out.println(errorMsg);
                    System.out.print("\033[0m"); // Reset
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
            } catch (NumberFormatException e) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "Importe invÃ¡lido. Debe ser un nÃºmero vÃ¡lido.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­neas internas vacÃ­as
            for (int i = 0; i < 2; i++) {
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.print("|");
                System.out.print(" ".repeat(anchoCuadro - 2));
                System.out.println("|");
            }
            
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.println("-".repeat(anchoCuadro));
            System.out.print("\033[0m"); // Reset
            
            // Intentar registrar depÃ³sito y mostrar mensaje centrado
            int resultado = controlador.regDeposito(cuenta, importe);
            
            if (resultado == 1) {
                String exitoMsg = "DepÃ³sito realizado exitosamente.";
                int exitoPos = startPos + (anchoCuadro - exitoMsg.length()) / 2;
                System.out.print(" ".repeat(exitoPos));
                System.out.print("\033[32m"); // Verde
                System.out.println(exitoMsg);
            } else {
                String errorMsg = "Error al procesar el depÃ³sito. Verifique los datos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
            }
            
            // Mensaje para continuar o salir
            String mensajeSalir = "Presione ENTER para limpiar y continuar o 0 para regresar al MENÃ PRINCIPAL";
            int mensajePos = (width - mensajeSalir.length()) / 2;
            System.out.println();
            System.out.print(" ".repeat(mensajePos));
            System.out.print("\033[34m"); // Azul
            System.out.println(mensajeSalir);
            System.out.print("\033[0m"); // Reset
            
            String continuar = scanner.nextLine();
            if (esCancelar(continuar)) {
                break; // Cancelar
            }
        }
    }
    
    /**
     * Realiza un retiro de una cuenta (diseÃ±o mejorado estilo .NET)
     */
    public static void realizarRetiro(CliCon_Controlador controlador) {
        int anchoCuadro = calcularAnchoLogo();
        int width = 120;
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        while (true) {
            limpiarPantalla();
            mostrarLogoYBienvenido();
            
            // Cuadro azul para tÃ­tulo y marco
            System.out.print("\033[34m"); // Azul
            System.out.print(" ".repeat(startPos));
            System.out.println("-".repeat(anchoCuadro));
            
            // Primera lÃ­nea interna con tÃ­tulo
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            String titulo = "RETIRO";
            int tituloPos = (anchoCuadro - 2 - titulo.length()) / 2;
            System.out.print(" ".repeat(tituloPos));
            System.out.print("\033[35m"); // Magenta
            System.out.print(titulo);
            int espaciosRestantesTitulo = anchoCuadro - 2 - tituloPos - titulo.length();
            System.out.print(" ".repeat(espaciosRestantesTitulo));
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: NÃºmero de cuenta
            String textoCuenta = "Ingrese el nÃºmero de cuenta: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoCuenta);
            
            System.out.print("\033[32m"); // Verde
            String cuenta = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalCuenta = textoCuenta.length() + cuenta.length();
            int espaciosRestantesCuenta = anchoCuadro - 2 - 2 - longitudTotalCuenta;
            if (espaciosRestantesCuenta > 0) {
                System.out.print(" ".repeat(espaciosRestantesCuenta));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(cuenta)) {
                break; // Cancelar
            }
            
            cuenta = cuenta.trim();
            
            // Validar cuenta
            if (cuenta.length() != 8 || !cuenta.matches("\\d{8}")) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "NÃºmero invÃ¡lido. Debe tener 8 dÃ­gitos numÃ©ricos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: Importe
            String textoImporte = "Ingrese el importe a retirar: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoImporte);
            
            System.out.print("\033[32m"); // Verde
            String importeStr = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalImporte = textoImporte.length() + importeStr.length();
            int espaciosRestantesImporte = anchoCuadro - 2 - 2 - longitudTotalImporte;
            if (espaciosRestantesImporte > 0) {
                System.out.print(" ".repeat(espaciosRestantesImporte));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(importeStr)) {
                break; // Cancelar
            }
            
            importeStr = importeStr.trim().replace(",", ".");
            double importe;
            try {
                importe = Double.parseDouble(importeStr);
                if (importe <= 0) {
                    // LÃ­neas internas vacÃ­as
                    for (int i = 0; i < 2; i++) {
                        System.out.print(" ".repeat(startPos));
                        System.out.print("\033[34m"); // Azul
                        System.out.print("|");
                        System.out.print(" ".repeat(anchoCuadro - 2));
                        System.out.println("|");
                    }
                    
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.println("-".repeat(anchoCuadro));
                    
                    String errorMsg = "Importe invÃ¡lido. Debe ser un nÃºmero mayor a cero.";
                    int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                    System.out.print(" ".repeat(errorPos));
                    System.out.print("\033[31m"); // Rojo
                    System.out.println(errorMsg);
                    System.out.print("\033[0m"); // Reset
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
            } catch (NumberFormatException e) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "Importe invÃ¡lido. Debe ser un nÃºmero vÃ¡lido.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­neas internas vacÃ­as
            for (int i = 0; i < 2; i++) {
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.print("|");
                System.out.print(" ".repeat(anchoCuadro - 2));
                System.out.println("|");
            }
            
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.println("-".repeat(anchoCuadro));
            System.out.print("\033[0m"); // Reset
            
            // Intentar registrar retiro y mostrar mensaje centrado
            int resultado = controlador.regRetiro(cuenta, importe);
            
            if (resultado == 1) {
                String exitoMsg = "Retiro realizado exitosamente.";
                int exitoPos = startPos + (anchoCuadro - exitoMsg.length()) / 2;
                System.out.print(" ".repeat(exitoPos));
                System.out.print("\033[32m"); // Verde
                System.out.println(exitoMsg);
            } else {
                String errorMsg = "Error al procesar el retiro. Verifique los datos y el saldo disponible.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
            }
            
            // Mensaje para continuar o salir
            String mensajeSalir = "Presione ENTER para limpiar y continuar o 0 para regresar al MENÃ PRINCIPAL";
            int mensajePos = (width - mensajeSalir.length()) / 2;
            System.out.println();
            System.out.print(" ".repeat(mensajePos));
            System.out.print("\033[34m"); // Azul
            System.out.println(mensajeSalir);
            System.out.print("\033[0m"); // Reset
            
            String continuar = scanner.nextLine();
            if (esCancelar(continuar)) {
                break; // Cancelar
            }
        }
    }
    
    /**
     * Realiza una transferencia entre cuentas (diseÃ±o mejorado estilo .NET)
     */
    public static void realizarTransferencia(CliCon_Controlador controlador) {
        int anchoCuadro = calcularAnchoLogo();
        int width = 120;
        int startPos = (width - anchoCuadro) / 2;
        if (startPos < 0) startPos = 0;
        
        while (true) {
            limpiarPantalla();
            mostrarLogoYBienvenido();
            
            // Cuadro azul para tÃ­tulo y marco
            System.out.print("\033[34m"); // Azul
            System.out.print(" ".repeat(startPos));
            System.out.println("-".repeat(anchoCuadro));
            
            // Primera lÃ­nea interna con tÃ­tulo
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            String titulo = "TRANSFERENCIA";
            int tituloPos = (anchoCuadro - 2 - titulo.length()) / 2;
            System.out.print(" ".repeat(tituloPos));
            System.out.print("\033[36m"); // Cyan
            System.out.print(titulo);
            int espaciosRestantesTitulo = anchoCuadro - 2 - tituloPos - titulo.length();
            System.out.print(" ".repeat(espaciosRestantesTitulo));
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: Cuenta Origen
            String textoCuentaOrigen = "Ingrese el nÃºmero de cuenta origen: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoCuentaOrigen);
            
            System.out.print("\033[32m"); // Verde
            String cuentaOrigen = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalOrigen = textoCuentaOrigen.length() + cuentaOrigen.length();
            int espaciosRestantesOrigen = anchoCuadro - 2 - 2 - longitudTotalOrigen;
            if (espaciosRestantesOrigen > 0) {
                System.out.print(" ".repeat(espaciosRestantesOrigen));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(cuentaOrigen)) {
                break; // Cancelar
            }
            
            cuentaOrigen = cuentaOrigen.trim();
            
            // Validar cuenta origen
            if (cuentaOrigen.length() != 8 || !cuentaOrigen.matches("\\d{8}")) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "NÃºmero invÃ¡lido. Debe tener 8 dÃ­gitos numÃ©ricos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: Cuenta Destino
            String textoCuentaDestino = "Ingrese el nÃºmero de cuenta destino: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoCuentaDestino);
            
            System.out.print("\033[32m"); // Verde
            String cuentaDestino = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalDestino = textoCuentaDestino.length() + cuentaDestino.length();
            int espaciosRestantesDestino = anchoCuadro - 2 - 2 - longitudTotalDestino;
            if (espaciosRestantesDestino > 0) {
                System.out.print(" ".repeat(espaciosRestantesDestino));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(cuentaDestino)) {
                break; // Cancelar
            }
            
            cuentaDestino = cuentaDestino.trim();
            
            // Validar cuenta destino
            if (cuentaDestino.length() != 8 || !cuentaDestino.matches("\\d{8}")) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "NÃºmero invÃ¡lido. Debe tener 8 dÃ­gitos numÃ©ricos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            if (cuentaOrigen.equals(cuentaDestino)) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "Error: No se puede transferir a la misma cuenta.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­nea vacÃ­a
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(anchoCuadro - 2));
            System.out.println("|");
            
            // Entrada: Importe
            String textoImporte = "Ingrese el importe a transferir: ";
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.print("|");
            System.out.print(" ".repeat(2));
            System.out.print("\033[97m"); // Blanco
            System.out.print(textoImporte);
            
            System.out.print("\033[32m"); // Verde
            String importeStr = scanner.nextLine();
            
            // Calcular espacios restantes para completar la lÃ­nea dentro del cuadro
            int longitudTotalImporte = textoImporte.length() + importeStr.length();
            int espaciosRestantesImporte = anchoCuadro - 2 - 2 - longitudTotalImporte;
            if (espaciosRestantesImporte > 0) {
                System.out.print(" ".repeat(espaciosRestantesImporte));
            }
            System.out.print("\033[34m"); // Azul
            System.out.println("|");
            
            if (esCancelar(importeStr)) {
                break; // Cancelar
            }
            
            importeStr = importeStr.trim().replace(",", ".");
            double importe;
            try {
                importe = Double.parseDouble(importeStr);
                if (importe <= 0) {
                    // LÃ­neas internas vacÃ­as
                    for (int i = 0; i < 2; i++) {
                        System.out.print(" ".repeat(startPos));
                        System.out.print("\033[34m"); // Azul
                        System.out.print("|");
                        System.out.print(" ".repeat(anchoCuadro - 2));
                        System.out.println("|");
                    }
                    
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.println("-".repeat(anchoCuadro));
                    
                    String errorMsg = "Importe invÃ¡lido. Debe ser un nÃºmero mayor a cero.";
                    int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                    System.out.print(" ".repeat(errorPos));
                    System.out.print("\033[31m"); // Rojo
                    System.out.println(errorMsg);
                    System.out.print("\033[0m"); // Reset
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    continue;
                }
            } catch (NumberFormatException e) {
                // LÃ­neas internas vacÃ­as
                for (int i = 0; i < 2; i++) {
                    System.out.print(" ".repeat(startPos));
                    System.out.print("\033[34m"); // Azul
                    System.out.print("|");
                    System.out.print(" ".repeat(anchoCuadro - 2));
                    System.out.println("|");
                }
                
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.println("-".repeat(anchoCuadro));
                
                String errorMsg = "Importe invÃ¡lido. Debe ser un nÃºmero vÃ¡lido.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
                System.out.print("\033[0m"); // Reset
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            
            // LÃ­neas internas vacÃ­as
            for (int i = 0; i < 2; i++) {
                System.out.print(" ".repeat(startPos));
                System.out.print("\033[34m"); // Azul
                System.out.print("|");
                System.out.print(" ".repeat(anchoCuadro - 2));
                System.out.println("|");
            }
            
            System.out.print(" ".repeat(startPos));
            System.out.print("\033[34m"); // Azul
            System.out.println("-".repeat(anchoCuadro));
            System.out.print("\033[0m"); // Reset
            
            // Intentar registrar transferencia y mostrar mensaje centrado
            int resultado = controlador.regTransferencia(cuentaOrigen, cuentaDestino, importe);
            
            if (resultado == 1) {
                String exitoMsg = "Transferencia realizada exitosamente.";
                int exitoPos = startPos + (anchoCuadro - exitoMsg.length()) / 2;
                System.out.print(" ".repeat(exitoPos));
                System.out.print("\033[32m"); // Verde
                System.out.println(exitoMsg);
            } else {
                String errorMsg = "Error al procesar la transferencia. Verifique los datos.";
                int errorPos = startPos + (anchoCuadro - errorMsg.length()) / 2;
                System.out.print(" ".repeat(errorPos));
                System.out.print("\033[31m"); // Rojo
                System.out.println(errorMsg);
            }
            
            // Mensaje para continuar o salir
            String mensajeSalir = "Presione ENTER para limpiar y continuar o 0 para regresar al MENÃ PRINCIPAL";
            int mensajePos = (width - mensajeSalir.length()) / 2;
            System.out.println();
            System.out.print(" ".repeat(mensajePos));
            System.out.print("\033[34m"); // Azul
            System.out.println(mensajeSalir);
            System.out.print("\033[0m"); // Reset
            
            String continuar = scanner.nextLine();
            if (esCancelar(continuar)) {
                break; // Cancelar
            }
        }
    }
    
    // ==================== MÃTODOS DE UTILIDAD ====================
    
    /**
     * Limpia la pantalla de consola
     */
    public static void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    // ==================== DESPEDIDA ====================
    
    /**
     * Muestra mensaje de despedida (diseÃ±o simple)
     */
    private static void mostrarDespedida() {
        limpiarPantalla();
        mostrarLogoYBienvenido();
        System.out.println();
        System.out.println("Â¡Gracias por usar EurekaBank! Hasta pronto.");
    }
}
