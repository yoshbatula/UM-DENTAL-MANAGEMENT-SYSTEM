package org.example.cce107;
import java.io.IOException;
import java.sql.SQLException;
import java.time.chrono.Chronology;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

public class MAINPAGECONT {

    @FXML
    private Button appoint_btn;

    @FXML
    private AnchorPane appoint_form;

    @FXML
    private AnchorPane doc_form;

    @FXML
    private Button doctor_btn;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label full_req;

    @FXML
    private Label gender_req;

    @FXML
    private Label mobile_req;

    @FXML
    private Label mail_req;

    @FXML
    private Label age_req;

    @FXML
    private Label address_req;

    @FXML
    private Button submit;

    @FXML
    private TextField mobile_tf;

    @FXML
    private TextField gender_tf;

    @FXML
    private TextField full_tf;

    @FXML
    private TextField email_tf;

    @FXML
    private TextField age_tf;

    @FXML
    private TextField address_tf;

    @FXML
    private DatePicker tf_date;

    @FXML
    private MenuButton tf_services;

    @FXML
    private MenuButton tf_time;

    private Connection conn = null;
    private Statement sts;
    private PreparedStatement pts;
    private ResultSet result;

    private String sql = "SELECT * FROM addInfo";

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {

            home_form.setVisible(true);
            appoint_form.setVisible(false);
            doc_form.setVisible(false);

            home_form.setStyle("-fx-background-color: #BAD6EB");
            appoint_form.setStyle("-fx-background-color: transparent");
            doc_form.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == appoint_btn) {

            home_form.setVisible(false);
            appoint_form.setVisible(true);
            doc_form.setVisible(false);

            appoint_form.setStyle("-fx-background-color:  #F6E9DA");
            doc_form.setStyle("-fx-background-color: transparent");
            home_form.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == doctor_btn) {

            home_form.setVisible(false);
            appoint_form.setVisible(false);
            doc_form.setVisible(true);

            doc_form.setStyle("-fx-background-color:  #37A1C3");
            appoint_form.setStyle("-fx-background-color: transparent");
            home_form.setStyle("-fx-background-color: transparent");
        }
    }

    public void personalInfo(ActionEvent event) {

        String fullname = full_tf.getText();
        int age = Integer.parseInt(age_tf.getText());
        String gender = gender_tf.getText();
        String mobileno = mobile_tf.getText();
        String email = email_tf.getText();
        String address = address_tf.getText();
        Chronology date = tf_date.getChronology();
        String time = tf_time.getItems().toString();
        String services = tf_services.getItems().toString();

        try {
            pts = conn.prepareStatement("INSERT INTO addInfo(Fullname, Age, Gender, MobileNo, Email, Address, Date, Time, Services) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pts.setString(1, fullname);
            pts.setInt(2, age);
            pts.setString(3, gender);
            pts.setString(4, mobileno);
            pts.setString(5, email);
            pts.setString(6, address);
            pts.setDate(7, Date.valueOf(String.valueOf(date)));
            pts.setString(8, time);
            pts.setString(9, services);
            pts = conn.prepareStatement(sql);
            result = pts.executeQuery();


            full_tf.setText("");
            full_tf.setText("");
            full_tf.setText("");
            full_tf.setText("");
            full_tf.setText("");
            full_tf.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (pts != null) {
                    pts.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (full_tf.getText().isEmpty() || age_tf.getText().isEmpty()) {
            full_req.setText("This is required.");
            age_req.setText("This is required");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> full_req.setText(""));
                    Platform.runLater(() -> age_req.setText(""));
                    timer.cancel();
                }
            }, 3500);

        }  if (address_tf.getText().isEmpty() || email_tf.getText().isEmpty()) {
            address_req.setText("This is required");
            mail_req.setText("This is required");

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> address_req.setText(""));
                    Platform.runLater(() -> mail_req.setText(""));
                    timer.cancel();
                }
            }, 3500);

        }  if (mobile_tf.getText().isEmpty() || gender_tf.getText().isEmpty()) {
            mobile_req.setText("This is required");
            gender_req.setText("This is required");


            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> mobile_req.setText(""));
                    Platform.runLater(() -> gender_req.setText(""));
                    timer.cancel();
                }
            }, 3500);
        }
    }

}
