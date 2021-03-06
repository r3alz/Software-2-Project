package model;

import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Chris Ortiz
 * DivisionDAOImpl class used for getting all the data from the division table in SQL and adding to observableArrayList
 */

public class DivisionDAOImpl {
    public static ObservableList<Division> getAllDivisions() {

        ObservableList<Division> dList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                int countryID = rs.getInt("Country_ID");
                String division = rs.getString("Division");

                Division d = new Division(divisionID, countryID, division);
                dList.add(d);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dList;
    }
}
