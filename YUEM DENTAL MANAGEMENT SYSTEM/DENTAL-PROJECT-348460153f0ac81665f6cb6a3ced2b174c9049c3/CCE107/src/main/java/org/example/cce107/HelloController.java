package org.example.cce107;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;

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
    private AnchorPane loginpane;


    public void login(ActionEvent event) {
        String username = "admin";
        String password = "admin123";

        Alert alert;

        if  (pass.getText().isBlank()) {
            pass_need.setText("This is required.");
            login.setStyle("-fx-border-color: red; -fx-border-radius: 9px; -fx-font-family: 'Palatino Linotype'");


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

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setNode(loginpane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setOnFinished(fadeEvent -> {

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
            });
            fadeTransition.play();
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
