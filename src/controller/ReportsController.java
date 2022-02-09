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

public class ReportsController implements Initializable {

    public TableView ReportTable;
    public TableColumn<Map, String> CustomerID;
    public TableColumn<Map, String> ContactID;
    public TableColumn<Map, String> Title;
    public TableColumn<Map, String> Total;

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
}
