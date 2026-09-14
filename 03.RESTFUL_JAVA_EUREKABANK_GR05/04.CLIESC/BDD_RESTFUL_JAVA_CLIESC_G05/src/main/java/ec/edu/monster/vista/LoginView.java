/*
 * Vista de Login - Diseño mejorado con imagen
 * @author Grupo Monster G09
 */
package ec.edu.monster.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Vista de login con diseño moderno mejorado
 */
public class LoginView {
    
    // Paleta de colores del HTML
    private static final String COLOR_PRIMARY = "#3ab4d9";
    private static final String COLOR_CORAL = "#f67e80";
    private static final String COLOR_YELLOW = "#f6de88";
    private static final String COLOR_PURPLE = "#9e7cc5";
    private static final String COLOR_LIGHT_BLUE = "#afe0f8";
    private static final String COLOR_BACKGROUND_LIGHT = "#f6f7f8";
    private static final String COLOR_TEXT_DARK = "#0f171a";
    
    // Credenciales
    private static final String USUARIO = "MONSTER";
    private static final String PASS = "6C3F6757E773775FD059E2F025BD14BA";
    
    private Stage stage;
    
    public void mostrar(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("EurekaBank - Iniciar Sesión");
        stage.setMinWidth(1200);
        stage.setMinHeight(700);
        
        // Contenedor principal horizontal
        HBox root = new HBox();
        root.setPrefSize(1200, 800);
        
        // Panel izquierdo con imagen decorativa
        StackPane leftPanel = crearPanelIzquierdo();
        leftPanel.setPrefWidth(500);
        leftPanel.setStyle("-fx-background-color: " + COLOR_LIGHT_BLUE + ";");
        
        // Panel derecho con formulario (centrado, sin espacio extra)
        VBox rightPanel = crearPanelDerecho();
        rightPanel.setPrefWidth(500);
        rightPanel.setStyle("-fx-background-color: " + COLOR_BACKGROUND_LIGHT + ";");
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPadding(new Insets(60, 20, 60, 40));
        
        root.getChildren().addAll(leftPanel, rightPanel);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
    /**
     * Crea el panel izquierdo con imagen decorativa
     */
    private StackPane crearPanelIzquierdo() {
        StackPane panel = new StackPane();
        panel.setPadding(new Insets(40));
        
        // Círculos decorativos
        Circle circle1 = new Circle(144);
        circle1.setFill(Color.web(COLOR_CORAL, 0.5));
        circle1.setTranslateX(-200);
        circle1.setTranslateY(-200);
        
        Circle circle2 = new Circle(192);
        circle2.setFill(Color.web(COLOR_PURPLE, 0.4));
        circle2.setTranslateX(200);
        circle2.setTranslateY(200);
        
        Circle circle3 = new Circle(120);
        circle3.setFill(Color.web(COLOR_YELLOW, 0.5));
        circle3.setTranslateX(-280);
        circle3.setTranslateY(100);
        
        // Contenedor para imagen y contenido
        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(500);
        
        // Imagen decorativa (usando un SVG o imagen placeholder)
        // En JavaFX podemos usar un ImageView con una imagen o crear un icono
        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(0.9);
        
        // Intentar cargar una imagen, si no existe usar un placeholder
        try {
            // La imagen debe estar en: src/main/resources/images/logo.png
            Image image = new Image(getClass().getResourceAsStream("/images/logo.png"));
            imageView.setImage(image);
        } catch (Exception e) {
            // Si no hay imagen, no mostrar nada (se verá solo el texto)
            imageView.setVisible(false);
        }
        
        // Logo y título
        VBox textContent = new VBox(20);
        textContent.setAlignment(Pos.CENTER);
        
        Label logoText = new Label("EurekaBank");
        logoText.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        Label title = new Label("Tu banca, más cerca que nunca.");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        title.setWrapText(true);
        title.setAlignment(Pos.CENTER);
        
        Label subtitle = new Label("Gestiona tus finanzas de forma segura, rápida y sencilla desde cualquier lugar.");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568;");
        subtitle.setWrapText(true);
        subtitle.setAlignment(Pos.CENTER);
        
        textContent.getChildren().addAll(logoText, title, subtitle);
        
        content.getChildren().addAll(imageView, textContent);
        
        panel.getChildren().addAll(circle1, circle2, circle3, content);
        
        return panel;
    }
    
    /**
     * Crea el panel derecho con el formulario de login (más centrado)
     */
    private VBox crearPanelDerecho() {
        VBox panel = new VBox(30);
        panel.setMaxWidth(500);
        panel.setPrefWidth(500);
        
        // Título y subtítulo
        VBox header = new VBox(8);
        Label title = new Label("Bienvenido de vuelta");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: 900; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        Label subtitle = new Label("Ingresa a tu cuenta para gestionar tus finanzas.");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #6b7280;");
        
        header.getChildren().addAll(title, subtitle);
        
        // Formulario
        VBox form = new VBox(20);
        
        // Campo Usuario
        VBox usuarioBox = new VBox(8);
        Label usuarioLabel = new Label("Usuario");
        usuarioLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingresa tu usuario");
        txtUsuario.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-pref-height: 56px; " +
            "-fx-padding: 12px 16px; " +
            "-fx-background-color: white; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);"
        );
        
        usuarioBox.getChildren().addAll(usuarioLabel, txtUsuario);
        
        // Campo Contraseña
        VBox passwordBox = new VBox(8);
        Label passwordLabel = new Label("Contraseña");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Ingresa tu contraseña");
        txtPassword.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-pref-height: 56px; " +
            "-fx-padding: 12px 16px; " +
            "-fx-background-color: white; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);"
        );
        
