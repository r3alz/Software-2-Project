package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the AddCustomer view
 */
public class AddCustomerController implements Initializable {
    public TextField CustomerName;
    public TextField Address;
    public TextField PostalCode;
    public TextField PhoneNumber;
    public TextField CustomerID;
    public ComboBox StateCombo;
    public ComboBox CountryCombo;
    public Button AddCustomer;
    public Button Cancel;

    private ObservableList<Division> statesList = DivisionDAOImpl.getAllDivisions();
    private ObservableList<model.Country> countriesList = CountryDAOImpl.getAllCountries();

    /**
     * This is used to add a Customer
     * @param actionEvent on click of the "Add Customer" button
     * @throws SQLException
     * @throws IOException
     */
    public void onAddCustomerHandler(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            Division d = (Division) StateCombo.getSelectionModel().getSelectedItem();
            String customerName = CustomerName.getText();
            String address = Address.getText();
            String postalCode = PostalCode.getText();
            String phoneNumber = PhoneNumber.getText();
            String createdBy = LoginController.getLoggedInUser().getUsername();
            LocalDateTime createDate = LocalDateTime.now();
            int divisionID = d.getDivisionID();

            ZonedDateTime createDateZoned = createDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zCreateDate = createDateZoned.withZoneSameInstant(ZoneId.of("UTC"));

            if(customerName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a name in the Customer Name field");
                alert.showAndWait();
                return;
            }

            else if(address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter an address in the Address field");
                alert.showAndWait();
                return;
            }

            else if(postalCode.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a postal code in the Postal Code field");
                alert.showAndWait();
                return;
            }

            else if(phoneNumber.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a phone number in the Phone Number field");
                alert.showAndWait();
                return;
            }

            else if(address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter an address in the Address field");
                alert.showAndWait();
                return;
            }

            CustomerDAOImpl.addCustomer(customerName, divisionID, address, postalCode, phoneNumber, zCreateDate, createdBy, zCreateDate, createdBy);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in each field with a proper value");
            alert.showAndWait();
            return;
        }

        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Customer View");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }

    /**
     * This initialized the Add Customer scene
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StateCombo.setItems(statesList);
        CountryCombo.setItems(countriesList);
    }

    /**
     * This will be used when a country is selected.  It will set the states combobox value to be only states that have the same countryID as selected country
     * .forEach lambda is used in this method since it iterates efficiently
     * @param actionEvent on select of a country from the Country combobox
     */
    public void onCountryHandler(ActionEvent actionEvent) {
        Country selc = (Country) CountryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Division> tempList = FXCollections.observableArrayList();
        /**
         * .forEach lambda is used here since it iterates efficiently
         */
        statesList.forEach(d -> {
            if (selc.getCountryID() == d.getCountryID()) {
                tempList.add(d);
            }
        });

        StateCombo.setItems(tempList);
    }

    /**
     * This will cancel adding a customer and will change the scene to customer view
     * @param actionEvent on click of the Cancel button
     * @throws IOException
     */
    public void onCancelHandler(ActionEvent actionEvent) throws IOException {
        //load widget hierarchy of next screen
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));

        //get the stage from an event's source widget
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        //Create the New Scene
        Scene scene = new Scene(root, 850, 550);
        stage.setTitle("Customer View");

        //Set the scene on the stage
        stage.setScene(scene);

        //raise the curtain
        stage.show();
    }
}
