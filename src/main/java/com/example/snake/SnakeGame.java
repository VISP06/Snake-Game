package com.example.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    //menu background image
    private Image menuBackground;

    private Font pixelFont;

    private static final Random random = new Random();
    private static int foodX = random.nextInt(0, (WIDTH / 30)) * 30;
    private static int foodY = random.nextInt(0, (WIDTH / 30)) * 30;

    private static Direction currentDirection = Direction.RIGHT;
    private static GameState currentGameState = GameState.MENU;

    private static VBox deathBox;

    private Timeline timeline;

    @Override
    public void start(Stage stage) throws IOException {

        loadAssets();

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        StackPane menuBox = createMenu(canvas);

        setupGameEngine(graphicsContext);

        createDeathScreen(canvas);

        StackPane stackPane = new StackPane(canvas, deathBox, menuBox);
        Scene scene = new Scene(stackPane, WIDTH, HEIGHT);

        setupControls(scene);

        stage.setScene(scene);
        stage.setResizable(false); //prevents the user from expanding the screen
        stage.show();
    }

    private void loadAssets() {
        //we want apple image to get loaded only once
        appleImage = new Image(
                getClass().getResourceAsStream("/com/example/snake/apple_art.png")
        );
        menuBackground = new Image(
                getClass().getResourceAsStream("/com/example/snake/menu_background.jpg")
        );
        pixelFont = Font.loadFont(
                getClass().getResourceAsStream("/fonts/Minecraftia-Regular.ttf"),
                24
        );
    }

    private StackPane createMenu(Canvas canvas) {

        //MENU SCREEN

        ImageView background = new ImageView(menuBackground);
        background.setFitWidth(WIDTH);
        background.setFitHeight(HEIGHT);
        background.setPreserveRatio(false);

        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(25);

        StackPane menuPane = new StackPane(background, controls);

        Text title = new Text("JUNGLE\nSNAKE");
        title.setFont(Font.font(pixelFont.getFamily(), 42));

        title.setFill(Color.WHITE);
        title.setStroke(Color.web("#32CD32"));
        title.setStrokeWidth(3);

        Button startButton = new Button("START GAME");
        startButton.setPrefSize(220, 50);

        String buttonStyle =
                "-fx-background-color: #A0520D;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: '" + pixelFont.getFamily() + "';" +
                        "-fx-font-size: 14;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;";

        startButton.setStyle(buttonStyle);

        startButton.setOnAction(e -> {
            currentGameState = GameState.PLAYING;
            menuPane.setVisible(false);
            canvas.requestFocus();
        });

        Button quitButton = new Button("QUIT");
        quitButton.setPrefSize(220, 50);
        quitButton.setStyle(buttonStyle);

        quitButton.setOnAction(e -> {
            Platform.exit();
        });

        controls.getChildren().addAll(
                title,
                startButton,
                quitButton
        );
        return menuPane;
    }

    private void setupGameEngine(GraphicsContext graphicsContext) {
        //game engine
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);

        snake.add(new Point(300, 300)); //snake head is created but not placed yet

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(150), e -> {
            update(timeline);      // Calculate new positions
            draw(graphicsContext); // Paint the new positions
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void createDeathScreen(Canvas canvas) {

        //GAME_OVER SCREEN
        Text deathScreen = new Text("You DIED!");

        Button retryButton = new Button("Retry");
        retryButton.setOnAction(e -> {

            currentGameState = GameState.PLAYING;

            //resetting the snake
            snake.clear();
            snake.add(new Point(300, 300));
            currentDirection = Direction.RIGHT;

            //resetting the apple
            foodX = random.nextInt(0, (WIDTH / 30)) * 30;
            foodY = random.nextInt(0, (WIDTH / 30)) * 30;

            timeline.play();
            deathBox.setVisible(false);
            canvas.requestFocus();
        });

        deathBox = new VBox(deathScreen, retryButton);
        deathBox.setAlignment(Pos.CENTER);
        deathBox.setVisible(false);
        deathBox.setSpacing(30);
        //END
    }

    private void setupControls(Scene scene) {

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (currentDirection != Direction.DOWN)
                        currentDirection = Direction.UP;
                    break;

                case DOWN:
                    if (currentDirection != Direction.UP)
                        currentDirection = Direction.DOWN;
                    break;

                case RIGHT:
                    if (currentDirection != Direction.LEFT)
                        currentDirection = Direction.RIGHT;
                    break;

                case LEFT:
                    if (currentDirection != Direction.RIGHT)
                        currentDirection = Direction.LEFT;
                    break;
            }
        });
    }

    private void draw(GraphicsContext gc) {

        //color of the game board
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        //snake
        gc.setFill(Color.DARKSEAGREEN);
        for (Point p : snake) {
            gc.fillRect(p.x(), p.y(), 30, 30);
        }

        //drawing apple
        gc.drawImage(appleImage, foodX, foodY, 30, 30);
    }

    private void update(Timeline tl) {

        Point head = snake.get(0);
        int headY = head.y();
        int headX = head.x();

        if (currentGameState == GameState.MENU || currentGameState == GameState.GAME_OVER) {
            return;
        }

        switch (currentDirection) {
            case UP -> headY -= 30;
            case DOWN -> headY += 30;
            case RIGHT -> headX += 30;
            case LEFT -> headX -= 30;
        }

        Point newHead = new Point(headX, headY);

        //we keep checking whether the snake collides with any part of its body
        for (Point p : snake) {
            if (newHead.x() == p.x() && newHead.y() == p.y()) {
                currentGameState = GameState.GAME_OVER;
                deathBox.setVisible(true);
                tl.stop();
                return;
            }
        }

        //snake moves by adding a block to the front and deleting one block in the back which gives the illusion of movement
        snake.add(0, newHead); //during first iteration snake head is placed but successive iterations it will be used for increasing the size of snake

        //if the snake eats an apple
        if (headX == foodX && headY == foodY) {
            //we don't need to get rid of a block from the back in this case as the size has actually increased
            foodX = random.nextInt(0, (WIDTH / 30)) * 30;
            foodY = random.nextInt(0, (WIDTH / 30)) * 30;
        } else {
            snake.remove(snake.size() - 1);
        }

        //if the snake hits anyone of the 4 corners, the game concludes
        if (headX > WIDTH || headY > HEIGHT || headX < 0 || headY < 0) {
            currentGameState = GameState.GAME_OVER;
            deathBox.setVisible(true);
            tl.stop();
        }
    }
}