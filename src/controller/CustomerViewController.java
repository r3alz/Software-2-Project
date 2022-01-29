package controller;

import DAO.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AppointmentDAOImpl;
import model.Customer;
import model.CustomerDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerViewController implements Initializable {
    public TableColumn CustomerID;
    public TableColumn Name;
    public TableColumn Address;
    public TableColumn PostalCode;
    public TableColumn PhoneNumber;
    public Button UpdateCustomer;
    public Button DeleteCustomer;
    public TableView<Customer> UpdateCustomerTable;
    public Button AddCustomer;
    public Button ViewAppointments;

    private static Customer selectedCustomer;

    public void updateCustomerHandler(ActionEvent actionEvent) throws IOException {
        selectedCustomer = UpdateCustomerTable.getSelectionModel().getSelectedItem();

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

    public void deleteCustomerHandler(ActionEvent actionEvent) throws SQLException {
        selectedCustomer = UpdateCustomerTable.getSelectionModel().getSelectedItem();
        String sql = "DELETE FROM customers WHERE Customer_ID = " + selectedCustomer.getCustomerID();
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.executeUpdate();
        UpdateCustomerTable.setItems(CustomerDAOImpl.getAllCustomers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));

        UpdateCustomerTable.setItems(CustomerDAOImpl.getAllCustomers());
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

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

    public void onViewAppointmentsHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Appointments");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }
}
