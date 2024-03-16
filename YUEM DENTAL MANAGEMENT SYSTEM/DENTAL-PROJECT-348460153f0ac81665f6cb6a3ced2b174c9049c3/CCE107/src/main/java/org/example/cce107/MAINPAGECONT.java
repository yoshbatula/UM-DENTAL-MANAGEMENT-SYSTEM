package org.example.cce107;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

import javafx.animation.TranslateTransition;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

import javafx.stage.Stage;
import javafx.util.Duration;
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
    private ComboBox<String> time;

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
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b1;

    @FXML
    private Button updateBTN;

    @FXML
    private Button update2;
    @FXML
    private TextField adressTF;

    @FXML
    private TextField otherservice;

    private ObservableList<String> list = FXCollections.observableArrayList("8:00am - 9:00am", "9:00am - 10:00am", "10:00am - 11:00am", "11:00am - 12:00am", "1:00pm - 2:00pm", "2:00pm - 3:00pm", "3:00pm - 4:00pm", "4:00pm  - 5:00pm");

    private ObservableList<String> list1 = FXCollections.observableArrayList("Tooth Extractions", "Teeth Whitening", "Dental Sealants", "Root Canal Therapy", "Dentures", "Teeth Cleanings", "Dental Veneers", "Invisalign", "Cosmetic Fillings", "Bridgework", "Dental Crowns", "Dental Bonding");
    @FXML
    void select_services(ActionEvent event) {

    }

    @FXML
    void select_time(ActionEvent event) throws SQLException {
        LocalDate selectedDate = tf_date.getValue();

        if (selectedDate != null) {
            String selectedTime = (String) time.getSelectionModel().getSelectedItem();

            try {
                if (isTimeSlotUnavailable(selectedDate, selectedTime)) {

                    list.remove(selectedTime);
                    time.setItems(list);

                } else if (!list.contains(selectedTime)) {

                    list.add(selectedTime);
                    time.setItems(list);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            time.setItems(list);
        }
    }


    @FXML
    void doctorBTN (MouseEvent event) {
    }

    @FXML
    void homeBTN(MouseEvent event) {
    }

    @FXML
    void addAppBTN(MouseEvent event) {
    }

    @FXML
    private AnchorPane navbar;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        services.setItems(list1);

        alert = new Alert(Alert.AlertType.INFORMATION);

        home_btn.setOnMouseClicked(event -> {
            TranslateTransition buttons = new TranslateTransition();
            buttons.setDuration(Duration.seconds(0.4));

            buttons.setToX(0);
            buttons.play();
        });

        appoint_btn.setOnMouseClicked(event -> {
            TranslateTransition buttons = new TranslateTransition();
            buttons.setDuration(Duration.seconds(0.4));

            buttons.setToX(0);
            buttons.play();
        });

        doctor_btn.setOnMouseClicked(event -> {
            TranslateTransition buttons = new TranslateTransition();
            buttons.setDuration(Duration.seconds(0.4));

            buttons.setToX(0);
            buttons.play();
        });

        try {
            showTable();
            appoint_table.refresh();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTimeComboBox() {
        time.setItems(list);
    }
    private Connection conn;
    private Statement sts;
    private PreparedStatement pts;
    private ResultSet result;

    private Alert alert;

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

        if (event.getSource() == b1) {
            Image swap1 = new Image(getClass().getResourceAsStream("/org/example/cce107/fxml/images/yosh.png"));
            imageV.setImage(swap1);
        }
        else if (event.getSource() == b2) {
            Image swap2 = new Image(getClass().getResourceAsStream("/org/example/cce107/fxml/images/myel.png"));
            imageV.setImage(swap2);
        } else if (event.getSource() == b3) {
            Image swap3 = new Image(getClass().getResourceAsStream("/org/example/cce107/fxml/images/mark.png"));
            imageV.setImage(swap3);
        } else if (event.getSource() == b4) {
            Image swap4 = new Image(getClass().getResourceAsStream("/org/example/cce107/fxml/images/ken.png"));
            imageV.setImage(swap4);
        } else if (event.getSource() == b5) {
            Image swap5 = new Image(getClass().getResourceAsStream("/org/example/cce107/fxml/images/ryy.png"));
            imageV.setImage(swap5);
        }
    }

    public void personalInfo(ActionEvent event) throws SQLException {


        LocalDate selectedDate = tf_date.getValue();

        //if (selectedDate == null) {
            //alert.setAlertType(Alert.AlertType.CONFIRMATION);
            //alert.setTitle("Error");
            //alert.setHeaderText("Invalid Date");
            //alert.setContentText("Please select a date for the appointment.");
            //alert.showAndWait();
            //return;
        //}

        if (full_tf.getText().isEmpty() || age_tf.getText().isEmpty()) {
            full_req.setText("This is required.");
            age_req.setText("This is required");

            submit.setStyle("-fx-border-color: red; -fx-background-color: #081F5C; -fx-text-fill: #e3ded3");

        }
        if (email_tf.getText().isEmpty()) {
            mail_req.setText("This is required");

            submit.setStyle("-fx-border-color: red; -fx-background-color: #081F5C; -fx-text-fill: #e3ded3");
        }
        if (mobile_tf.getText().isEmpty() || gender_tf.getText().isEmpty()) {
            mobile_req.setText("This is required");
            gender_req.setText("This is required");

            submit.setStyle("-fx-border-color: red; -fx-background-color: #081F5C; -fx-text-fill: #e3ded3");

        } else {

            // In here as you can see we store some data into the database.
            try  {
                pts = conn.prepareStatement("INSERT INTO \"addInfo\" (\"Fullname\", \"Age\", \"Gender\", \"MobileNo\", \"Email\", \"Address\", \"Date\", \"Time\", \"Services\")\n" +
                        "VALUES (?,?,?,?,?,?,?,?,?)");

                pts.setString(1, full_tf.getText());
                pts.setInt(2, Integer.valueOf(age_tf.getText()));
                pts.setString(3, gender_tf.getText());
                pts.setString(4, mobile_tf.getText());
                pts.setString(5, email_tf.getText());
                pts.setString(6, adressTF.getText());
                pts.setDate(7, Date.valueOf(tf_date.getValue()));
                pts.setString(8, (String) time.getSelectionModel().getSelectedItem());
                pts.setString(9, (String) services.getSelectionModel().getSelectedItem());
                pts.executeUpdate();
                ClearForm();

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.show();

                full_req.setText("");
                age_req.setText("");
                address_req.setText("");
                mail_req.setText("");
                mobile_req.setText("");
                gender_req.setText("");
                submit.setStyle("-fx-border-color: none; -fx-background-color: #081F5C; -fx-text-fill: #e3ded3");



                pts.close();
                showTable();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean isTimeSlotUnavailable(LocalDate selectedDate, String selectedTime) throws SQLException {
        String query = "SELECT COUNT(*) FROM \"addInfo\" WHERE \"Date\" = ? AND \"Time\" = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(query)) {
            checkStmt.setDate(1, Date.valueOf(selectedDate));
            checkStmt.setString(2, selectedTime);

            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }



    // The clearform method as you may know it's for clearing the textfield and combo box after the execution of preparedStatement.
    public void ClearForm() {
        full_tf.setText("");
        age_tf.setText("");
        gender_tf.setText("");
        mobile_tf.setText("");
        email_tf.setText("");
        adressTF.setText("");
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
                        result.getString("services"),
                        result.getString("address")
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

            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRM");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Deleted!");
            Optional<ButtonType> click = alert.showAndWait();

            showTable();
        }catch (SQLException event) {
            event.printStackTrace();
        }
    }

    public void setUpdateBTN(ActionEvent event) throws SQLException {
        if (event.getSource() == updateBTN) {

            home_form.setVisible(false);
            appoint_form.setVisible(true);
            doc_form.setVisible(false);


            InfoData selectedItem = appoint_table.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                full_tf.setText(selectedItem.getFullname());
                age_tf.setText(String.valueOf(selectedItem.getAge()));
                gender_tf.setText(selectedItem.getGender());
                mobile_tf.setText(selectedItem.getMobileno());
                email_tf.setText(selectedItem.getEmail());
                adressTF.setText(selectedItem.getAddress());
                tf_date.setValue(selectedItem.getDate().toLocalDate());
                time.getSelectionModel().select(selectedItem.getTime());
                services.getSelectionModel().select(selectedItem.getServices());
            }

        } else if (event.getSource() == update2) {
            String fullname = full_tf.getText();
            String ageString = age_tf.getText();

            if (ageString.isEmpty()) {
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageString);
            } catch (NumberFormatException e) {

                return;
            }

            String gender = gender_tf.getText();
            String mobileNo = mobile_tf.getText();
            String email = email_tf.getText();
            String address = adressTF.getText();
            LocalDate date = tf_date.getValue();
            String timeSlot = time.getSelectionModel().getSelectedItem();
            String service = String.valueOf(services.getSelectionModel().getSelectedItem());

            String sql = "UPDATE \"addInfo\"\n" +
                    "SET \"Fullname\"=?, \"Age\"=?, \"Gender\"=?, \"MobileNo\"=?, \"Email\"=?, \"Address\"=?, \"Date\"=?, \"Time\"=?, \"Services\"=?\n" +
                    "WHERE \"Fullname\"=?";

            try {
                pts = conn.prepareStatement(sql);

                pts.setString(1, fullname);
                pts.setInt(2, age);
                pts.setString(3, gender);
                pts.setString(4, mobileNo);
                pts.setString(5, email);
                pts.setString(6, address);
                pts.setDate(7, Date.valueOf(date));
                pts.setString(8, timeSlot);
                pts.setString(9, service);

                pts.setString(10, fullname);

                int rowsUpdated = pts.executeUpdate();
                if (rowsUpdated > 0) {
                    Platform.runLater(() -> {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Update Successful");
                        alert.show();
                    });
                    ClearForm();
                } else {
                    Platform.runLater(() -> {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText(null);
                        alert.setContentText("Update Failed");
                        alert.show();
                    });
                }
                pts.close();
                showTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
