package com.example.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SnakeGame extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    //variables responsible for movement of snake
    private static int snakeX = 300;
    private static int snakeY = 300;

    private static int foodX = 200;
    private static int foodY = 200;

    private static Direction currentDirection = Direction.RIGHT;
    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        //color of the game board
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);


        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        KeyFrame frame = new KeyFrame(Duration.millis(150), e -> {
            update();      // Calculate new positions
            draw(graphicsContext); // Paint the new positions
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();


        Group group = new Group(canvas);
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setOnKeyPressed(event-> {
            switch (event.getCode()){
                case UP: currentDirection = Direction.UP; break;
                case DOWN: currentDirection = Direction.DOWN; break;
                case RIGHT: currentDirection = Direction.RIGHT; break;
                case LEFT : currentDirection = Direction.LEFT; break;
            }
        });
        stage.setScene(scene);
        stage.setResizable(false); //prevents the user from expanding the screen
        stage.show();
    }
    private void draw(GraphicsContext gc){
        //color of the game board
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        //snake
        gc.setFill(Color.DARKSEAGREEN);
        gc.fillRect(snakeX, snakeY, 30, 30);

        //apple
        gc.setFill(Color.CRIMSON);
        gc.fillOval(foodX, foodY, 30, 30);
    }
    private void update(){
        if(snakeX == WIDTH || snakeY == HEIGHT){
            //terminate game
        }
        if(snakeX == foodX && snakeY == foodY){
            //increase size of snake
        }
        switch(currentDirection){
            case UP -> snakeY += 30;
            case DOWN -> snakeY -= 30;
            case RIGHT -> snakeX += 30;
            case LEFT -> snakeX -= 30;
        }
    }
}


