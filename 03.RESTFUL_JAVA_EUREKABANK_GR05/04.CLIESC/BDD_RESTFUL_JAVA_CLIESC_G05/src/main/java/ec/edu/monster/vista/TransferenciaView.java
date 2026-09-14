/*
 * Vista para realizar transferencias - Diseño mejorado
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
 * Vista para realizar transferencias entre cuentas con diseño mejorado
 */
public class TransferenciaView {
    
    private static final String COLOR_PRIMARY = "#3ab4d9";
    private static final String COLOR_YELLOW = "#f6de88";
    private static final String COLOR_BACKGROUND_LIGHT = "#afe0f8";
    private static final String COLOR_TEXT_DARK = "#0f171a";
    
    private Desktop_Controlador controlador;
    
    public void mostrar(Stage stage, Desktop_Controlador controlador) {
        this.controlador = controlador;
        
        stage.setTitle("EurekaBank - Realizar Transferencia");
        stage.setWidth(1000);
        stage.setHeight(800);
        
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
        content.setMaxWidth(700);
        
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
        iconCircle.setFill(javafx.scene.paint.Color.web(COLOR_YELLOW, 0.2));
        iconCircle.setStroke(javafx.scene.paint.Color.web(COLOR_YELLOW));
        iconCircle.setStrokeWidth(3);
        
        StackPane iconPane = new StackPane(iconCircle);
        iconPane.setAlignment(Pos.CENTER);
        iconPane.setPadding(new Insets(0, 0, 5, 0));
        
        // Título (más pequeño)
        Label title = new Label("Realizar Transferencia");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: 900; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        Label subtitle = new Label("Envía dinero a otras cuentas de forma segura");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #6b7280;");
        
        // Formulario
        VBox form = new VBox(16);
        form.setPrefWidth(600);
        
        // Campo Cuenta Origen
        VBox cuentaOrigenBox = new VBox(8);
        Label cuentaOrigenLabel = new Label("Cuenta Origen");
        cuentaOrigenLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtCuentaOrigen = new TextField();
        txtCuentaOrigen.setPromptText("Ingrese el número de cuenta origen");
        txtCuentaOrigen.setStyle(
            "-fx-font-size: 15px; " +
            "-fx-pref-height: 46px; " +
            "-fx-padding: 10px 16px; " +
            "-fx-background-color: #f6f7f8; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);"
        );
        
        cuentaOrigenBox.getChildren().addAll(cuentaOrigenLabel, txtCuentaOrigen);
        
        // Campo Cuenta Destino
        VBox cuentaDestinoBox = new VBox(8);
        Label cuentaDestinoLabel = new Label("Cuenta Destino");
        cuentaDestinoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtCuentaDestino = new TextField();
        txtCuentaDestino.setPromptText("Ingrese el número de cuenta destino");
        txtCuentaDestino.setStyle(
            "-fx-font-size: 15px; " +
            "-fx-pref-height: 46px; " +
            "-fx-padding: 10px 16px; " +
            "-fx-background-color: #f6f7f8; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);"
        );
        
        cuentaDestinoBox.getChildren().addAll(cuentaDestinoLabel, txtCuentaDestino);
        
        // Campo Importe
        VBox importeBox = new VBox(8);
        Label importeLabel = new Label("Importe");
        importeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtImporte = new TextField();
        txtImporte.setPromptText("Ingrese el importe a transferir");
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
        
        // Botón de transferencia (más pequeño)
        Button btnTransferir = new Button("Realizar Transferencia");
        btnTransferir.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 600px; " +
            "-fx-pref-height: 48px; " +
            "-fx-background-color: " + COLOR_YELLOW + "; " +
            "-fx-text-fill: " + COLOR_TEXT_DARK + "; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(246,222,136,0.3), 8, 0, 0, 4);"
        );
        
        btnTransferir.setOnMouseEntered(e -> btnTransferir.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 600px; " +
            "-fx-pref-height: 48px; " +
            "-fx-background-color: #e5c868; " +
            "-fx-text-fill: " + COLOR_TEXT_DARK + "; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(246,222,136,0.3), 8, 0, 0, 4);"
        ));
        btnTransferir.setOnMouseExited(e -> btnTransferir.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 600px; " +
            "-fx-pref-height: 48px; " +
            "-fx-background-color: " + COLOR_YELLOW + "; " +
            "-fx-text-fill: " + COLOR_TEXT_DARK + "; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(246,222,136,0.3), 8, 0, 0, 4);"
        ));
        
        btnTransferir.setOnAction(e -> {
            String cuentaOrigen = txtCuentaOrigen.getText().trim();
            String cuentaDestino = txtCuentaDestino.getText().trim();
            String importeStr = txtImporte.getText().trim();
            
            if (cuentaOrigen.isEmpty() || cuentaDestino.isEmpty() || importeStr.isEmpty()) {
                mostrarError("Por favor, complete todos los campos.");
                return;
            }
            
            if (cuentaOrigen.equals(cuentaDestino)) {
                mostrarError("No se puede transferir a la misma cuenta.");
                return;
            }
            
            try {
                double importe = Double.parseDouble(importeStr.replace(",", "."));
                if (importe <= 0) {
                    mostrarError("El importe debe ser mayor a cero.");
                    return;
                }
                
                int resultado = controlador.regTransferencia(cuentaOrigen, cuentaDestino, importe);
                if (resultado == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Éxito");
                    alert.setHeaderText(null);
                    alert.setContentText("Transferencia realizada exitosamente.");
                    alert.showAndWait();
                    
                    // Limpiar campos
                    txtCuentaOrigen.clear();
                    txtCuentaDestino.clear();
                    txtImporte.clear();
                } else {
                    mostrarError("Error al procesar la transferencia. Verifique los datos.");
                }
            } catch (NumberFormatException ex) {
                mostrarError("Formato inválido. Ingrese un número válido.");
            }
        });
        
        form.getChildren().addAll(cuentaOrigenBox, cuentaDestinoBox, importeBox, btnTransferir);
        
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
