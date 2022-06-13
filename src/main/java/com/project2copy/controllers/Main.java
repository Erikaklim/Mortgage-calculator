package com.project2copy.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

   public void start(Stage stage){
       try {
           Parent root = FXMLLoader.load(getClass().getResource("/com/project2copy/mainScene.fxml"));
           Scene scene = new Scene(root);
           stage.setTitle("Mortgage Calculator");
           stage.setScene(scene);
           stage.show();

       } catch (Exception e) {
           e.printStackTrace();
       }
   }

    public static void main(String[] args) {
        launch();
    }
}
