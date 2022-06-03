package AdminDashboard;

import DbAccess.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class AdminAddDriver implements Initializable {
    @FXML
    private Button addDriverButton,
            exitButton;
    @FXML
    private TextField name_field;
    @FXML
    private RadioButton four_seater,
            six_seater;
    @FXML
    private Label alert_message;
    @FXML
    private ChoiceBox<String> location_choiceBox;

    private String[] locations = {"SLUMMLAKES","CONTAINEMENT","RUNOFF","THE PIT","AIRBASE","BUNKER","THUNDERDOME","SKULL TOWN","MARKET","WATER TREATMENT","REPULSOR","THE CAGE","ARTILLERY","RELAY","WETLANDS","SWAMPS","HYDRO DAM"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        location_choiceBox.getItems().addAll(locations);
    }

    public void addDriverButtonOnAction(ActionEvent event) throws Exception {
        Connection conn = DBConnector.getConnection();
        if (name_field.getText().isBlank()){
            alert_message.setText("Please enter driver name!");
        } else if (!four_seater.isSelected() && !six_seater.isSelected()){
            alert_message.setText("Please select vehicle capacity!");
        } else if (location_choiceBox.getValue().isBlank()){
            alert_message.setText("Please select a initial location of driver!");
        } else {
            String name = name_field.getText();
            int capacity = 0;
            if (four_seater.isSelected()){
                capacity = 4;
            } else if (six_seater.isSelected()){
                capacity = 6;
            }
            String location = location_choiceBox.getValue();
            PreparedStatement addDriverStatement = conn.prepareStatement("INSERT INTO driver VALUES ('"+name+"', '"+capacity+"', '"+location+"', 'Available', NULL, 4.0)");
            addDriverStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Driver");
            alert.setHeaderText("New driver "+name+" has been added!");
            alert.showAndWait();
            Stage stage = (Stage) addDriverButton.getScene().getWindow();
            stage.close();
        }
    }

    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}
