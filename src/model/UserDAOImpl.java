package model;

import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created By Chris Ortiz
 * UserDAOImpl class is used for storing the function to get all of the execute SQL query to store the user information
 */

public class UserDAOImpl {

    /**
     * Executes a SQL Query, stores them into variable values, then adds them to an list of Users
     * @return an observableArrayList of Users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> uList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(userId, userName, password);
                uList.add(u);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return uList;
    }
}
