/*
 * Vista del Menú Principal - EurekaBank - Diseño Dashboard Mejorado
 * @author Grupo Monster G09
 */
package ec.edu.monster.vista;

import ec.edu.monster.controlador.Desktop_Controlador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Vista del menú principal del banco con diseño tipo dashboard mejorado
 */
public class MenuView {
    
    private static final String COLOR_PRIMARY = "#3ab4d9";
    private static final String COLOR_CORAL = "#f67e80";
    private static final String COLOR_YELLOW = "#f6de88";
    private static final String COLOR_PURPLE = "#9e7cc5";
    private static final String COLOR_LIGHT_BLUE = "#afe0f8";
    private static final String COLOR_BACKGROUND_LIGHT = "#afe0f8";
    private static final String COLOR_TEXT_DARK = "#0f171a";
    
    private Stage stage;
    private Desktop_Controlador controlador;
    
    public void mostrar(Stage primaryStage) {
        this.stage = primaryStage;
        this.controlador = new Desktop_Controlador();
        
        stage.setTitle("EurekaBank - Dashboard");
        stage.setWidth(1400);
        stage.setHeight(900);
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BACKGROUND_LIGHT + ";");
        
        // Header mejorado
        HBox header = crearHeader();
        root.setTop(header);
        
        // Contenido principal tipo dashboard
        VBox content = crearContenidoDashboard();
        root.setCenter(content);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
    
