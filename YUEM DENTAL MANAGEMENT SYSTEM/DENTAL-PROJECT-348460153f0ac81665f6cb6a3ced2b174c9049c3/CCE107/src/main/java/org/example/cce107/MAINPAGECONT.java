package org.example.cce107;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.chrono.Chronology;
import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

public class MAINPAGECONT implements Initializable {

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

    @FXML
    private ComboBox time;

    @FXML
    private ComboBox services;

    @FXML
    void select_services(ActionEvent event) {

    }

    @FXML
    void select_time(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("8:00am - 9:00am", "9:00am - 10:00am", "10:00am - 11:00am", "11:00am - 12:00am", "1:00pm - 2:00pm", "2:00pm - 3:00pm", "3:00pm - 4:00pm", "4:00pm  - 5:00pm");
        time.setItems(list);

        ObservableList<String> list1 = FXCollections.observableArrayList("Tooth Extractions", "Teeth Whitening", "Dental Sealants", "Root Canal Therapy", "Dentures", "Teeth Cleanings", "Dental Veneers", "Invisalign", "Cosmetic Fillings", "Bridgework", "Dental Crowns", "Dental Bonding");
        services.setItems(list1);

        verifyTableSchema("addInfo");
    }

    private Connection conn;
    private Statement sts;
    private PreparedStatement pts;
    private ResultSet result;

    public MAINPAGECONT() {
        dentaldb("postgres", "postgres", "admin");
    }

    public void dentaldb(String dbname, String user, String pass) {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.print("Connection Established");
            } else {
                System.out.print("Connection Failed");
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private void verifyTableSchema(String tableName) {
        String schemaQuery = "SELECT column_name, data_type, is_nullable FROM information_schema.columns WHERE table_name = ? AND table_schema = 'public'";
        try (PreparedStatement pts = conn.prepareStatement(schemaQuery)) {
            pts.setString(1, tableName);
            ResultSet schemaResult = pts.executeQuery();
            System.out.println("Table schema for " + tableName + ":");
            while (schemaResult.next()) {
                System.out.println("Column: " + schemaResult.getString(1) + ", Type: " + schemaResult.getString(2) + ", Nullable: " + schemaResult.getString(3));
            }
        } catch (SQLException e) {
            System.err.println("Error verifying table schema: " + e.getMessage());
        }
    }


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

    public void personalInfo(ActionEvent event) throws SQLException {

        try (PreparedStatement pts = conn.prepareStatement("INSERT INTO addInfo (Fullname, Age, Gender, MobileNo, Email, Address, Date, Time, Services) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pts.setString(1, full_tf.getText());
            pts.setInt(2, Integer.valueOf(age_tf.getText()));
            pts.setString(3, gender_tf.getText());
            pts.setString(4, mobile_tf.getText());
            pts.setString(5, email_tf.getText());
            pts.setString(6, address_tf.getText());
            pts.setDate(7, Date.valueOf(tf_date.getValue()));
            pts.setString(8, (String) time.getSelectionModel().getSelectedItem());
            pts.setString(9, (String) services.getSelectionModel().getSelectedItem());
            pts.executeUpdate();

            ClearForm();

        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
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

        }
        if (address_tf.getText().isEmpty() || email_tf.getText().isEmpty()) {
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

        }
        if (mobile_tf.getText().isEmpty() || gender_tf.getText().isEmpty()) {
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
    public void ClearForm() {
        full_tf.setText("");
        age_tf.setText("");
        gender_tf.setText("");
        mobile_tf.setText("");
        email_tf.setText("");
        address_tf.setText("");
        tf_date.setValue(null);
        time.getSelectionModel().clearSelection();
        services.getSelectionModel().clearSelection();
    }
}
