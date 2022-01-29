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
    public Button Cancel;

    private ObservableList<Division> statesList = DivisionDAOImpl.getAllDivisions();
    private ObservableList<model.Country> countriesList = CountryDAOImpl.getAllCountries();

    public void onAddCustomerHandler(ActionEvent actionEvent) throws SQLException {
        Division d = (Division) StateCombo.getSelectionModel().getSelectedItem();
        String customerName = CustomerName.getText();
        String address = Address.getText();
        String postalCode = PostalCode.getText();
        String phoneNumber = PhoneNumber.getText();
        String createdBy = LoginController.getLoggedInUser().getUsername();
        LocalDateTime createDate = LocalDateTime.now();
        int divisionID = d.getDivisionID();

        CustomerDAOImpl.addCustomer(customerName, divisionID, address, postalCode, phoneNumber, createDate, createdBy, createDate, createdBy);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
