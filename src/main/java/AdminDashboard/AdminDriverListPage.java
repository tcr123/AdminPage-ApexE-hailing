package AdminDashboard;

import DbAccess.DBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminDriverListPage implements Initializable {
    @FXML
    private Button backwardButton;
    @FXML
    private Hyperlink toMenuHyperlink;
    @FXML
    private TableView<Driver> driverTable;
    @FXML
    private TableColumn<Driver, String> name_col;
    @FXML
    private TableColumn<Driver, String> icnumber_col;

    ObservableList<Driver> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection conn = DBConnector.getConnection();
            ResultSet driverRS = conn.createStatement().executeQuery("SELECT * FROM driverlist");
            while (driverRS.next()){
                oblist.add(new Driver(driverRS.getString("Name"), driverRS.getString("IcNumber")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        icnumber_col.setCellValueFactory(new PropertyValueFactory<>("icNumber"));

        driverTable.setItems(oblist);
    }

    public void toMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMenuPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void refreshTable(){

    }
}
