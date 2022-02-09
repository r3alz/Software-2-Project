package controller;

import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Reports3Controller implements Initializable {
    public TableView Reports3Table;
    public TableColumn State;
    public TableColumn AppointmentID;
    public TableColumn Title;
    public TableColumn Type;
    public TableColumn Description;
    public TableColumn Start;
    public TableColumn End;
    public TableColumn CustomerID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Map<String, Object>> items = FXCollections.observableArrayList();
        State.setCellValueFactory(new MapValueFactory<>("state"));
        AppointmentID.setCellValueFactory(new MapValueFactory<>("appointmentID"));
        Title.setCellValueFactory(new MapValueFactory<>("title"));
        Type.setCellValueFactory(new MapValueFactory<>("type"));
        Description.setCellValueFactory(new MapValueFactory<>("description"));
        Start.setCellValueFactory(new MapValueFactory<>("start"));
        End.setCellValueFactory(new MapValueFactory<>("end"));
        CustomerID.setCellValueFactory(new MapValueFactory<>("customerID"));

        try{
            String sql = "SELECT first_level_divisions.Division as State, appointments.Appointment_ID, appointments.Title, appointments.Type, appointments.Description, appointments.Start, appointments.End, appointments.Customer_ID FROM appointments INNER JOIN customers ON appointments.Customer_ID = customers.Customer_ID INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Map<String, Object> item = new HashMap<>();
                String state = rs.getString("State");
                String appointmentID = String.valueOf(rs.getInt("Appointment_ID"));
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String customerID = rs.getString("Customer_ID");

                item.put("state", state);
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
        Reports3Table.setItems(items);
    }
}
