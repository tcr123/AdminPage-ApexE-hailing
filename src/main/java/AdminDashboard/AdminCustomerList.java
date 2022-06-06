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
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> username_col;
    @FXML
    private TableColumn<Customer, String> status_col;
    @FXML
    private TableColumn<Customer, String> expectedArrivalTime_col;
    @FXML
    private TableColumn<Customer, Integer> capacity_col;
    @FXML
    private TableColumn<Customer, String> startingPoint_col;
    @FXML
    private TableColumn<Customer, String> destination_col;


    Connection conn = null;
    ResultSet customerRS = null;
    PreparedStatement sqlStatement = null;
    ObservableList<Customer> oblist = FXCollections.observableArrayList();

    Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conn = DBConnector.getConnection();
            customerRS = conn.createStatement().executeQuery("SELECT * FROM user");
            while (customerRS.next()){
                oblist.add(new Customer(customerRS.getString("username"), customerRS.getString("phone"),
                        customerRS.getString("password"), customerRS.getString("status"),
                        customerRS.getString("expected_arrival_time"), customerRS.getInt("capacity"),
                        customerRS.getString("starting_point"), customerRS.getString("destination")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        username_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        expectedArrivalTime_col.setCellValueFactory(new PropertyValueFactory<>("expectedArrivalTime"));
        capacity_col.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        startingPoint_col.setCellValueFactory(new PropertyValueFactory<>("startingPoint"));
        destination_col.setCellValueFactory(new PropertyValueFactory<>("destination"));



        customerTable.setItems(oblist);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {
//                    System.out.println("Testing");
                    refreshTable();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void deleteCustomerButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminDeleteDriverPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Delete Customer");
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
            customerRS = conn.createStatement().executeQuery("SELECT * FROM user");
            while (customerRS.next()){
                oblist.add(new Customer(customerRS.getString("username"), customerRS.getString("phone"),
                        customerRS.getString("password"), customerRS.getString("status"),
                        customerRS.getString("expected_arrival_time"), customerRS.getInt("capacity"),
                        customerRS.getString("starting_point"), customerRS.getString("destination")));
            }
            customerTable.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
