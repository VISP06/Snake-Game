package com.example.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.swing.*;
import java.io.IOException;
import java.lang.classfile.Label;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    //variables responsible for movement of snake
    List<Point> snake = new ArrayList<>();

    //apple
    private Image appleImage;

    private static final Random random = new Random();
    private static int foodX = random.nextInt(0, (WIDTH/30))*30;
    private static int foodY = random.nextInt(0, (WIDTH/30))*30;

    private static Direction currentDirection = Direction.RIGHT;
    private static GameState currentGameState = GameState.MENU;
    @Override
    public void start(Stage stage) throws IOException {
        //we want apple image to get loaded only once
        appleImage = new Image(
                getClass().getResourceAsStream("/com.example.snake/apple_art.png")
        );
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            //MENU SCREEN
            VBox box = new VBox();
            box.setAlignment(Pos.CENTER);
            box.setSpacing(30);
            Text title = new Text("Jungle Snake");

            Button startButton = new Button("Start Game");
            startButton.setOnAction(e->{

            });

            Button quitButton = new Button("Quit");
            quitButton.setOnAction(e->{

            });
            box.getChildren().addAll(title);
            //END

            //color of the game board
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

            snake.add(new Point(300, 300));
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            KeyFrame frame = new KeyFrame(Duration.millis(150), e -> {
                update(timeline);      // Calculate new positions
                draw(graphicsContext); // Paint the new positions
            });

            timeline.getKeyFrames().add(frame);
            timeline.play();


            Text deathScreen = new Text("You DIED!");
            Button retryButton = new Button("Retry");
            retryButton.setOnAction(e->{

            });


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
        //drawing apple
        gc.drawImage(appleImage, foodX, foodY, 30, 30);
    }
    private void update(Timeline tl){
        Point head = snake.get(0);
        int headY = head.y();
        int headX = head.x();

        if(currentGameState == GameState.MENU || currentGameState == GameState.GAME_OVER){
            return;
        }else{

        }

        switch(currentDirection){
            case UP -> headY -= 30;
            case DOWN -> headY += 30;
            case RIGHT -> headX += 30;
            case LEFT -> headX -= 30;
        }

        Point newHead = new Point(headX, headY);
        //we keep checking whether the snake collides with any part of its body
        for(Point p:snake) {
            if (newHead.x() == p.x() && newHead.y() == p.y()) {
                tl.stop();
                return;
            }
        }
        snake.add(0, newHead);

        //if the snake eats an apple
        if(headX == foodX && headY == foodY){
            foodX = random.nextInt(0, (WIDTH/30))*30;
            foodY = random.nextInt(0, (WIDTH/30))*30;
        }else{
            snake.remove(snake.size()-1);
        }

        //if the snake hits anyone of the 4 corners, the game concludes
        if(headX>=WIDTH|| headY>=HEIGHT || headX < 0 || headY < 0){
            tl.stop();
        }
    }
}