package org.example.cce107;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.Chronology;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
    private TableView<InfoData> appoint_table;
    @FXML
    private TableColumn<InfoData, Integer> age_table;
    @FXML
    private TableColumn<InfoData, Date> date_table;
    @FXML
    private TableColumn<InfoData, String> email_table;
    @FXML
    private TableColumn<InfoData, String> mobile_table;
    @FXML
    private TableColumn<InfoData, String> name_table;
    @FXML
    private TableColumn<InfoData, String> service_table;
    @FXML
    private TableColumn<InfoData, String> sex_table;
    @FXML
    private TableColumn<InfoData, String> time_table;
    @FXML
    private Label total;

    @FXML
    private ImageView imageV;
    @FXML
    private Button b2;
    //Image swap2 = new Image(getClass().getResourceAsStream("ken.jpg"));
    //public void displayImage() {
        //imageV.setImage(swap2);
    //}
    @FXML
    private Button b3;
    //Image swap3 = new Image(getClass().getResourceAsStream("myel.jpg"));
    //public void displayImageq() {
        //imageV.setImage(swap3);
    //}
    @FXML
    private Button b4;
    //Image swap4 = new Image(getClass().getResourceAsStream("mark.jpg"));
    //public void displayImage2() {
        //imageV.setImage(swap4);
    //}
    @FXML
    private Button b5;
    //Image swap5 = new Image(getClass().getResourceAsStream("ryy.jpg"));
    //public void displayImage3() {
        //imageV.setImage(swap5);
    //}
    @FXML
    private Button b1;
    //Image swap1 = new Image(getClass().getResourceAsStream("yosh.jpg"));
    //public void displayImage4() {
        //imageV.setImage(swap1);
    //}
    private ObservableList<String> list = FXCollections.observableArrayList("8:00am - 9:00am", "9:00am - 10:00am", "10:00am - 11:00am", "11:00am - 12:00am", "1:00pm - 2:00pm", "2:00pm - 3:00pm", "3:00pm - 4:00pm", "4:00pm  - 5:00pm");

    ObservableList<String> list1 = FXCollections.observableArrayList("Tooth Extractions", "Teeth Whitening", "Dental Sealants", "Root Canal Therapy", "Dentures", "Teeth Cleanings", "Dental Veneers", "Invisalign", "Cosmetic Fillings", "Bridgework", "Dental Crowns", "Dental Bonding");
    @FXML
    void select_services(ActionEvent event) {

    }

    @FXML
    void select_time(ActionEvent event) {

        LocalDate selectedDate = tf_date.getValue();
        if (selectedDate != null) {
            time.setItems(getAvailableTimes(selectedDate));
        } else {
            time.setItems(list);
        }
    }

    private ObservableList<String> getAvailableTimes(LocalDate selectedDate) {
        ObservableList<String> availableTimes = FXCollections.observableArrayList();
        ObservableList<InfoData> appointments;
        try {
            appointments = appoint_list();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (appointments != null) {
            for (String timeOption : list) {
                boolean isTimeAvailable = true;
                if (selectedDate != null) {
                    for (InfoData appointment : appointments) {
                        if (appointment.getDate() != null && appointment.getDate().toLocalDate().equals(selectedDate) && appointment.getTime().equals(timeOption)) {
                            isTimeAvailable = false;
                            break;
                        }
                    }
                    if (isTimeAvailable) {
                        availableTimes.add(timeOption);
                    }
                }
            }
        }

        return availableTimes;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();
        time.setItems(getAvailableTimes(date));
        services.setItems(list1);

        if (date.equals(true)) {
            
        }
        try {
            showTable();
            appoint_table.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection conn;
    private Statement sts;
    private PreparedStatement pts;
    private ResultSet result;

    public MAINPAGECONT() {
        dentaldb("postgres", "postgres", "admin");
    }


    // In this block of code it's for connecting into jdbc.
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

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {

            home_form.setVisible(true);
            appoint_form.setVisible(false);
            doc_form.setVisible(false);
            list.remove(time.getSelectionModel().getSelectedItem());

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

        } else {

            // The preparedStatement is used for executing and in this block of codes we insert some data and execute it.
            try  {
                pts = conn.prepareStatement("INSERT INTO \"addInfo\" (\"Fullname\", \"Age\", \"Gender\", \"MobileNo\", \"Email\", \"Address\", \"Date\", \"Time\", \"Services\")\n" +
                        "VALUES (?,?,?,?,?,?,?,?,?)");

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

                pts.close();
                showTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // The clearform method as you may know it's for clearing the textfield and combo box after the execution of preparedStatement.
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

    // In here we use the preparedStatement to execute and select all the queries to print in the table view.
    public ObservableList<InfoData> appoint_list() throws SQLException {
        ObservableList<InfoData> list = FXCollections.observableArrayList();
        pts = conn.prepareStatement("select *\n" +
                "from \"addInfo\";");

        try {
            sts = conn.createStatement();
            result = pts.executeQuery();

            while (result.next()) {
                InfoData data;
                data = new InfoData(
                        result.getString("fullname"),
                        result.getInt("age"),
                        result.getString("gender"),
                        result.getString("mobileno"),
                        result.getString("email"),
                        result.getDate("date"),
                        result.getString("time"),
                        result.getString("services")
                );
                        list.add(data);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // This method is for showing the data into the tableview
    public void showTable() throws SQLException {
        ObservableList<InfoData> list = appoint_list();

        name_table.setCellValueFactory(new PropertyValueFactory<InfoData, String>("fullname"));
        age_table.setCellValueFactory(new PropertyValueFactory<InfoData, Integer>("age"));
        sex_table.setCellValueFactory(new PropertyValueFactory<InfoData, String>("gender"));
        mobile_table.setCellValueFactory(new PropertyValueFactory<InfoData, String>("mobileno"));
        email_table.setCellValueFactory(new PropertyValueFactory<InfoData, String>("email"));
        date_table.setCellValueFactory(new PropertyValueFactory<InfoData, Date>("date"));
        time_table.setCellValueFactory(new PropertyValueFactory<InfoData, String>("time"));
        service_table.setCellValueFactory(new PropertyValueFactory<InfoData, String>("services"));

        appoint_table.setItems(list);
        total.setText(String.valueOf(appoint_table.getItems().size()));


    }

    public void deleteBTN(ActionEvent e) throws SQLException {

        String selectedFullname = appoint_table.getSelectionModel().getSelectedItem().getFullname();

        try {
            pts = conn.prepareStatement("DELETE FROM \"addInfo\" WHERE \"Fullname\" = ?");
            pts.setString(1, selectedFullname);

            appoint_table.getItems().remove(appoint_table.getSelectionModel().getSelectedItem());
            total.setText(String.valueOf(appoint_table.getItems().size()));
            pts.executeUpdate();

            showTable();
        }catch (SQLException event) {
            event.printStackTrace();
        }
    }

}
