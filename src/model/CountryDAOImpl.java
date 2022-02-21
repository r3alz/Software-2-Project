package model;

import util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created By Chris Ortiz
 * used to get all of the Country data from the countries table in a SQL database
 */
public class CountryDAOImpl {
    /**
     * Gets all of the countries from the countries table and adds them as Country Objects to an observableArrayList
     * @return cList an observableArrayList of Country Ojbects
     */
    public static ObservableList<Country> getAllCountries() {

        ObservableList<Country> cList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryID, countryName);
                cList.add(c);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cList;
    }
}
