package com.example.project3cs213;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Driver class to run project 3
 * @author Christian Osma, Liam Smith
 */
public class HelloApplication extends Application {
    /**
     * Overriden method that is the main entry point for the application
     * @param stage Stage that is to be displayed on screen
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 420);
        stage.setTitle("Tuition Manager");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    /**
     * This method is responsible for starting up the JavaFX application
     * @param args array of Strings
     */
    public static void main(String[] args) {
        launch();
    }
}
