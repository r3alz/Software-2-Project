package model;

import DAO.JDBC;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


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
                int contactID = rs.getInt("Contact_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = (LocalDateTime) rs.getObject("Start");
                LocalDateTime end = (LocalDateTime) rs.getObject("End");
                String createDate = rs.getString("Create_Date");
                String createBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                Appointment a = new Appointment(appointmentId, customerId, userId, title, description, location, type, start, end, createDate, createBy, lastUpdate, lastUpdatedBy, contactID);
                aList.add(a);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aList;
    }

    public static void addAppointment(String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setObject(5, startDateTime);
        ps.setObject(6, endDateTime);
        ps.setObject(7, createDate);
        ps.setString(8, createdBy);
        ps.setObject(9, lastUpdate);
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);
        ps.executeUpdate();
    }

    public static void getAppointmentAlert() {
        User currentUser = LoginController.getLoggedInUser();
        LocalDateTime dateTime = LocalDateTime.now();
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dateTime.atZone(zoneID);
        LocalDateTime dateTimeStart = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime dateTimeEnd = dateTimeStart.plusMinutes(15);

        try{
            String sql = "Select * FROM appointments WHERE Start BETWEEN '" + dateTimeStart + "' AND '" + dateTimeEnd + "' AND User_ID = " + currentUser.getId();

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                LocalDateTime start = (LocalDateTime) rs.getObject("Start");

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment at " + start + ".  The appointment ID is: " + appointmentID);
                alert.showAndWait();
                return;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
