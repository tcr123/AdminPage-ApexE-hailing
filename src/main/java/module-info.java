module com.example.ehailingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ehailingapp to javafx.fxml;
    exports com.example.ehailingapp;
}