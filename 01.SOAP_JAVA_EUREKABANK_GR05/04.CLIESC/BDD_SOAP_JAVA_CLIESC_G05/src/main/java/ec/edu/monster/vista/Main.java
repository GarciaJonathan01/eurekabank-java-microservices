/*
 * Cliente de Escritorio EurekaBank - Aplicación Principal
 * @author Grupo Monster G09
 */
package ec.edu.monster.vista;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación JavaFX
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        loginView.mostrar(primaryStage);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

