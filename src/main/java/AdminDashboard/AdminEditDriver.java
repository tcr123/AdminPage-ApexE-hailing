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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminEditDriver implements Initializable {
    @FXML
    private Button saveChangeButton,
            getDriverButton,
            exitButton;
    @FXML
    private TextField name_field, newName_field;
    @FXML
    private RadioButton four_seater,
            six_seater;
    @FXML
    private Label alert_message1,
            alert_message2;
    @FXML
    private ChoiceBox<String> location_choiceBox;

    private String[] locations = {"SLUMMLAKES","CONTAINEMENT","RUNOFF","THE PIT","AIRBASE","BUNKER","THUNDERDOME","SKULL TOWN","MARKET","WATER TREATMENT","REPULSOR","THE CAGE","ARTILLERY","RELAY","WETLANDS","SWAMPS","HYDRO DAM"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = null;
        try {
            conn = DBConnector.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        location_choiceBox.getItems().addAll(locations);
//        location_choiceBox.setValue();
    }


    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void getDriverButtonOnAction(ActionEvent event) throws Exception {
        Connection conn = DBConnector.getConnection();
        String name = name_field.getText();
        PreparedStatement statement = conn.prepareStatement("SELECT COUNT(name) AS got FROM driver WHERE name = '"+name+"'");
        ResultSet rs = statement.executeQuery();
        int got = 0;
        while (rs.next()){
            got = rs.getInt("got");
        }
        if (got == 0){
            alert_message1.setText("Driver \""+name+"\" not found!");
        } else {
            statement = conn.prepareStatement("SELECT * FROM driver WHERE name = '"+name+"'");
            rs = statement.executeQuery();
            String dbName = "", dbLocation = "";
            int dbCapacity = 0;
            while(rs.next()){
                dbName = rs.getString("name");
                dbCapacity = rs.getInt("capacity");
                dbLocation = rs.getString("location");
            }
            newName_field.setText(dbName);
            if (dbCapacity == 4) four_seater.setSelected(true);
            else if (dbCapacity == 6) six_seater.setSelected(true);
            location_choiceBox.setValue(dbLocation);
            name_field.setDisable(true);
        }
    }


    public void saveChangeButtonOnAction(ActionEvent event) throws Exception {
        Connection conn = DBConnector.getConnection();
        if (newName_field.getText().isBlank()){
            alert_message2.setText("Please enter driver name!");
        } else if (!four_seater.isSelected() && !six_seater.isSelected()){
            alert_message2.setText("Please select vehicle capacity!");
        } else if (location_choiceBox.getValue().isBlank()){
            alert_message2.setText("Please select a initial location of driver!");
        } else {
            String name = newName_field.getText();
            int capacity = 0;
            if (four_seater.isSelected()){
                capacity = 4;
            } else if (six_seater.isSelected()){
                capacity = 6;
            }
            String location = location_choiceBox.getValue();
            PreparedStatement addDriverStatement = conn.prepareStatement("UPDATE driver SET name = '"+name+"', capacity = '"+capacity+"', location = '"+location+"' WHERE name = '"+name_field.getText()+"'");
            addDriverStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit Driver");
            alert.setHeaderText("Information about driver "+name_field.getText()+" has been edited!");
            alert.showAndWait();
            Stage stage = (Stage) saveChangeButton.getScene().getWindow();
            stage.close();
        }
    }
}
