package org.example.cce107;

import java.io.IOException;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Button login;
    @FXML
    private PasswordField pass;
    @FXML
    private TextField user;
    @FXML
    private Label t;
    @FXML
    private Label wrong;

    @FXML
    private Label label_wrong;

    @FXML
    private Label pass_need;

    @FXML
    private Label userid;

    @FXML
    private RadioButton showpass;

    public void login(ActionEvent event) {
        String username = "admin";
        String password = "admin123";

        Alert alert;

        if  (pass.getText().isBlank()) {
            pass_need.setText("This is required.");
            login.setStyle("-fx-border-color: red; -fx-border-radius: 7px; -fx-font-family: 'Palatino Linotype'");


            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> pass_need.setText(""));
                    Platform.runLater(() -> login.setStyle("-fx-border-color: none"));
                    timer.cancel();
                }
            }, 3500);

        } if  (user.getText().isBlank()) {

            userid.setText("This is required.");
            login.setStyle("-fx-border-color: red; -fx-border-radius: 9px");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> userid.setText(""));
                    Platform.runLater(() -> login.setStyle("-fx-border-color: none"));
                    timer.cancel();
                }
            }, 3500);

        } if (username.equals(user.getText()) && password.equals(pass.getText())) {

            login.getScene().getWindow().hide();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } else {

            label_wrong.setText("Incorrect username / password.");
            login.setStyle("-fx-border-color: red; -fx-border-radius: 9px");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> label_wrong.setText(""));
                    Platform.runLater(() -> login.setStyle("-fx-border-color: none"));
                    timer.cancel();
                }
            }, 3500);
        }

    }




}