    /**
     * Crea el header mejorado tipo dashboard
     */
    private HBox crearHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(12, 40, 12, 40));
        header.setStyle(
            "-fx-background-color: rgba(255,255,255,0.5); " +
            "-fx-background-radius: 0; " +
            "-fx-border-color: rgba(255,255,255,0.3); " +
            "-fx-border-width: 0 0 1 0;"
        );
        header.setAlignment(Pos.CENTER_LEFT);
        
        // Logo con icono
        HBox logoBox = new HBox(12);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        
        Label logo = new Label("EurekaBank");
        logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        logoBox.getChildren().add(logo);
        
        // Spacer
        HBox spacer = new HBox();
        javafx.scene.layout.Region region = new javafx.scene.layout.Region();
        HBox.setHgrow(region, javafx.scene.layout.Priority.ALWAYS);
        spacer.getChildren().add(region);
        
        // Botón Salir
        Button btnSalir = new Button("Salir");
        btnSalir.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-pref-width: 80px; " +
            "-fx-pref-height: 36px; " +
            "-fx-background-color: " + COLOR_CORAL + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 6px; " +
            "-fx-cursor: hand;"
        );
        btnSalir.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.mostrar(stage);
        });
        
        header.getChildren().addAll(logoBox, spacer, btnSalir);
        
        return header;
    }
    
    /**
     * Crea el contenido principal tipo dashboard con 4 tarjetas
     */
    private VBox crearContenidoDashboard() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 40, 30, 40));
        content.setStyle("-fx-background-color: " + COLOR_BACKGROUND_LIGHT + ";");
        
        // Título de bienvenida
        VBox titleBox = new VBox(6);
        titleBox.setAlignment(Pos.CENTER);
        
        Label title = new Label("Bienvenido a EurekaBank");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: 900; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        Label subtitle = new Label("Selecciona una operación para continuar");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #4a5568;");
        
        titleBox.getChildren().addAll(title, subtitle);
        
        // Grid de 2x2 para las 4 tarjetas
        GridPane cardsGrid = new GridPane();
        cardsGrid.setAlignment(Pos.CENTER);
        cardsGrid.setHgap(20);
        cardsGrid.setVgap(20);
        cardsGrid.setPadding(new Insets(20, 0, 0, 0));
        
        // Fila 1
        VBox cardMovimientos = crearCardAccion(
            "Consultar Historial",
            COLOR_PRIMARY,
            "Ver el historial de transacciones de tus cuentas",
            "📊"
        );
        Button btnMovimientos = (Button) cardMovimientos.getChildren().get(3);
        btnMovimientos.setOnAction(e -> {
            MovimientosView movimientosView = new MovimientosView();
            movimientosView.mostrar(stage, controlador);
        });
        
        VBox cardDeposito = crearCardAccion(
            "Realizar Depósito",
            COLOR_PURPLE,
            "Agregar fondos a tu cuenta bancaria",
            "💰"
        );
        Button btnDeposito = (Button) cardDeposito.getChildren().get(3);
        btnDeposito.setOnAction(e -> {
            DepositoView depositoView = new DepositoView();
            depositoView.mostrar(stage, controlador);
        });
        
        // Fila 2
        VBox cardRetiro = crearCardAccion(
            "Realizar Retiro",
            COLOR_CORAL,
            "Retirar dinero de tu cuenta bancaria",
            "💸"
        );
        Button btnRetiro = (Button) cardRetiro.getChildren().get(3);
        btnRetiro.setOnAction(e -> {
            RetiroView retiroView = new RetiroView();
            retiroView.mostrar(stage, controlador);
        });
        
        VBox cardTransferencia = crearCardAccion(
            "Realizar Transferencia",
            COLOR_YELLOW,
            "Enviar dinero a otras cuentas",
            "🔄"
        );
        Button btnTransferencia = (Button) cardTransferencia.getChildren().get(3);
        btnTransferencia.setOnAction(e -> {
            TransferenciaView transferenciaView = new TransferenciaView();
            transferenciaView.mostrar(stage, controlador);
        });
        
        // Agregar tarjetas al grid
        cardsGrid.add(cardMovimientos, 0, 0);
        cardsGrid.add(cardDeposito, 1, 0);
        cardsGrid.add(cardRetiro, 0, 1);
        cardsGrid.add(cardTransferencia, 1, 1);
        
        content.getChildren().addAll(titleBox, cardsGrid);
        content.setAlignment(Pos.TOP_CENTER);
        
        return content;
    }
    
    /**
     * Crea una tarjeta de acción tipo dashboard mejorada (más compacta)
     */
    private VBox crearCardAccion(String titulo, String color, String descripcion, String icono) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setPrefWidth(300);
        card.setPrefHeight(260);
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 18px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 15, 0, 0, 5);"
        );
        card.setAlignment(Pos.CENTER);
        
        // Icono con emoji o texto grande (más pequeño)
        Label iconLabel = new Label(icono);
        iconLabel.setStyle(
            "-fx-font-size: 48px; " +
            "-fx-padding: 0;"
        );
        iconLabel.setAlignment(Pos.CENTER);
        
        // Contenedor para el icono con fondo circular (más pequeño)
        StackPane iconContainer = new StackPane();
        Circle iconCircle = new Circle(40);
        iconCircle.setFill(Color.web(color, 0.15));
        iconCircle.setStroke(Color.web(color));
        iconCircle.setStrokeWidth(2);
        iconContainer.getChildren().addAll(iconCircle, iconLabel);
        iconContainer.setAlignment(Pos.CENTER);
        
        // Título (asegurar que se vea completo)
        Label titleLabel = new Label(titulo);
        titleLabel.setStyle(
            "-fx-font-size: 19px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: " + COLOR_TEXT_DARK + "; " +
            "-fx-wrap-text: true; " +
            "-fx-text-alignment: center;"
        );
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(250);
        
        // Descripción (más compacta)
        Label descLabel = new Label(descripcion);
        descLabel.setStyle(
            "-fx-font-size: 13px; " +
            "-fx-text-fill: #6b7280; " +
            "-fx-wrap-text: true; " +
            "-fx-text-alignment: center;"
        );
        descLabel.setAlignment(Pos.CENTER);
        descLabel.setMaxWidth(250);
        
        // Botón de acción (más pequeño)
        Button btnAccion = new Button("Continuar");
        btnAccion.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 250px; " +
            "-fx-pref-height: 42px; " +
            "-fx-background-color: " + color + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 10px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 3);"
        );
        btnAccion.setOnMouseEntered(e -> {
            String darker = oscurecerColor(color);
            btnAccion.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 250px; " +
                "-fx-pref-height: 42px; " +
                "-fx-background-color: " + darker + "; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 10px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 3);"
            );
        });
        btnAccion.setOnMouseExited(e -> {
            btnAccion.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-pref-width: 250px; " +
                "-fx-pref-height: 42px; " +
                "-fx-background-color: " + color + "; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 10px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 3);"
            );
        });
        
        card.getChildren().addAll(iconContainer, titleLabel, descLabel, btnAccion);
        
        return card;
    }
    
    /**
     * Oscurece un color hexadecimal
     */
    private String oscurecerColor(String color) {
        if (color.equals(COLOR_PRIMARY)) return "#2a9fc4";
        if (color.equals(COLOR_PURPLE)) return "#7d5fa0";
        if (color.equals(COLOR_CORAL)) return "#e05d5f";
        if (color.equals(COLOR_YELLOW)) return "#e5c868";
        return color;
    }
    
    /**
     * Muestra un mensaje informativo
     */
    private void mostrarMensaje(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
