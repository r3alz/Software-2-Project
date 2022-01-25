package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AppointmentDAOImpl;
import model.CustomerDAOImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    public TableColumn CustomerID;
    public TableColumn Name;
    public TableColumn Address;
    public TableColumn PostalCode;
    public TableColumn PhoneNumber;
    public Button UpdateCustomer;
    public Button DeleteCustomer;
    public TableView UpdateCustomerTable;

    public void updateCustomerHandler(ActionEvent actionEvent) {
    }

    public void deleteCustomerHandler(ActionEvent actionEvent) {

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
}
