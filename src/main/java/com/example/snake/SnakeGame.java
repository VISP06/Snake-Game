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
import java.util.ArrayList;
import java.util.List;

public class SnakeGame extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    //variables responsible for movement of snake
    List<Point> snake = new ArrayList<>();

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

        snake.add(new Point(300, 300));
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
                case UP : if(currentDirection != Direction.DOWN) currentDirection = Direction.UP; break;
                case DOWN : if(currentDirection != Direction.UP) currentDirection = Direction.DOWN; break;
                case RIGHT : if(currentDirection != Direction.LEFT) currentDirection = Direction.RIGHT; break;
                case LEFT : if(currentDirection != Direction.RIGHT) currentDirection = Direction.LEFT; break;
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
        for(Point p:snake){
            gc.fillRect(p.x(), p.y(), 30, 30);
        }

        //apple
        gc.setFill(Color.CRIMSON);
        gc.fillOval(foodX, foodY, 30, 30);
    }
    private void update(){
        Point head = snake.get(0);
        int nextY = head.y();
        int nextX = head.x();

        switch(currentDirection){
            case UP -> nextY -= 30;
            case DOWN -> nextY += 30;
            case RIGHT -> nextX += 30;
            case LEFT -> nextX -= 30;
        }

        Point newHead = new Point(nextX, nextY);

        if(nextX>=WIDTH|| nextY>=HEIGHT){
            //terminate game
        }
        if(nextX == foodX && nextY == foodY){
            //increase size of snake
        }
    }
}


