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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created By Chris Ortiz
 * Used to control the UpdateCustomer view
 */
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

    /**
     *
     * @param actionEvent
     */
    public void onCountryHandler(ActionEvent actionEvent) {
        Country selc = (Country) CountryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Division> tempList = FXCollections.observableArrayList();
        statesList.forEach(d -> {
           if (selc.getCountryID() == d.getCountryID()) {
               tempList.add(d);
           }
        });
        StateCombo.setItems(tempList);
    }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onUpdateCustomerHandler(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int customerID = CustomerViewController.getSelectedCustomer().getCustomerID();
            String customerName = CustomerName.getText();
            String address = Address.getText();
            String postalCode = PostalCode.getText();
            String phone = PhoneNumber.getText();
            LocalDateTime lastUpdate = LocalDateTime.now();
            String lastUpdatedBy = LoginController.getLoggedInUser().getUsername();
            int divisionID = StateCombo.getSelectionModel().getSelectedItem().getDivisionID();

            CustomerDAOImpl.updateCustomer(customerID, customerName, address, postalCode, phone, lastUpdate, lastUpdatedBy, divisionID);
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
     *
     * @param url
     * @param resourceBundle
     */
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

    /**
     *
     * @param actionEvent
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
