package AdminDashboard;

import DbAccess.DBConnector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminCustomerStatus implements Initializable {
    @FXML
    private Button backwardButton,
            refreshButton,
            exitButton;
    @FXML
    private Hyperlink toMenuHyperlink;
    @FXML
    private TableView<Driver> driverTable;
    @FXML
    private TableColumn<Driver, String> name_col;
    @FXML
    private Text realTimeText;

    Connection conn = null;
    ResultSet driverRS = null;
    PreparedStatement sqlStatement = null;
    ObservableList<Driver> oblist = FXCollections.observableArrayList();

    Timeline timeline, realTimeTL;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conn = DBConnector.getConnection();
            driverRS = conn.createStatement().executeQuery("SELECT * FROM driverlist");
            while (driverRS.next()){
                oblist.add(new Driver(driverRS.getString("Name"), driverRS.getString("IcNumber")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        driverTable.setItems(oblist);

        //Update table every 0.5 second
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {
                    refreshTable();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //Update real timer every 1 second
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        realTimeTL = new Timeline(
                new KeyFrame(Duration.seconds(1.0), e -> {
                    final String timeNow = sdf.format(new Date());
                    realTimeText.setText(timeNow);
                })
        );
        realTimeTL.setCycleCount(Timeline.INDEFINITE);
        realTimeTL.play();
    }

    public void exit(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit confirmation");
        alert.setContentText("Do you want to exit?: ");
        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public void toMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMenuPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        timeline.stop();
        realTimeTL.stop();
    }

    public void refreshButtonOnAction(ActionEvent event){
        refreshTable();
    }

    public void refreshTable(){
        try{
            oblist.clear();
            driverRS = conn.createStatement().executeQuery("SELECT * FROM driverlist");
            while (driverRS.next()){
                oblist.add(new Driver(driverRS.getString("Name"), driverRS.getString("IcNumber")));
            }
            driverTable.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
