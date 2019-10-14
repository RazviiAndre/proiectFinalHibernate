package controller;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    //variable
    static int speed = 5;
    static int foodcolor = 0;
    static int latime = 20;
    static int inaltime = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random random = new Random();

//    tick
    public static void tick(GraphicsContext graphicsContext){
        if(gameOver){
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("",50));
            graphicsContext.fillText("GAME OVER",100 , 250);
            return;
        }
        for(int i = snake.size() - 1 ; i >= 1 ; i--){
            snake.get(i).x = snake.get(i - 1 ).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction){
            case up:
                snake.get(0).y--;
                    if(snake.get(0).y < 0){
                        gameOver = true;
                    }
                    break;
            case down:
                snake.get(0).y++;
                    if(snake.get(0).y > inaltime){
                        gameOver = true;
                    }
                    break;
            case left:
                snake.get(0).x--;
                    if(snake.get(0).x < 0){
                        gameOver = true;
                    }
                    break;
            case right:
                snake.get(0).x++;
                    if(snake.get(0).x > latime){
                        gameOver = true;
                    }
                    break;
        }

        // eat food
        if(foodX == snake.get(0).x && foodY == snake.get(0).y){
            snake.add(new Corner(-1 , -1));
            newFood();
        }

        // self destroy
        for(int i = 1 ; i < snake.size() ; i++){
            if(snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y){
                gameOver = true;
            }
        }

        //fill
        //background

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0, latime*cornersize , inaltime*cornersize);

        //score
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("",30));
        graphicsContext.fillText("Score: ",+(speed-6),10,30);

        //random foodcolor
        Color cc = Color.WHITE;

        switch (foodcolor){
            case 0 : cc = Color.PURPLE;
            break;
            case 1 : cc = Color.RED;
            break;
            case 2 : cc = Color.YELLOW;
            break;
            case 3 : cc = Color.BLUE;
            break;
            case 4 : cc = Color.GREEN;
        }

        graphicsContext.setFill(cc);
        graphicsContext.fillOval(foodX * cornersize , foodY * cornersize , cornersize , cornersize);

        //snake
        for(Corner corner : snake){
            graphicsContext.setFill(Color.LIGHTGREEN);
            graphicsContext.fillRect(corner.x * cornersize , corner.y * cornersize , cornersize - 1 , cornersize - 1);
            graphicsContext.fillRect(corner.x * cornersize , corner.y * cornersize , cornersize - 2 , cornersize - 2);
        }

        //new game

        if(gameOver == true){
            Alert alert = new Alert(AlertType.NONE , "                              You LOSE !" , ButtonType.OK , ButtonType.CANCEL, ButtonType.APPLY);
            alert.show();


        }

    }

//food
    public static void newFood(){
        start : while(true){
            foodX = random.nextInt(latime);
            foodY = random.nextInt(inaltime);

            for(Corner c : snake){
                if(c.x == foodX && c.y == foodY){
                    continue start;
                }
            }
            foodcolor = random.nextInt(5);
            speed++;
            break;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            newFood();

            VBox root = new VBox();
            Canvas canvas = new Canvas(latime * cornersize, inaltime * cornersize);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                @Override
                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(graphicsContext);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(graphicsContext);
                    }
                }
            }.start();


            Scene scene = new Scene(root, latime * cornersize, inaltime * cornersize);

            //control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.S) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.A) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.D) {
                    direction = Dir.right;
                }
            });

            //add start snake parts
            snake.add(new Corner(latime / 2, inaltime / 2));
            snake.add(new Corner(latime / 2, inaltime / 2));
            snake.add(new Corner(latime / 2, inaltime / 2));


            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE");
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
