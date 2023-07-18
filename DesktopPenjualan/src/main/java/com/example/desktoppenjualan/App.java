package com.example.desktoppenjualan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    double x,y;
    @Override
    public void start(Stage stage) throws IOException {
        HBox root =
                (HBox)FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root,600, 400);
        root.setOnMousePressed(e->{
            x= e.getSceneX();
            y= e.getSceneY();
        });
        root.setOnMouseDragged(e->{
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getSceneX() - y);
        });
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
