/*
 * Vista para consultar movimientos bancarios - Diseño mejorado
 * @author Grupo Monster G09
 */
package ec.edu.monster.vista;

import ec.edu.monster.controlador.Desktop_Controlador;
import ec.edu.monster.modelo.Movimiento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Vista para consultar movimientos de una cuenta con diseño mejorado
 */
public class MovimientosView {
    
    private static final String COLOR_PRIMARY = "#3ab4d9";
    private static final String COLOR_BACKGROUND_LIGHT = "#afe0f8";
    private static final String COLOR_TEXT_DARK = "#0f171a";
    private static final String COLOR_INGRESO = "#10b981"; // Verde
    private static final String COLOR_SALIDA = "#ef4444"; // Rojo
    
    private Desktop_Controlador controlador;
    
    public void mostrar(Stage stage, Desktop_Controlador controlador) {
        this.controlador = controlador;
        
        stage.setTitle("EurekaBank - Consultar Movimientos");
        stage.setWidth(1400);
        stage.setHeight(900);
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BACKGROUND_LIGHT + ";");
        
        // Header
        HBox header = crearHeader(stage);
        root.setTop(header);
        
        // Contenido
        VBox content = crearContenido();
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
     * Crea el contenido principal mejorado
     */
    private VBox crearContenido() {
        VBox content = new VBox(25);
        content.setPadding(new Insets(40));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Título
        Label title = new Label("Consultar Movimientos");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: 900; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        // Campo de búsqueda mejorado
        HBox searchBox = new HBox(15);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(20));
        searchBox.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"
        );
        
        Label label = new Label("Número de cuenta:");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: 500; -fx-text-fill: " + COLOR_TEXT_DARK + ";");
        
        TextField txtCuenta = new TextField();
        txtCuenta.setPromptText("Ingrese el número de cuenta");
        txtCuenta.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-pref-width: 300px; " +
            "-fx-pref-height: 44px; " +
            "-fx-padding: 8px 16px; " +
            "-fx-background-color: #f6f7f8; " +
            "-fx-border-color: #d1d5db; " +
            "-fx-border-radius: 8px; " +
            "-fx-background-radius: 8px;"
        );
        
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-pref-width: 140px; " +
            "-fx-pref-height: 44px; " +
            "-fx-background-color: " + COLOR_PRIMARY + "; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 8px; " +
            "-fx-cursor: hand; " +
            "-fx-effect: dropshadow(gaussian, rgba(58,180,217,0.3), 6, 0, 0, 2);"
        );
        
        // Tabla de movimientos
        TableView<Movimiento> tabla = crearTabla();
        
        btnBuscar.setOnAction(e -> {
            String cuenta = txtCuenta.getText().trim();
            if (cuenta.isEmpty()) {
                mostrarError("Por favor, ingrese un número de cuenta.");
                return;
            }
            
            List<Movimiento> movimientos = controlador.traerMovimientos(cuenta);
            ObservableList<Movimiento> data = FXCollections.observableArrayList(movimientos);
            tabla.setItems(data);
            
            if (movimientos.isEmpty()) {
                mostrarMensaje("No se encontraron movimientos para la cuenta: " + cuenta);
            }
        });
        
        searchBox.getChildren().addAll(label, txtCuenta, btnBuscar);
        
        // Contenedor para la tabla que ocupe todo el ancho
        VBox tablaContainer = new VBox();
        tablaContainer.setAlignment(Pos.CENTER);
        tablaContainer.getChildren().add(tabla);
        
        content.getChildren().addAll(title, searchBox, tablaContainer);
        
        return content;
    }
    
    /**
     * Crea la tabla de movimientos con colores mejorados
     */
    private TableView<Movimiento> crearTabla() {
        TableView<Movimiento> tabla = new TableView<>();
        tabla.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 12, 0, 0, 4);"
        );
        tabla.setPrefHeight(550);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabla.setPadding(new Insets(10));
        
        // Columna Cuenta
        TableColumn<Movimiento, String> colCuenta = new TableColumn<>("Cuenta");
        colCuenta.setCellValueFactory(cellData -> {
            String cuenta = cellData.getValue().getCuencodigo();
            return new javafx.beans.property.SimpleStringProperty(cuenta != null ? cuenta : "");
        });
        colCuenta.setPrefWidth(140);
        colCuenta.setStyle("-fx-alignment: CENTER;");
        
        // Columna Nro Mov
        TableColumn<Movimiento, String> colNroMov = new TableColumn<>("Nro Mov");
        colNroMov.setCellValueFactory(cellData -> {
            int nro = cellData.getValue().getMovinumero();
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(nro));
        });
        colNroMov.setPrefWidth(110);
        colNroMov.setStyle("-fx-alignment: CENTER;");
        
        // Columna Fecha
        TableColumn<Movimiento, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(cellData -> {
            Movimiento mov = cellData.getValue();
            if (mov.getMovifecha() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return new javafx.beans.property.SimpleStringProperty(sdf.format(mov.getMovifecha()));
            }
            return new javafx.beans.property.SimpleStringProperty("N/A");
        });
        colFecha.setPrefWidth(130);
        colFecha.setStyle("-fx-alignment: CENTER;");
        
        // Columna Tipo
        TableColumn<Movimiento, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(cellData -> {
            String tipo = cellData.getValue().getTipocodigo();
            return new javafx.beans.property.SimpleStringProperty(tipo != null ? tipo : "");
        });
        colTipo.setPrefWidth(110);
        colTipo.setStyle("-fx-alignment: CENTER;");
        
        // Columna Acción con color
        TableColumn<Movimiento, String> colAccion = new TableColumn<>("Acción");
        colAccion.setCellValueFactory(cellData -> {
            String accion = cellData.getValue().getAccion();
            return new javafx.beans.property.SimpleStringProperty(accion != null ? accion : "");
        });
        colAccion.setPrefWidth(140);
        colAccion.setCellFactory(column -> new TableCell<Movimiento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                    if ("INGRESO".equals(item)) {
                        setStyle(
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: " + COLOR_INGRESO + "; " +
                            "-fx-background-color: rgba(16,185,129,0.1); " +
                            "-fx-background-radius: 6px; " +
                            "-fx-padding: 4px 8px;"
                        );
                    } else if ("SALIDA".equals(item)) {
                        setStyle(
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: " + COLOR_SALIDA + "; " +
                            "-fx-background-color: rgba(239,68,68,0.1); " +
                            "-fx-background-radius: 6px; " +
                            "-fx-padding: 4px 8px;"
                        );
                    } else {
                        setStyle("-fx-text-fill: #6b7280;");
                    }
                }
            }
        });
        
        // Columna Importe con color (verde para INGRESO, rojo para SALIDA)
        TableColumn<Movimiento, String> colImporte = new TableColumn<>("Importe");
        colImporte.setCellValueFactory(cellData -> {
            Movimiento mov = cellData.getValue();
            String accion = mov.getAccion();
            double importe = mov.getMoviimporte();
            String signo = "INGRESO".equals(accion) ? "+" : "-";
            String importeStr = String.format("%s $ %.2f", signo, Math.abs(importe));
            return new javafx.beans.property.SimpleStringProperty(importeStr);
        });
        colImporte.setPrefWidth(200);
        // Hacer que la última columna use el espacio restante
        colImporte.setResizable(true);
        colImporte.setCellFactory(column -> new TableCell<Movimiento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setAlignment(Pos.CENTER);
                    if (item.startsWith("+")) {
                        // INGRESO - Verde
                        setStyle(
                            "-fx-font-size: 15px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: " + COLOR_INGRESO + "; " +
                            "-fx-background-color: rgba(16,185,129,0.15); " +
                            "-fx-background-radius: 8px; " +
                            "-fx-padding: 6px 12px;"
                        );
                    } else if (item.startsWith("-")) {
                        // SALIDA - Rojo
                        setStyle(
                            "-fx-font-size: 15px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: " + COLOR_SALIDA + "; " +
                            "-fx-background-color: rgba(239,68,68,0.15); " +
                            "-fx-background-radius: 8px; " +
                            "-fx-padding: 6px 12px;"
                        );
                    } else {
                        setStyle("-fx-text-fill: #6b7280;");
                    }
                }
            }
        });
        
        tabla.getColumns().addAll(colCuenta, colNroMov, colFecha, colTipo, colAccion, colImporte);
        
        return tabla;
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
