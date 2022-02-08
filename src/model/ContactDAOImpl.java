package model;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAOImpl {
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> cList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(contactID, contactName, email);
                cList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cList;
    }
}
