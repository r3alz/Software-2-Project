package model;

public class Division {
    private int divisionID;
    private int countryID;
    private String division;

    public Division(int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return (division);
    }
}