        passwordBox.getChildren().addAll(passwordLabel, txtPassword);
        
        // Botón de login
        Button btnLogin = new Button("Iniciar Sesión");
        btnLogin.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 420px; " +
            "-fx-pref-height: 52px; " +
            "-fx-background-color: " + COLOR_PRIMARY + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(58,180,217,0.3), 8, 0, 0, 4);"
        );
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 420px; " +
            "-fx-pref-height: 52px; " +
            "-fx-background-color: #2a9fc4; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(58,180,217,0.3), 8, 0, 0, 4);"
        ));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 420px; " +
            "-fx-pref-height: 52px; " +
            "-fx-background-color: " + COLOR_PRIMARY + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(58,180,217,0.3), 8, 0, 0, 4);"
        ));
        
        // Acción del botón
        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText().trim();
            String password = txtPassword.getText();
            
            if (usuario.isEmpty() || password.isEmpty()) {
                mostrarError("Por favor, complete todos los campos.");
                return;
            }
            
            // Validar contra el Microservicio de Autenticación
            ec.edu.monster.servicio.EurekaService service = new ec.edu.monster.servicio.EurekaService();
            boolean authOk = service.autenticar(usuario, password);
            if (authOk) {
                // Abrir menú principal
                MenuView menuView = new MenuView();
                menuView.mostrar(stage);
            } else {
                mostrarError("Error de Autenticación: Credenciales incorrectas o Microservicio de Autenticación fuera de línea (Undeployed).");
            }
        });
        
        form.getChildren().addAll(usuarioBox, passwordBox, btnLogin);
        
        // Footer
        VBox footer = new VBox(8);
        footer.setAlignment(Pos.CENTER);
        Label copyright = new Label("© 2026 EurekaBank. Todos los derechos reservados.");
        copyright.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        
        HBox links = new HBox(8);
        links.setAlignment(Pos.CENTER);
        Hyperlink link1 = new Hyperlink("Ayuda");
        Hyperlink link2 = new Hyperlink("Seguridad");
        Hyperlink link3 = new Hyperlink("Términos");
        link1.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        link2.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        link3.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        links.getChildren().addAll(link1, new Label("•"), link2, new Label("•"), link3);
        
        footer.getChildren().addAll(copyright, links);
        
        panel.getChildren().addAll(header, form, footer);
        
        return panel;
    }
    
    /**
     * Calcula el hash MD5 de una cadena
     */
    private String calcularMD5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
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
            return input;
        }
    }
    
    /**
     * Muestra un mensaje de error
     */
    private void mostrarError(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
