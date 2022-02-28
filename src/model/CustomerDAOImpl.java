package model;

import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by Chris Ortiz
 * used to get all of the customer data from the customers table in a SQL database
 */

public class CustomerDAOImpl {

    /**
     * Gets all of the customer data from the customers table in SQL database.  Adds them to a observableArrayList
     * @return cList returns the list of Customer objects that are added to an observableArrayList
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> cList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String country = "";
                String division = "";

                ObservableList<Division> divisionList = DivisionDAOImpl.getAllDivisions();
                ObservableList<Country> countryList = CountryDAOImpl.getAllCountries();

                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int divisionID = rs.getInt("Division_ID");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                Date createDate = rs.getDate("Create_Date");
                String createdBy = rs.getString("Created_By");
                Date lastUpdate = rs.getDate("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");

                for(Division d: divisionList) {
                    if(divisionID == d.getDivisionID()) {
                        division = d.getDivision();
                        for(Country c: countryList) {
                            if(d.getCountryID() == c.getCountryID()) {
                                country = c.getCountry();
                            }
                        }
                    }
                }

                Customer c = new Customer(customerID, customerName, divisionID, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, division, country);
                cList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cList;
    }

    /**
     * Adds (Inserts) a customer into the SQL database
     * @param customerName to add to the Customer_Name column
     * @param divisionID to add to the Division_ID column
     * @param address to add to the Address column
     * @param postalCode to add to the Postal_Code column
     * @param phone to add to the Phone column
     * @param createDate to add to the Create_Date column
     * @param createdBy to add to the Created_By column
     * @param lastUpdate to add to the Last_Update column
     * @param lastUpdatedBy to add to the Last_Updated_By column
     * @throws SQLException
     */
    public static void addCustomer(String customerName, int divisionID, String address, String postalCode, String phone, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdatedBy) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setObject(5, createDate);
        ps.setString(6, createdBy);
        ps.setObject(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.executeUpdate();
    }

    /**
     * Deletes a customer from the SQL database
     * @param customerID row with this customerID will be deleted from the customer table
     * @throws SQLException
     */
    public static void deleteCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.executeUpdate();
    }

    /**
     * Updates row in the customers table
     * @param customerID Updates the row in the customers table that has this customerID
     * @param customerName customerName to update Customer_Name column
     * @param address address to update Address column
     * @param postalCode postalCode to update Postal_Code column
     * @param phone phone to update Phone column
     * @param lastUpdate lastUpdate to update Last_Update
     * @param lastUpdatedBy lastUpdatedBy to updated Last_Updated_By column
     * @param divisionID divisionID to update Division_ID column
     * @throws SQLException
     */
    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phone, ZonedDateTime lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "Update customers SET Customer_Name = ?,Address = ?,Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = " + customerID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setObject(5, lastUpdate);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionID);
        ps.executeUpdate();
    }

    /**
     * Runs a SQL query to see if an appointment exists for the Customer
     * @param customerID uses this customerID to find Customer
     * @return true if appointment exists false if does not exist
     */
    public static Boolean doesAppointmentExist(int customerID) {
        try{
            String sql = "Select * from appointments";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int cID = rs.getInt("Customer_ID");
                if (customerID == cID) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
