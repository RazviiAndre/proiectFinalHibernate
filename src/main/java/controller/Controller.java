package controller;

import DAO.Player;
import DAO.Score;
import DAO.User;
import game.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller  {

    //VARIABILE
    private boolean isLogged = false;
    private static String loggedUsername;
    private static String loggedPassword;
    private static String loggedEmail;


    private static String loggedScore;

    //sample page
    public Button sampleButtonPlay;
    public Button sampleButtonLogin;
    public Button sampleButtonRegister;
    public Button buttonBackRegister;

    //register page
    public TextField registerTextFieldUsername;
    public TextField registerTextFieldEmail;
    public TextField registerTextfieldLastname;
    public TextField registerTextFieldFirstname;
    public TextField registerTextFieldPhone;
    public PasswordField registerTextFieldPassword;
    public PasswordField registerTextFieldConfirmPassword;
    public Button registerButtonRegister;

    //login page
    public TextField loginTextFieldUsername;
    public PasswordField loginTextFieldPassword;
    public Button loginButtonLogin;
    public Button loginButtonBack;

    // sample logged page
    public Button sampleLoggedPlayButton;
    public Button sampleLoggedLogoutButton;
    public Label sampleLoggedLabelUsername;
    public Label sampleLoggedLabelEmail;
    public Label sampleLoggedLabelScore;


    //    TEST BUTTONS
//    public Button testButton;


    //METODE


        //    Display details
        public void setSampleLoggedDisplayDetails () {
            sampleLoggedLabelUsername.setText(loggedUsername);
            sampleLoggedLabelEmail.setText(loggedEmail);
            sampleLoggedLabelScore.setText(loggedScore);

        }


        //    Buttons sets
        public void setSampleButtonPlay() {
            sampleLoggedPlayButton.setOnAction(event -> {
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            new Main().start(new Stage());
                        } catch (Exception e) {
                            System.out.println("### EROARE ### Controller ### setSampleButtonPlay ###");
                        }
                    }
                });
            });
        }

        public void setButtonBackLogin () {
            if (!isLogged) {
                loginButtonBack.setOnMouseClicked((event) -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    } catch (Exception e) {
                        System.out.println("### setButtonBackLogin ### Controller ### sample.fxml");
                    }
                });
            } else {
                loginButtonBack.setOnMouseClicked((event) -> {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/fxml/sampleLogged.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                            } catch (Exception e) {
                                System.out.println("### setButtonBackLogin ### Controller ### sampleLogged.fxml");
                            }
                        }
                );
            }
        }

        public void setSampleButtonLogin () {
            sampleButtonLogin.setOnMouseClicked((event) -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setScene(scene);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Fail to create login window. ### Controller ### setSampleButtonLogin", e);

                }
            });
        }

        public void setBackButtonRegister () {
            buttonBackRegister.setOnMouseClicked((event) -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    System.out.println("ERROR ### Controller ### setBackButtonRegister ");
                }
            });
        }

        public void setSampleButtonRegister () {
            sampleButtonRegister.setOnMouseClicked((event) -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/register.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("Register");
                    stage.setScene(scene);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(getClass().getName());
                    logger.log(Level.SEVERE, "Failed to create register window. ### Controller ### setSampleButtonRegister ###", e);
                }
            });
        }

        public void setSampleLoggedLogoutButton () {
            sampleLoggedLogoutButton.setOnMouseClicked(event -> {
                try {
                    isLogged = false;
                    loggedEmail = null;
                    loggedPassword = null;
                    loggedUsername = null;

                    File file = new File("loggedEmail.txt");
                    File file1 = new File("loggedUsername.txt");
                    File file2 = new File("loggedScore.txt");

                    file.delete();
                    file1.delete();
                    file2.delete();


                    Alert alert = new Alert(AlertType.INFORMATION, "You have been logged out !");
                    alert.setHeaderText(null);
                    alert.setGraphic(null);
                    alert.showAndWait();

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                    Stage stage = new Stage();
                    stage.setTitle("");
                    stage.setScene(scene);
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();

                } catch (Exception e) {
                    System.out.println("### EXCEPTIE ### Controller ### setSampleLoggedLogoutButton");
                }
            });

            isLogged = false;
        }


        // REGISTER + LOGIN
        public void setLoginButtonLogin () {
            DBApp dbApp = new DBApp();
            loginButtonLogin.setOnMouseClicked((event -> {
                try {
                    User user = dbApp.validatePassword(loginTextFieldPassword.getText(),
                            loginTextFieldUsername.getText());
                    if (user != null) {
                        isLogged = true;

                        loggedUsername = loginTextFieldUsername.getText();
                        Player player = user.getPlayer();
                        loggedEmail = player.getEmail();
                        Score score = player.getScore();
                        if (score != null) {
                            loggedScore = "" + score.getValue();
                        } else {
                            loggedScore = "";
                        }

                        File file = new File("loggedUsername.txt");
                        File file1 = new File("loggedEmail.txt");
                        File file2 = new File("loggedScore.txt");

                        try {
                            if (file.createNewFile() && file1.createNewFile() && file2.createNewFile()) {
                                System.out.println("File is created!");
                            } else {
                                System.out.println("File already exists.");
                            }

                            FileWriter writer = new FileWriter(file);
                            writer.write(loggedUsername);
                            writer.close();

                            FileWriter writer1 = new FileWriter(file1);
                            writer1.write(loggedEmail);
                            writer1.close();

                            FileWriter writer2 = new FileWriter(file2);
                            writer2.write(loggedScore);
                            writer2.close();
                        } catch (Exception e) {
                            System.out.println("### ERROR CREATING FILE ### CONTROLLER ### setLoginButtonLogin ###");
                        }


                        Alert alert = new Alert(AlertType.INFORMATION, "You have been logged succesfully !");
                        alert.setHeaderText(null);
                        alert.show();

                        loginButtonBack.setOnMouseClicked((event1 -> {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("/fxml/sampleLogged.fxml"));
                                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                                Stage stage = new Stage();
                                stage.setTitle("Login");
                                stage.setScene(scene);
                                stage.show();
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                            } catch (IOException e) {
                                Logger logger = Logger.getLogger(getClass().getName());
                                logger.log(Level.SEVERE, "Failed to create login window. ### Controller ### setLoginButtonLogin ###", e);
                            }
                        }));

                    } else {
                        Alert alert = new Alert(AlertType.WARNING, "Username or password it's wrong !");
                        alert.setHeaderText(null);
                        alert.show();
                    }

                } catch (Exception e) {
                    System.out.println("### setLoginButtonLogin ### Controller ###");
                }
            }));
        }

        public void setRegisterButtonRegister () {
            DBApp dbApp = new DBApp();

            if (!registerTextFieldUsername.getText().equals("") &&
                    !registerTextFieldEmail.getText().equals("") &&
                    !registerTextFieldPassword.getText().equals("") &&
                    !registerTextFieldConfirmPassword.getText().equals("") &&
                    !registerTextFieldFirstname.getText().equals("") &&
                    !registerTextfieldLastname.getText().equals("") &&
                    !registerTextFieldPhone.getText().equals("")) {
                if (!dbApp.validateEmail(registerTextFieldEmail.getText()) && !dbApp.validateUsername(registerTextFieldUsername.getText())) {
                    if (registerTextFieldPassword.getText().equals(registerTextFieldConfirmPassword.getText())) {

                        dbApp.insertUser(registerTextFieldUsername.getText(),
                                registerTextFieldPassword.getText(),
                                registerTextFieldEmail.getText(),
                                registerTextFieldFirstname.getText(),
                                registerTextfieldLastname.getText(),
                                Integer.parseInt(registerTextFieldPhone.getText()));

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Register succesufully !");
                        alert.setTitle("Register");
                        alert.setHeaderText(null);
                        alert.setGraphic(null);
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Password dosen't match !");
                        alert.setTitle("Register");
                        alert.setHeaderText(null);
                        alert.setGraphic(null);
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username or email exist ! Try another !");
                    alert.setTitle("Register");
                    alert.setHeaderText(null);
                    alert.setGraphic(null);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "You must complete all fields !");
                alert.setTitle("Register");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.show();
            }
        }


}






