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
import model.Appointment;
import model.AppointmentDAOImpl;
import model.Contact;
import model.ContactDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the UpdateAppointment view
 */
public class UpdateAppointmentController implements Initializable {
    public TextField Title;
    public TextField Description;
    public TextField Location;
    public TextField AppointmentID;
    public TextField Contact;
    public TextField Type;
    public TextField CustomerID;
    public TextField UserID;
    public Button UpdateAppointment;
    public Button Cancel;
    public DatePicker StartDate;
    public ComboBox Hour;
    public ComboBox Minutes;
    public ComboBox AmOrPm;
    public DatePicker EndDate;
    public ComboBox EndHour;
    public ComboBox EndMinutes;
    public ComboBox EndAmOrPm;
    public ComboBox ContactBox;

    private ObservableList<model.Contact> contactsList = ContactDAOImpl.getAllContacts();

    /**
     * This is used to update an appointment
     * @param actionEvent on click of the update appoitnment button
     * @throws SQLException
     * @throws IOException
     */
    public void onUpdateAppointmentHandler(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int appointmentID = AppointmentViewController.getSelectedAppointment().getAppointmentID();
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
            String lastUpdatedBy = LoginController.getLoggedInUser().getUsername();
            LocalDateTime lastUpdate = LocalDateTime.now();

            ZonedDateTime startDateTimeZoned = startDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime zStartDateTime = startDateTimeZoned.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endDateTimeZoned = endDateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime zEndDateTime = endDateTimeZoned.withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime lastUpdateZoned = lastUpdate.atZone(ZoneId.systemDefault());
            ZonedDateTime zLastUpdate = lastUpdateZoned.withZoneSameInstant(ZoneId.of("UTC"));

            Boolean appointmentOverlapOnUpdate = AppointmentDAOImpl.appointmentOverlapOnUpdate(startDateTime, endDateTime, appointmentID);
            Boolean appointmentDuringHours = AppointmentDAOImpl.appointmentDuringHours(startDateTime, endDateTime);

            if(!appointmentOverlapOnUpdate && appointmentDuringHours) {
                AppointmentDAOImpl.updateAppointment(appointmentID, title, description, location, type, zStartDateTime, zEndDateTime, zLastUpdate, lastUpdatedBy, customerID, userID, contactID);
            } else if(appointmentOverlapOnUpdate) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment would overlap with another appointment.  Please change the dates and time.");
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This appointment is outside of business hours");
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
     * this will initialize the scene
     * .forEach lambda is used in this method since it iterates efficiently
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment appointment = AppointmentViewController.getSelectedAppointment();
        int startHour = appointment.getStartDateTime().getHour();
        int endHour = appointment.getEndDateTime().getHour();
        LocalDate startDate = appointment.getStartDateTime().toLocalDate();
        LocalDate endDate = appointment.getEndDateTime().toLocalDate();
        ContactBox.setItems(contactsList);

        Hour.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        Minutes.getItems().addAll(0, 15, 30, 45);
        AmOrPm.getItems().addAll("AM", "PM");
        EndHour.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        EndMinutes.getItems().addAll(0, 15, 30, 45);
        EndAmOrPm.getItems().addAll("AM", "PM");
        Title.setText(appointment.getTitle());
        Description.setText(appointment.getDescription());
        Location.setText(appointment.getLocation());
        AppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        Type.setText(appointment.getType());
        CustomerID.setText(String.valueOf(appointment.getCustomerID()));
        UserID.setText(String.valueOf(appointment.getUserID()));
        StartDate.setValue(startDate);
        EndDate.setValue(endDate);
        Minutes.setValue(appointment.getStartDateTime().getMinute());
        EndMinutes.setValue(appointment.getEndDateTime().getMinute());

        // .forEach lambda is used here since it iterates efficiently
        contactsList.forEach(c -> {
            if(c.getContactID() == appointment.getContactID()) {
                ContactBox.setValue(c);
            }
        });

        if(startHour > 12) {
            startHour -= 12;
            Hour.setValue(startHour);
            AmOrPm.setValue("PM");
        } else {
            Hour.setValue(startHour);
            AmOrPm.setValue("AM");
        }

        if(endHour > 12) {
            endHour -= 12;
            EndHour.setValue(endHour);
            EndAmOrPm.setValue("PM");
        } else {
            EndHour.setValue(endHour);
            EndAmOrPm.setValue("AM");
        }
    }

    /**
     * this is used to cancel updating an appointment and go back to appointment view
     * @param actionEvent on click of the cancel button
     * @throws IOException
     */
    public void onCancelHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Add Customer");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }
}
