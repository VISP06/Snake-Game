package com.example.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SnakeGame extends Application {
    private static int WIDTH = 600;
    private static int HEIGHT = 600;
    //variables responsible for movement of snake
    private static int snakeX = 300;
    private static int snakeY = 300;
    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        //color of the game board
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

        //snake
        graphicsContext.setFill(Color.DARKSEAGREEN);
        graphicsContext.fillRect(200, 200, 30, 30);

        //apple
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.fillOval(150, 150, 30, 30);

        Group group = new Group(canvas);
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false); //prevents the user from expanding the screen
        stage.show();
    }
}
