package model;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerDAOImpl {

    private static int currentCID = getAllCustomers().size();

    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> cList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from customers";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
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

                Customer c = new Customer(customerID, customerName, divisionID, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy);
                cList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cList;
    }

    public static void addCustomer(int customerID, String customerName, int divisionID, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) throws SQLException {
        String sql = "INSERT INTO `customers` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setString(2, customerName);
        ps.setInt(10, divisionID);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setObject(6, createDate);
        ps.setString(7, createdBy);
        ps.setObject(8, lastUpdate);
        ps.setString(9, lastUpdatedBy);
        ps.executeUpdate();
    }

    public static void deleteCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.executeUpdate();
    }

    public static void updateCustomer(String customerName, String address, String postalCode, String phone, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        int cID = 10;
        String sql = "Update customers SET Customer_Name = ?,Address = ?,Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = " + cID;
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setDate(5, createDate);
        ps.setString(6, createdBy);
        ps.setDate(7, lastUpdate);
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);
        ps.executeUpdate();
    }

    public static void updateCID() {
        currentCID++;
    }

    public static int getCID() {
        return currentCID;
    }
}
