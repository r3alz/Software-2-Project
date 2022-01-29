package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {
    public TextField CustomerName;
    public TextField Address;
    public TextField PostalCode;
    public TextField PhoneNumber;
    public TextField CustomerID;
    public ComboBox<Division> StateCombo;
    public ComboBox<Country> CountryCombo;
    public Button UpdateCustomer;
    public Button Cancel;

    private ObservableList<Division> statesList = DivisionDAOImpl.getAllDivisions();
    private ObservableList<model.Country> countriesList = CountryDAOImpl.getAllCountries();

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

    public void onUpdateCustomerHandler(ActionEvent actionEvent) throws SQLException {
        int customerID = CustomerViewController.getSelectedCustomer().getCustomerID();
        String customerName = CustomerName.getText();
        String address = Address.getText();
        String postalCode = PostalCode.getText();
        String phone = PhoneNumber.getText();
        LocalDateTime lastUpdate = LocalDateTime.now();
        String lastUpdatedBy = LoginController.getLoggedInUser().getUsername();
        int divisionID = StateCombo.getSelectionModel().getSelectedItem().getDivisionID();

        CustomerDAOImpl.updateCustomer(customerID, customerName, address, postalCode, phone, lastUpdate, lastUpdatedBy, divisionID);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Division division = null;
        Country country = null;
        Customer selectedCustomer = CustomerViewController.getSelectedCustomer();

        ObservableList<Division> tempList = FXCollections.observableArrayList();

        for(Division d: statesList) {
            if(selectedCustomer.getDivisionID() == d.getDivisionID()) {
                StateCombo.setValue(d);
                division = d;
            }
        }

        for(Country c: countriesList) {
            if(division.getCountryID() == c.getCountryID()){
                CountryCombo.setValue(c);
                country = c;
            }
        }

        for(Division d: statesList) {
            if (d.getCountryID() == country.getCountryID()) {
                tempList.add(d);
            }
        }

        StateCombo.setItems(tempList);
        CountryCombo.setItems(countriesList);

        CustomerName.setText(selectedCustomer.getCustomerName());
        Address.setText(selectedCustomer.getAddress());
        PostalCode.setText(selectedCustomer.getPostalCode());
        PhoneNumber.setText(selectedCustomer.getPhone());
        CustomerID.setText(String.valueOf(selectedCustomer.getCustomerID()));

    }

    public void onCancelHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Customer View");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }
}
