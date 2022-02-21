package model;

/**
 * Created By Chris Ortiz
 * This class is used to store Country information
 */
public class Country {
    private int countryID;
    private String country;

    /**
     * Constructor to initialize Country object
     * @param countryID intial countryID
     * @param country intial country
     */
    public Country(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * Get the countryID of Country
     * @return countryID of country
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Set the countryID for Country
     * @param countryID new countryID for Country
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Get country of Country
     * @return country of Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set the country for Country
     * @param country new country for Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Override the toString for Country
     * @return country (String)
     */
    @Override
    public String toString() {
        return(country);
    }
}
