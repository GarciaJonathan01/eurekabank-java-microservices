/*
 * Vista para realizar depósitos - Diseño mejorado
 * @author Grupo Monster G09
 */
package ec.edu.monster.vista;

import ec.edu.monster.controlador.Desktop_Controlador;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Vista para realizar depósitos con diseño mejorado
 */
public class DepositoView {
    
    private static final String COLOR_PRIMARY = "#3ab4d9";
    private static final String COLOR_PURPLE = "#9e7cc5";
    private static final String COLOR_BACKGROUND_LIGHT = "#afe0f8";
    private static final String COLOR_TEXT_DARK = "#0f171a";
    
    private Desktop_Controlador controlador;
    
    public void mostrar(Stage stage, Desktop_Controlador controlador) {
        this.controlador = controlador;
        
        stage.setTitle("EurekaBank - Realizar Depósito");
        stage.setWidth(900);
        stage.setHeight(700);
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BACKGROUND_LIGHT + ";");
        
        // Header
        HBox header = crearHeader(stage);
        root.setTop(header);
        
        // Contenido
        VBox content = crearContenido(stage);
        root.setCenter(content);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    /**
     * Crea el header mejorado
     */
    private HBox crearHeader(Stage stage) {
        HBox header = new HBox(20);
        header.setPadding(new Insets(12, 40, 12, 40));
        header.setStyle(
            "-fx-background-color: rgba(255,255,255,0.5); " +
            "-fx-border-color: rgba(255,255,255,0.3); " +
            "-fx-border-width: 0 0 1 0;"
        );
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label logo = new Label("EurekaBank");
        logo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        HBox spacer = new HBox();
        javafx.scene.layout.Region region = new javafx.scene.layout.Region();
        HBox.setHgrow(region, javafx.scene.layout.Priority.ALWAYS);
        spacer.getChildren().add(region);
        
        Button btnVolver = new Button("Volver");
        btnVolver.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-pref-width: 80px; " +
            "-fx-pref-height: 36px; " +
            "-fx-background-color: " + COLOR_PRIMARY + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 6px; " +
            "-fx-cursor: hand;"
        );
        btnVolver.setOnAction(e -> {
            MenuView menuView = new MenuView();
            menuView.mostrar(stage);
        });
        
        header.getChildren().addAll(logo, spacer, btnVolver);
        
        return header;
    }
    
    /**
     * Crea el contenido del formulario mejorado
     */
    private VBox crearContenido(Stage stage) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20, 40, 20, 40));
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(600);
        
        // Tarjeta principal
        VBox card = new VBox(20);
        card.setPadding(new Insets(30, 40, 30, 40));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 16px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 12, 0, 0, 4);"
        );
        card.setAlignment(Pos.CENTER);
        
        // Icono decorativo (más pequeño)
        javafx.scene.shape.Circle iconCircle = new javafx.scene.shape.Circle(35);
        iconCircle.setFill(javafx.scene.paint.Color.web(COLOR_PURPLE, 0.2));
        iconCircle.setStroke(javafx.scene.paint.Color.web(COLOR_PURPLE));
        iconCircle.setStrokeWidth(3);
        
        StackPane iconPane = new StackPane(iconCircle);
        iconPane.setAlignment(Pos.CENTER);
        iconPane.setPadding(new Insets(0, 0, 5, 0));
        
        // Título (más pequeño)
        Label title = new Label("Realizar Depósito");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        Label subtitle = new Label("Agrega fondos a tu cuenta bancaria");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        
        // Formulario
        VBox form = new VBox(16);
        form.setPrefWidth(500);
        
        // Campo Cuenta
        VBox cuentaBox = new VBox(8);
        Label cuentaLabel = new Label("Número de cuenta");
        cuentaLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtCuenta = new TextField();
        txtCuenta.setPromptText("Ingrese el número de cuenta");
        txtCuenta.setStyle(
            "-fx-font-size: 15px; " +
            "-fx-pref-height: 46px; " +
            "-fx-padding: 10px 16px; " +
            "-fx-background-color: #f6f7f8; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);"
        );
        
        cuentaBox.getChildren().addAll(cuentaLabel, txtCuenta);
        
        // Campo Importe
        VBox importeBox = new VBox(8);
        Label importeLabel = new Label("Importe");
        importeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtImporte = new TextField();
        txtImporte.setPromptText("Ingrese el importe a depositar");
        txtImporte.setStyle(
            "-fx-font-size: 15px; " +
            "-fx-pref-height: 46px; " +
            "-fx-padding: 10px 16px; " +
            "-fx-background-color: #f6f7f8; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);"
        );
        
        importeBox.getChildren().addAll(importeLabel, txtImporte);
        
        // Botón de depósito (más pequeño)
        Button btnDepositar = new Button("Realizar Depósito");
        btnDepositar.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 500px; " +
            "-fx-pref-height: 48px; " +
            "-fx-background-color: " + COLOR_PURPLE + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(158,124,197,0.3), 8, 0, 0, 4);"
        );
        
        btnDepositar.setOnMouseEntered(e -> btnDepositar.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 500px; " +
            "-fx-pref-height: 48px; " +
            "-fx-background-color: #7d5fa0; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(158,124,197,0.3), 8, 0, 0, 4);"
        ));
        btnDepositar.setOnMouseExited(e -> btnDepositar.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 500px; " +
            "-fx-pref-height: 48px; " +
            "-fx-background-color: " + COLOR_PURPLE + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(158,124,197,0.3), 8, 0, 0, 4);"
        ));
        
        btnDepositar.setOnAction(e -> {
            String cuenta = txtCuenta.getText().trim();
            String importeStr = txtImporte.getText().trim();
            
            if (cuenta.isEmpty() || importeStr.isEmpty()) {
                mostrarError("Por favor, complete todos los campos.");
                return;
            }
            
            try {
                double importe = Double.parseDouble(importeStr.replace(",", "."));
                if (importe <= 0) {
                    mostrarError("El importe debe ser mayor a cero.");
                    return;
                }
                
                int resultado = controlador.regDeposito(cuenta, importe);
                if (resultado == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText(null);
                    alert.setContentText("Depósito realizado exitosamente.");
                    alert.showAndWait();
                    
                    // Limpiar campos
                    txtCuenta.clear();
                    txtImporte.clear();
                } else {
                    mostrarError("Error al procesar el depósito. Verifique los datos.");
                }
            } catch (NumberFormatException ex) {
                mostrarError("Formato inválido. Ingrese un número válido.");
            }
        });
        
        form.getChildren().addAll(cuentaBox, importeBox, btnDepositar);
        
        card.getChildren().addAll(iconPane, title, subtitle, form);
        
        content.getChildren().addAll(card);
        
        return content;
    }
    
    /**
     * Muestra un mensaje de error
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
