package org.example.cce107;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class MAINLUNCH extends Application {

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MAINLUNCH.class.getResource("fxml/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("YUEM DENTAL CLINIC MAIN PAGE!");

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
