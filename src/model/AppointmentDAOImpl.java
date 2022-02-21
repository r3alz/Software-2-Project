package model;

import util.JDBC;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created By Chris Ortiz
 * AppointmentDAOImpl class used for getting all the data from the appointments table in SQL and adding to observableArrayList
 */
public class AppointmentDAOImpl {
    /**
     * Gets all of the appointments from the appointments table and adds them as Appointment Objects to an observableArrayList
     * @return aList an observableArrayList of Appointment Ojbects
     */
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

    /**
     * Adds (Inserts) an appointment to the appointments table
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
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

    /**
     * Updtes an appointment in the appointments table
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLException
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = " + appointmentID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setObject(5, startDateTime);
        ps.setObject(6, endDateTime);
        ps.setObject(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.executeUpdate();
    }

    /**
     * Alerts upon login if there is an appointment within 15 minutes of login time
     */
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

    public static Boolean appointmentOverlap(LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointment> aList = AppointmentDAOImpl.getAllAppointments();
        for(Appointment a: aList) {
            if(start.isBefore(a.getEndDateTime()) && end.isAfter(a.getStartDateTime())) {
                return true;
            }
        }

        return false;
    }
}
