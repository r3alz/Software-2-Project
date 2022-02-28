package controller;

import javafx.scene.control.*;
import util.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.CustomerDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the CustomerView view
 */
public class CustomerViewController implements Initializable {
    public TableColumn CustomerID;
    public TableColumn Name;
    public TableColumn Address;
    public TableColumn PostalCode;
    public TableColumn PhoneNumber;
    public TableColumn Division;
    public TableColumn Country;
    public Button UpdateCustomer;
    public Button DeleteCustomer;
    public TableView<Customer> UpdateCustomerTable;
    public Button AddCustomer;
    public Button ViewAppointments;

    private static Customer selectedCustomer;

    /**
     * This is used to update a customer
     * @param actionEvent on click of the update customer button
     * @throws IOException
     */
    public void updateCustomerHandler(ActionEvent actionEvent) throws IOException {
        selectedCustomer = UpdateCustomerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer");
            alert.showAndWait();
            return;
        }

        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Add Customer");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This is used to delete a selected customer
     * @param actionEvent on click of the delete customer button
     * @throws SQLException
     */
    public void deleteCustomerHandler(ActionEvent actionEvent) throws SQLException {
        selectedCustomer = UpdateCustomerTable.getSelectionModel().getSelectedItem();

        if(selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer");
            alert.showAndWait();
            return;
        }

        if(CustomerDAOImpl.doesAppointmentExist(selectedCustomer.getCustomerID())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "A customer with an appointment scheduled cannot be deleted");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this customer?");
        Optional<ButtonType> selection = alert.showAndWait();
        if(selection.get() == ButtonType.OK) {
            String sql = "DELETE FROM customers WHERE Customer_ID = " + selectedCustomer.getCustomerID();
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
            UpdateCustomerTable.setItems(CustomerDAOImpl.getAllCustomers());
        }
    }

    /**
     * this will initialize the scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Country.setCellValueFactory(new PropertyValueFactory<>("country"));
        Division.setCellValueFactory(new PropertyValueFactory<>("firstLevelDivision"));


        UpdateCustomerTable.setItems(CustomerDAOImpl.getAllCustomers());
    }

    /**
     * This will return the selected customer
     * @return selectedCustomer selected on the table
     */
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * This is used to add a customer
     * @param actionEvent on click of the add customer button
     * @throws IOException
     */
    public void onAddCustomerHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Add Customer");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * this is used to see appointments
     * @param actionEvent on click of the appointment view button
     * @throws IOException
     */
    public void onViewAppointmentsHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Appointments");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * this is used to view the customer report
     * @param actionEvent on click of the customer report button
     * @throws IOException
     */
    public void onReportsHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerReport.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Report");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This is used to view the schedule report
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
     * This is used to view state report
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
