package model;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAOImpl {
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> aList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String createDate = rs.getString("Create_Date");
                String createBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Appointment a = new Appointment(appointmentId, customerId, userId, title, description, location, type, start, end, createDate, createBy, lastUpdate, lastUpdatedBy);
                aList.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aList;
    }
}
