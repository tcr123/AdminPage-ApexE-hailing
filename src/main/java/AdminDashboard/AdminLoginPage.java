package AdminDashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminLoginPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminLoginPage.class.getResource("adminLoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
//        stage.setTitle("Apex E-Hailing");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void accessAdminLoginPage() {
        launch();
    }
}
