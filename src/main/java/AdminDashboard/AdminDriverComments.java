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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminDriverComments implements Initializable {
    @FXML
    private Button backwardButton,
            refreshButton,
            exitButton;
    @FXML
    private Hyperlink toMenuHyperlink, toDriverListHyperlink;
    @FXML
    private TableView<Comments> commentsTable;
    @FXML
    private TableColumn<Comments, String> comments_col;
    @FXML
    private Label driverNameLabel;

    Connection conn = null;
    ResultSet commentsRS = null;
    PreparedStatement sqlStatement = null;
    ObservableList<Comments> oblist = FXCollections.observableArrayList();

    Timeline timeline;

    private static String driverName = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverNameLabel.setText(driverName);
        try {
            conn = DBConnector.getConnection();
            commentsRS = conn.createStatement().executeQuery("SELECT * FROM comment WHERE driver = '"+driverName+"'");
            while (commentsRS.next()){
                oblist.add(new Comments(commentsRS.getString("comment")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        comments_col.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentsTable.setItems(oblist);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
//                    System.out.println("Testing");
                    refreshTable();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void deleteCommentButtonOnAction(ActionEvent event) throws SQLException {
        if (commentsTable.getSelectionModel().getSelectedItem() != null){
            String selectedComment = commentsTable.getSelectionModel().getSelectedItem().getComment();
            sqlStatement = conn.prepareStatement("DELETE FROM comment WHERE comment = '"+selectedComment+"' LIMIT 1");
            sqlStatement.executeUpdate();
        }
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

    public void previousButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminDriverListPage.fxml"));
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
            commentsRS = conn.createStatement().executeQuery("SELECT * FROM comment WHERE driver = '"+driverName+"'");
            while (commentsRS.next()){
                oblist.add(new Comments(commentsRS.getString("comment")));
            }
            commentsTable.setItems(oblist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDriverName(String name){
        driverName = name;
    }

    public void toMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMenuPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        timeline.stop();
    }
}
