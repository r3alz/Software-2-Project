package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField CustomerName;
    public TextField Address;
    public TextField PostalCode;
    public TextField PhoneNumber;
    public TextField CustomerID;
    public ComboBox StateCombo;
    public ComboBox CountryCombo;
    public Button AddCustomer;

    private ObservableList<Division> statesList = DivisionDAOImpl.getAllDivisions();
    private ObservableList<model.Country> countriesList = CountryDAOImpl.getAllCountries();

    public void onAddCustomerHandler(ActionEvent actionEvent) throws SQLException {
        CustomerDAOImpl.updateCID();
        Division d = (Division) StateCombo.getSelectionModel().getSelectedItem();
        String customerName = CustomerName.getText();
        String address = Address.getText();
        String postalCode = PostalCode.getText();
        String phoneNumber = PhoneNumber.getText();
        String createdBy = LoginController.getLoggedInUser().getUsername();
        LocalDateTime createDate = LocalDateTime.now();
        int divisionID = d.getDivisionID();
        int customerID = CustomerDAOImpl.getCID();

        Customer c = new Customer(customerID, customerName, address, postalCode, phoneNumber);
        CustomerDAOImpl.addCustomer(customerID, customerName, divisionID, address, postalCode, phoneNumber, createDate, createdBy, createDate, createdBy);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String cID = String.valueOf(CustomerDAOImpl.getCID() + 1);

        CustomerID.setText(cID);

        StateCombo.setItems(statesList);
        CountryCombo.setItems(countriesList);
    }

    public void onCountryHandler(ActionEvent actionEvent) {
        Country selc = (Country) CountryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Division> tempList = FXCollections.observableArrayList();
        for(Division d: statesList) {
            if (selc.getCountryID() == d.getCountryID()) {
                tempList.add(d);
            }
        }
        StateCombo.setItems(tempList);
    }
}
