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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminCustomerList implements Initializable {
    @FXML
    private Button backwardButton,
            refreshButton,
            exitButton;
    @FXML
    private Button addDriverButton;
    @FXML
    private Hyperlink toMenuHyperlink;
    @FXML
    private TableView<Driver> driverTable;
    @FXML
    private TableColumn<Driver, String> name_col;
    @FXML
    private TableColumn<Driver, String> icnumber_col;

    Connection conn = null;
    ResultSet driverRS = null;
    PreparedStatement sqlStatement = null;
    ObservableList<Driver> oblist = FXCollections.observableArrayList();

    Timeline timeline;

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
        icnumber_col.setCellValueFactory(new PropertyValueFactory<>("icNumber"));

        driverTable.setItems(oblist);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
//                    System.out.println("Testing");
                    refreshTable();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void addCustomerButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminAddDriverPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Add Driver");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(backwardButton.getScene().getWindow());
        stage.show();
//        sqlStatement = conn.prepareStatement("INSERT INTO driverlist ")
//        refreshTable();
    }

    public void deleteCustomerButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminDeleteDriverPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Delete Driver");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(backwardButton.getScene().getWindow());
        stage.show();
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