package model;

import util.JDBC;
import controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;

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
        ObservableList<Contact> contactList = ContactDAOImpl.getAllContacts();

        try{
            String sql = "Select * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String contactName = "";
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                String createDate = rs.getString("Create_Date");
                String createBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                LocalDateTime lStart = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
                LocalDateTime lEnd = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

                for(Contact c: contactList) {
                    if(c.getContactID() == contactID) {
                        contactName = c.getContactName();
                    }
                }

                Appointment a = new Appointment(appointmentId, customerId, userId, title, description, location, type, lStart, lEnd, createDate, createBy, lastUpdate, lastUpdatedBy, contactID, contactName);
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
    public static void addAppointment(String title, String description, String location, String type, ZonedDateTime startDateTime, ZonedDateTime endDateTime, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
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
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, ZonedDateTime startDateTime, ZonedDateTime endDateTime, ZonedDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
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
        ZonedDateTime dateTimeStart = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime dateTimeEnd = dateTimeStart.plusMinutes(15);

        System.out.println(dateTimeStart);

        try{
            String sql = "Select * FROM appointments WHERE Start BETWEEN '" + dateTimeStart + "' AND '" + dateTimeEnd + "' AND User_ID = " + currentUser.getId();

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment at " + start + ".  The appointment ID is: " + appointmentID);
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have no upcoming appointments");
            alert.showAndWait();
            return;

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

    public static Boolean appointmentOverlapOnUpdate(LocalDateTime start, LocalDateTime end, int appointmentID) {
        ObservableList<Appointment> aList = AppointmentDAOImpl.getAllAppointments();
        for(Appointment a: aList) {
            if(start.isBefore(a.getEndDateTime()) && end.isAfter(a.getStartDateTime())) {
                if(a.getAppointmentID() != appointmentID) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Boolean appointmentDuringHours(LocalDateTime start, LocalDateTime end) {
        LocalDate date = start.toLocalDate();
        ZonedDateTime zStart = ZonedDateTime.of(start, ZoneId.systemDefault());
        ZonedDateTime zEnd = ZonedDateTime.of(end, ZoneId.systemDefault());
        ZonedDateTime zdtStart = ZonedDateTime.of(date, LocalTime.of(8,0), ZoneId.of("America/New_York"));
        ZonedDateTime zdtEnd = ZonedDateTime.of(date, LocalTime.of(22,0), ZoneId.of("America/New_York"));

        if (zStart.isBefore(zdtStart) | zStart.isAfter(zdtEnd) | zEnd.isBefore(zdtStart) | zEnd.isAfter(zdtEnd)) {
            return false;
        }
        else {
            return true;
        }
    }
}
