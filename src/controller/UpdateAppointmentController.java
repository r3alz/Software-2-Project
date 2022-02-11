package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {
    public TextField Title;
    public TextField Description;
    public TextField Location;
    public TextField StartDateTime;
    public TextField AppointmentID;
    public TextField Contact;
    public TextField Type;
    public TextField EndDateTime;
    public TextField CustomerID;
    public TextField UserID;
    public Button UpdateAppointment;
    public Button Cancel;

    public void onUpdateAppointmentHandler(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment appointment = AppointmentViewController.getSelectedAppointment();
        Title.setText(appointment.getTitle());
        Description.setText(appointment.getDescription());
        Location.setText(appointment.getLocation());
        StartDateTime.setText(String.valueOf(appointment.getStartDateTime()));
        AppointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        Contact.setText(String.valueOf(appointment.getContactID()));
        Type.setText(appointment.getType());
        EndDateTime.setText(String.valueOf(appointment.getEndDateTime()));
        CustomerID.setText(String.valueOf(appointment.getCustomerID()));
        UserID.setText(String.valueOf(appointment.getUserID()));
    }

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
