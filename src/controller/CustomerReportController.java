package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the CustomerReport view
 */
public class CustomerReportController implements Initializable {

    public TableView ReportTable;
    public TableColumn<Map, String> CustomerID;
    public TableColumn<Map, String> ContactID;
    public TableColumn<Map, String> Title;
    public TableColumn<Map, String> Total;

    /**
     * This will initialize the scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Map<String, Object>> items = FXCollections.observableArrayList();
        CustomerID.setCellValueFactory(new MapValueFactory<>("customerID"));
        ContactID.setCellValueFactory(new MapValueFactory<>("type"));
        Title.setCellValueFactory(new MapValueFactory<>("start"));
        Total.setCellValueFactory(new MapValueFactory<>("total"));

        try{
            String sql = "SELECT Customer_ID, Type, MONTHNAME(Start) as Month, count(*) as NUM FROM appointments GROUP BY Customer_ID, Type, MONTHNAME(Start)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Map<String, Object> item = new HashMap<>();
                String customerID = String.valueOf(rs.getInt("Customer_ID"));
                String total = String.valueOf(rs.getInt("NUM"));
                String type = rs.getString("Type");
                String start = rs.getString("Month");

                item.put("customerID", customerID);
                item.put("total", total);
                item.put("type", type);
                item.put("start", start);
                items.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ReportTable.setItems(items);
    }

    /**
     * This will take you back to the appointment view scene
     * @param actionEvent on click of the back button
     * @throws IOException
     */
    public void onBackHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Appointments");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }
}
