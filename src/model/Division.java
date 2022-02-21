package model;

/**
 * Created by Chris Ortiz
 * Division class used for storing Division information
 */

public class Division {
    private int divisionID;
    private int countryID;
    private String division;

    /**
     * This is a constructor to initialize a Division object
     * @param divisionID an initial divisionID for Division
     * @param countryID an initial countryID for country
     * @param division an initial division for Division
     */

    public Division(int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    /**
     * Get the countryID of a Division
     * @return countryID of Division
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Set the countryID for a Division
     * @param countryID new countryID for Division
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Get the divisionID for Division
     * @return the divisionID of Division
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Set the divisionID of a Division
     * @param divisionID new divisionID for Division
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Get the division of a Division
     * @return division of Division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Set the division of a Division
     * @param division new division for Division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Overriding toString for the Division Class
     * @return division (String)
     */
    @Override
    public String toString() {
        return (division);
    }
}
