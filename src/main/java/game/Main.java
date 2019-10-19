package game;

import controller.DBApp;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.cert.PolicyQualifierInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application  {
    
    //variable game
    static int speed = 5;
    static int foodcolor = 0;
    static int scorecolor = 0;
    static int latime = 20;
    static int inaltime = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random random = new Random();
    static int score = 0;

//    primaryStage game
    private static Stage stage;

//  variable logged account
    static String loggedUsername;
    static String loggedEmail;
    static int loggedScore;


    // variable fxml Buttons and more
    @FXML
    public  Label sampleLoggedLabelUsername;
    public Button sampleLoggedPlayButton;
    public Button sampleLoggedLogoutButton;
    public Label sampleLoggedLabelEmail;
    public Label sampleLoggedLabelScore;


    public void setSampleButtonPlay() {
        sampleLoggedPlayButton.setOnAction(event -> {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Platform.runLater(new Runnable() {
                public void run() {
                    try {
                        new Main().start(new Stage());
                    } catch (Exception e) {
                        System.out.println("### EROARE ### Main ### setSampleButtonPlay ###");
                    }
                }
            });
        });
    }

    //  method for logout button
    public void setSampleLoggedLogoutButton () {
        sampleLoggedLogoutButton.setOnMouseClicked(event -> {
            try {
                loggedEmail = null;
                loggedUsername = null;
                loggedScore = 0;

                File file = new File("loggedEmail.txt");
                File file1 = new File("loggedUsername.txt");
                File file2 = new File("loggedScore.txt");

                file.delete();
                file1.delete();
                file2.delete();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have been logged out !");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.showAndWait();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setScene(scene);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();

            } catch (Exception e) {
                System.out.println("### EXCEPTIE ### Main ### setSampleLoggedLogoutButton");
            }
        });
    }

    //    method for user details display(username,score,email..)
    public void setSampleLoggedDisplayDetails() {
        sampleLoggedLabelUsername.setText(loggedUsername);
        sampleLoggedLabelEmail.setText(loggedEmail);
        sampleLoggedLabelScore.setText(String.valueOf(loggedScore));
    }

    //    tick
    public static void tick(GraphicsContext graphicsContext) {
        if (gameOver) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("", 50));
            graphicsContext.fillText("GAME OVER", 100, 250);
            return;
        }
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y > inaltime) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x > latime) {
                    gameOver = true;
                }
                break;
        }

        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));
            newFood();
            score++;

        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }

        //fill
        //background

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, latime * cornersize, inaltime * cornersize);

        //score
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("", 15));
        graphicsContext.fillText("SCORE: ", 5, 15, 60);

