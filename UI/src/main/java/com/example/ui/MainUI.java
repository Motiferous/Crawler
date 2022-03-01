package com.example.ui;
//Basic main. Nothing really changed.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 1000);
        stage.setTitle("Web crawler");
        stage.setScene(scene);
        stage.show();
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
    }

    public static void main(String[] args) {
        launch();
    }



}
