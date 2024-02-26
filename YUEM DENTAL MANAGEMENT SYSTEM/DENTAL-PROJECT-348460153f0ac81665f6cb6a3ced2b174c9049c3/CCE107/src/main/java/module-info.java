    module org.example.cce107 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
        requires java.sql;

        opens org.example.cce107 to javafx.fxml;
    exports org.example.cce107;
}