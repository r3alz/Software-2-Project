package controller;

import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentDAOImpl;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the AppointmentView view
 */
public class AppointmentViewController implements Initializable {
    public TableColumn AppointmentID;
    public TableColumn Title;
    public TableColumn Description;
    public TableColumn Location;
    public TableColumn Type;
    public TableColumn StartDateTime;
    public TableColumn EndDateTime;
    public TableColumn CustomerID;
    public TableColumn UserID;
    public TableColumn Contact;
    public TableView<Appointment> AppointmentTable;
    public Button UpdateAppointment;
    public Button DeleteAppointment;
    public Button AddAppointment;
    public Button CustomerView;

    private static Appointment selectedAppointment;
    public RadioButton ViewWeek;
    public ToggleGroup tgroup;
    public RadioButton ViewMonth;
    public RadioButton ViewAll;
    public Button ReportsButton;
    public Button Reports2Button;

    /**
     * This will initialize the scene
     * @param url
     * @param resourceBundle
     */
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
        Contact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        AppointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
    }

    /**
     * This is used to update an appointment
     * @param actionEvent on click of the Update Appointment button
     * @throws IOException
     */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment");
            alert.showAndWait();
            return;
        }

        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Add Appointment");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This will let you delete the selected appointment
     * @param actionEvent on click of the Delete button
     * @throws SQLException
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a appointment");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this appointment with Appointment ID: " + selectedAppointment.getAppointmentID() + " and Type: " + selectedAppointment.getType());
        Optional<ButtonType> selection = alert.showAndWait();
        if(selection.get() == ButtonType.OK) {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + selectedAppointment.getAppointmentID();
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            AppointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());

            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "You deleted the appointment with Appointment ID: " + selectedAppointment.getAppointmentID() + " and the Type: " + selectedAppointment.getType());
            alertInfo.showAndWait();
            return;
        }
    }

    /**
     * This is used to add an appointment
     * @param actionEvent on click of the Add Appoitment button
     * @throws IOException
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Add Appointment");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This will send you to customer view
     * @param actionEvent on click of customer view button
     * @throws IOException
     */
    public void onCustomerViewHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Customers");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This returns the selected appointment
     * @return selectedAppointment the selected appointment
     */
    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * This will only show appointments this week
     * .forEach lambda is used here since it iterates efficiently
     * @param actionEvent on click of the view by week radio button
     */
    public void onViewWeekHandler(ActionEvent actionEvent) {
        ObservableList<Appointment> appointments = AppointmentDAOImpl.getAllAppointments();
        ObservableList<Appointment> tempList = FXCollections.observableArrayList();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        TemporalField weekOfMonth = weekFields.weekOfMonth();
        LocalDate date = LocalDate.now();
        LocalDate end = date.plusMonths(1);
        List<LocalDate> dates = new ArrayList<>();
        int wom = date.get(weekOfMonth);
        Month month = date.getMonth();
        while (date.isBefore(end)) {
            /**
             * .forEach lambda is used here since it iterates efficiently
             */
            appointments.forEach(a -> {
                LocalDateTime testDateTime = a.getStartDateTime();
                LocalDate testDate = testDateTime.toLocalDate();
                Month dateTimeMonth = testDate.getMonth();
                if(wom == testDate.get(weekOfMonth) && month == dateTimeMonth) {
                    if(!tempList.contains(a)) {
                        tempList.add(a);
                    }
                }
            });
            date = date.plusDays(1);
        }

        AppointmentTable.setItems(tempList);
    }

    /**
     * This will show all appointments in the month
     * .forEach lambda is used here since it iterates efficiently
     * @param actionEvent on click of the view by month radio button
     */
    public void onViewMonthHandler(ActionEvent actionEvent) {
        ObservableList<Appointment> appointments = AppointmentDAOImpl.getAllAppointments();
        ObservableList<Appointment> tempList = FXCollections.observableArrayList();
        LocalDate day = LocalDate.now();
        LocalDate date = LocalDate.now().withDayOfMonth(1);
        LocalDate end = date.plusMonths(1);
        List<LocalDate> dates = new ArrayList<>();
        while (date.isBefore(end)) {
            Month month = date.getMonth();
            /**
             * .forEach lambda is used here since it iterates efficiently
             */
            appointments.forEach(a -> {
                LocalDateTime testDateTime = a.getStartDateTime();
                Month dateTimeMonth = testDateTime.getMonth();
                if (month == dateTimeMonth) {
                    if (!tempList.contains(a)) {
                        tempList.add(a);
                    }
                }
            });

            date = date.plusDays(1);
        }

        AppointmentTable.setItems(tempList);
    }

    /**
     * this will show all appointments
     * @param actionEvent on click of the view all radio button
     */
    public void onViewAllHandler(ActionEvent actionEvent) {
        AppointmentTable.setItems(AppointmentDAOImpl.getAllAppointments());
    }

    /**
     * This will show you the customer reports scene
     * @param actionEvent on click of the customer reports button
     * @throws IOException
     */
    public void onReportsHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerReport.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("Report");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This will send you to the schedule report scene
     * @param actionEvent on click of the schedule report button
     * @throws IOException
     */
    public void onReports2Handler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/ScheduleReport.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("Report");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * this will send you to the state report scene
     * @param actionEvent on click of the state report button
     * @throws IOException
     */
    public void onReports3Handler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/StateReport.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 700, 600);
        stage.setTitle("Report");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }
}
