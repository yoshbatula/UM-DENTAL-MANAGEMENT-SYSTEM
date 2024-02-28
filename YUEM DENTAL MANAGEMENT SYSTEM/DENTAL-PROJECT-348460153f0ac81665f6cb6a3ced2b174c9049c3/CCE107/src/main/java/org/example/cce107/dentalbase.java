package org.example.cce107;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
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

public class dentalbase implements Initializable {

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
        ObservableList <String> list = FXCollections.observableArrayList("8:00am - 9:00am","9:00am - 10:00am","10:00am - 11:00am","11:00am - 12:00am","1:00pm - 2:00pm","2:00pm - 3:00pm","3:00pm - 4:00pm","4:00pm  - 5:00pm");
        time.setItems(list);

        ObservableList <String> list1 = FXCollections.observableArrayList("Tooth Extractions","Teeth Whitening","Dental Sealants","Root Canal Therapy","Dentures","Teeth Cleanings","Dental Veneers","Invisalign","Cosmetic Fillings","Bridgework","Dental Crowns","Dental Bonding");
        services.setItems(list1);
    }



    Connection con = null;
    private Statement sts;
    private PreparedStatement pts;
    private ResultSet result;

   public Connection dentaldb(String dbname, String user, String pass) {

        try {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname,user,pass);

            if (con!= null) {
                System.out.print("Connection Established");

            } else {
                System.out.print("Connection Failed");
            }

        } catch (Exception e) {
            System.out.print(e);
        }
        return con;
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
}
