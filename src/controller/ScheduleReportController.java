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
 * Used to control the ScheduleReport view
 */
public class ScheduleReportController implements Initializable {
    public TableColumn<Map, String> ContactName;
    public TableColumn<Map, String> AppointmentID;
    public TableColumn<Map, String> Title;
    public TableColumn<Map, String> Type;
    public TableColumn<Map, String> Description;
    public TableColumn<Map, String> Start;
    public TableColumn<Map, String> End;
    public TableColumn<Map, String> CustomerID;
    public TableView Reports2Table;

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Map<String, Object>> items = FXCollections.observableArrayList();
        ContactName.setCellValueFactory(new MapValueFactory<>("contactName"));
        AppointmentID.setCellValueFactory(new MapValueFactory<>("appointmentID"));
        Title.setCellValueFactory(new MapValueFactory<>("title"));
        Type.setCellValueFactory(new MapValueFactory<>("type"));
        Description.setCellValueFactory(new MapValueFactory<>("description"));
        Start.setCellValueFactory(new MapValueFactory<>("start"));
        End.setCellValueFactory(new MapValueFactory<>("end"));
        CustomerID.setCellValueFactory(new MapValueFactory<>("customerID"));

        try{
            String sql = "SELECT contacts.Contact_Name, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID FROM appointments INNER JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Map<String, Object> item = new HashMap<>();
                String contactName = rs.getString("Contact_Name");
                String appointmentID = String.valueOf(rs.getInt("Appointment_ID"));
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String customerID = rs.getString("Customer_ID");

                item.put("contactName", contactName);
                item.put("appointmentID", appointmentID);
                item.put("title", title);
                item.put("type", type);
                item.put("description", description);
                item.put("start", start);
                item.put("end", end);
                item.put("customerID", customerID);
                items.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Reports2Table.setItems(items);
    }

    /**
     *
     * @param actionEvent
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
