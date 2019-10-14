package controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    //VARIABILE
    private boolean isLogged = false;
    public  String loggedUsername;
    private static String loggedPassword;
    private static String loggedEmail;

    public  String getLoggedUsername() {
        return loggedUsername;
    }

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

    public Label getSampleLoggedLabelUsername() {
        return sampleLoggedLabelUsername;
    }



    //    TEST BUTTONS
    public Button testButton;


    //METODE

    public void setTestButton() {
        testButton.setOnAction(event -> {
//            Snake.class;
        });
    }

    public void score() {
        sampleLoggedLabelScore.setText("0");
    }


    //    Display details
    public void setSampleLoggedDisplayDetails() {
        sampleLoggedLabelUsername.setText(loggedUsername);
        sampleLoggedLabelEmail.setText(loggedEmail);
        sampleLoggedLabelScore.setText(loggedScore);

    }

    //    Buttons sets
    public void setButtonBackLogin() {
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

    public void setSampleButtonLogin() {
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

    public void setBackButtonRegister() {
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

    public void setSampleButtonRegister() {
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

    public void setSampleLoggedLogoutButton() {
        sampleLoggedLogoutButton.setOnMouseClicked(event -> {
            try {
                isLogged = false;
                loggedEmail = null;
                loggedPassword = null;
                loggedUsername = null;


                Alert alert = new Alert(AlertType.INFORMATION, "You have been logged out !");
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
                System.out.println("### EXCEPTIE ### Controller ### setSampleLoggedLogoutButton");
            }
        });
        isLogged = false;
    }





    // REGISTER + LOGIN
    public void setLoginButtonLogin() {
        DBApp dbApp = new DBApp();
        loginButtonLogin.setOnMouseClicked((event -> {
            try {

                if (dbApp.validateUsername(loginTextFieldUsername.getText()) && dbApp.validatePassword(loginTextFieldPassword.getText(),
                        loginTextFieldPassword.getText())) {
                    isLogged = true;

                    loggedUsername = loginTextFieldUsername.getText();
                    loggedEmail = dbApp.getEmail(loggedUsername);
                    loggedScore = dbApp.getScore(loggedUsername);

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

    public void setRegisterButtonRegister() {
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



