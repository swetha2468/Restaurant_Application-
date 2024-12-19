package com.group4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String CONFIGURATION_FILENAME = "configuration.json";
    
    private static Configuration g_configuration;

    private static Order g_curOrder;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1300, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Configuration getConfiguration()
    {
        return App.g_configuration;
    }

    public static Order getOrder()
    {
        return App.g_curOrder;
    }

    public static void main(String[] args) {
        // Load configuration into Application.
        App.g_configuration = Configuration.load(CONFIGURATION_FILENAME);
        System.out.println("Configuration:");
        System.out.println(App.g_configuration);
        System.out.println(g_configuration.toString()); 
        System.out.println(g_configuration.getItems());
        System.out.println(g_configuration.getItems());
        
        // Instantiate order as an empty Order to be filled as needed.
        App.g_curOrder = new Order();

        launch();
        System.out.println("HELLO");
        System.out.println(App.getOrder().getItems());
    }
}