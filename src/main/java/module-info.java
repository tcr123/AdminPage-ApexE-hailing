module com.example.ehailingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens AdminDashboard to javafx.fxml;
    exports AdminDashboard;
}