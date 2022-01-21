package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Customer;
import model.CustomerDAOImpl;

import java.sql.Date;
import java.sql.SQLException;

public class AddCustomerController {
    public TextField CustomerName;
    public TextField Address;
    public TextField PostalCode;
    public TextField PhoneNumber;
    public TextField CustomerID;
    public ComboBox State;
    public ComboBox Country;
    public Button AddCustomer;

    public void onAddCustomerHandler(ActionEvent actionEvent) throws SQLException {
        String customerName = CustomerName.getText();
        String address = Address.getText();
        String postalCode = PostalCode.getText();
        String phoneNumber = PhoneNumber.getText();
        int customerID = Integer.parseInt(CustomerID.getText());

        Customer c = new Customer(customerID, customerName, address, postalCode, phoneNumber);
        CustomerDAOImpl.addCustomer(customerID, customerName, 1, address, postalCode, phoneNumber, Date.valueOf("2019-04-04"), "test", Date.valueOf("2019-03-03"), "test");
    }
}
