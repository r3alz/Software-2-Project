package controller;

import javafx.fxml.Initializable;

import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.AppointmentDAOImpl;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialController implements Initializable {
    public TableColumn AppointmentID;
    public TableColumn Title;
    public TableColumn Description;
    public TableColumn Location;
    public TableColumn Type;
    public TableColumn StartDateTime;
    public TableColumn EndDateTime;
    public TableColumn CustomerID;
    public TableColumn UserID;
    public TableView AppointmentTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        StartDateTime.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        EndDateTime.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        UserID.setCellValueFactory(new PropertyValueFactory<>("userID"));

        AppointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
    }
}
