package model;

import DAO.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {

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
