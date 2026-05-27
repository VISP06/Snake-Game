package com.example.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeGame extends Application {
    private static int WIDTH = 600;
    private static int HEIGHT = 600;
    @Override
    public void start(Stage stage) throws IOException {
        Group group = new Group();
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();
        Canvas canvas = new Canvas();
    }
}