//        Random scorecolor
        Color sc = Color.WHITE;

        switch (scorecolor) {
            case 0:
                sc = Color.ALICEBLUE;
                break;
            case 1:
                sc = Color.DARKRED;
                break;
            case 2:
                sc = Color.YELLOWGREEN;
                break;
            case 3:
                sc = Color.BLUEVIOLET;
                break;
            case 4:
                sc = Color.GREENYELLOW;
                break;
            case 5:
                sc = Color.ROYALBLUE;
                break;
            case 6:
                sc = Color.SEAGREEN;
                break;
            case 7:
                sc = Color.SNOW;
                break;
            case 8:
                sc = Color.PALEGREEN;
                break;
            case 9:
                sc = Color.INDIANRED;
                break;
            case 10:
                sc = Color.PALEVIOLETRED;
        }

        //update score
        graphicsContext.setFill(sc);
        graphicsContext.setFont(new Font("", 15));
        graphicsContext.fillText(String.valueOf(score), 63, 15, 50);

        //random foodcolor
        Color cc = Color.WHITE;

        switch (foodcolor) {
            case 0:
                cc = Color.PURPLE;
                break;
            case 1:
                cc = Color.RED;
                break;
            case 2:
                cc = Color.YELLOW;
                break;
            case 3:
                cc = Color.BLUE;
                break;
            case 4:
                cc = Color.GREEN;
                break;
            case 5:
                cc = Color.ROYALBLUE;
                break;
            case 6:
                cc = Color.SEAGREEN;
                break;
            case 7:
                cc = Color.SNOW;
                break;
            case 8:
                cc = Color.PALEGREEN;
                break;
            case 9:
                cc = Color.INDIANRED;
                break;
            case 10:
                cc = Color.PALEVIOLETRED;
        }

        graphicsContext.setFill(cc);
        graphicsContext.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);

        //snake
        for (Corner corner : snake) {
            graphicsContext.setFill(Color.LIGHTGREEN);
            graphicsContext.fillRect(corner.x * cornersize, corner.y * cornersize, cornersize - 1, cornersize - 1);
            graphicsContext.fillRect(corner.x * cornersize, corner.y * cornersize, cornersize - 2, cornersize - 2);
        }

        //game over
        if (gameOver) {
            ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            ButtonType backButton = new ButtonType("Back", ButtonBar.ButtonData.OK_DONE);

            Dialog<String> dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().add(backButton);
            dialog.getDialogPane().getButtonTypes().add(saveButton);

            dialog.getDialogPane().lookupButton(saveButton).setOnMousePressed(event -> {



                try {
//                    Read from file for accounts details
                    File file = new File("loggedUsername.txt");
                    Scanner scanner = new Scanner(file);
                    loggedUsername = scanner.nextLine();

                    File file1 = new File("loggedEmail.txt");
                    Scanner scanner1 = new Scanner(file1);
                    loggedEmail = scanner1.nextLine();

                    File file2 = new File("loggedScore.txt");
                    Scanner scanner2 = new Scanner(file2);
                    loggedScore = scanner2.nextInt();


                    DBApp dbApp = new DBApp();
                    String dbPlayerScore = dbApp.getScore(loggedUsername);
                    String dbPlayerScoreId = dbApp.getScoreID(loggedUsername);
                    int scoreInDb = Integer.parseInt(dbPlayerScore);

                    loggedScore = scoreInDb + score;

                    try {
                        FileWriter writer = new FileWriter(file2);
                        writer.write(String.valueOf(loggedScore));
                        writer.close();
                    } catch (Exception e){
                        System.out.println("### EXCEPTIE ### saveButton ### Main ### fileWriter SCORE ###");
                    }

                    dbApp.updateScore(Integer.parseInt(dbPlayerScoreId),loggedScore);

                    Alert.AlertType alertAlertType;
                    Alert alert = new Alert(AlertType.INFORMATION , "Your score was successfully saved !");
                    alert.setHeaderText(null);
                    alert.show();

                } catch (Exception e){
                    System.out.println("### EXCEPTIE ### Main ### saveButton");
                }

            });

            dialog.getDialogPane().lookupButton(backButton).setOnMousePressed((event -> {

                stage.hide();
                dialog.close();


                try {

                    File file = new File("loggedUsername.txt");
                    Scanner scanner = new Scanner(file);
                    loggedUsername = scanner.nextLine();

                    File file1 = new File("loggedEmail.txt");
                    Scanner scanner1 = new Scanner(file1);
                    loggedEmail = scanner1.nextLine();

                    File file2 = new File("loggedScore.txt");
                    Scanner scanner2 = new Scanner(file2);
                    loggedScore = scanner2.nextInt();

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(Main.class.getResource("/fxmlGame/sampleLogged.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    Logger logger = Logger.getLogger(Main.class.getName());
                    logger.log(Level.SEVERE, "Failed to create login window. ### Main ### if(gameOver) ###", e);
                }
            }));


            dialog.show();

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
            int min = 0;
            int max = 10;
            foodcolor = random.nextInt((max - min) + 1) + min;
            scorecolor = random.nextInt((max - min) + 1) + min;
            speed++;
            break;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
       Platform.setImplicitExit(true);

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
                if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
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
