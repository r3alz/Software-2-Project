package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the AddAppointment view
 */
public class AddAppointmentController implements Initializable {
    public TextField Title;
    public TextField Description;
    public TextField Location;
    public TextField AppointmentID;
    public Button UpdateAppointment;
    public ComboBox ContactBox;
    public TextField Type;
    public TextField CustomerID;
    public TextField UserID;
    public Button Cancel;
    public DatePicker StartDate;
    public ComboBox Hour;
    public ComboBox Minutes;
    public ComboBox AmOrPm;
    public DatePicker EndDate;
    public ComboBox EndHour;
    public ComboBox EndMinutes;
    public ComboBox EndAmOrPm;

    private ObservableList<Contact> contactsList = ContactDAOImpl.getAllContacts();

    /**
     * This is used to add an appointment
     * @param actionEvent on click of the "Add appointment" button
     * @throws SQLException
     */
    public void onAddAppointmentHandler(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            String title = Title.getText();
            String description = Description.getText();
            String location = Location.getText();
            LocalDate startDate = StartDate.getValue();
            Contact contact = (Contact) ContactBox.getSelectionModel().getSelectedItem();
            int contactID = contact.getContactID();
            String type = Type.getText();
            LocalDate endDate = EndDate.getValue();
            String amOrPm = String.valueOf(AmOrPm.getValue());
            String endAmOrPm = String.valueOf(EndAmOrPm.getValue());
            int customerID = Integer.parseInt(CustomerID.getText());
            int userID = Integer.parseInt(UserID.getText());
            int startHour = Integer.parseInt(String.valueOf(Hour.getValue()));
            int startMinutes = Integer.parseInt(String.valueOf(Minutes.getValue()));

            if (amOrPm == "PM" && startHour < 12) {
                startHour += 12;
            }

            LocalTime startTime = LocalTime.of(startHour, startMinutes);
            int endHour = Integer.parseInt(String.valueOf(EndHour.getValue()));
            int endMinutes = Integer.parseInt(String.valueOf(EndMinutes.getValue()));

            if (endAmOrPm == "PM" && endHour < 12) {
                endHour += 12;
            }

            LocalTime endTime = LocalTime.of(endHour, endMinutes);
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
            String createdBy = LoginController.getLoggedInUser().getUsername();
            LocalDateTime createDate = LocalDateTime.now();

            ZonedDateTime endDateTimeZoned = endDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime zEndDateTime = endDateTimeZoned.withZoneSameInstant(ZoneId.of("UTC"));

            ZonedDateTime startDateTimeZoned = startDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime zStartDateTime = startDateTimeZoned.withZoneSameInstant(ZoneId.of("UTC"));

            ZonedDateTime createDateZoned = createDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zCreateDate = createDateZoned.withZoneSameInstant(ZoneId.of("UTC"));

            Boolean appointmentOverlap = AppointmentDAOImpl.appointmentOverlap(startDateTime, endDateTime);
            Boolean appointmentDuringHours = AppointmentDAOImpl.appointmentDuringHours(startDateTime, endDateTime);


            if(!appointmentOverlap && appointmentDuringHours) {
                AppointmentDAOImpl.addAppointment(title, description, location, type, zStartDateTime, zEndDateTime, zCreateDate, createdBy, zCreateDate, createdBy, customerID, userID, contactID);
            } else if(appointmentOverlap) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment would overlap with another appointment.  Please change the dates and time.");
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment is outside of business hours.");
                alert.showAndWait();
                return;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in each field with a proper value");
            alert.showAndWait();
            return;
        }

        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Appointment View");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This is a way to cancel adding an appointment.  Will change the scene to appointment view.
     * @param actionEvent the action event of clicking "Cancel" button
     * @throws IOException
     */
    public void onCancelHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Customer View");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }


    /**
     * This initializes the scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Hour.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Minutes.getItems().addAll(0, 15, 30, 45);
        AmOrPm.getItems().addAll("AM", "PM");
        EndHour.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        EndMinutes.getItems().addAll(0, 15, 30, 45);
        EndAmOrPm.getItems().addAll("AM", "PM");

        ContactBox.setItems(contactsList);

    }
}
