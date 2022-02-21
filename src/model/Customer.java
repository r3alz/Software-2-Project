package model;

import java.util.Date;

/**
 * Customer class used to store information for Customer
 */

public class Customer {
    private int customerID;
    private int divisionID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor to initialize Customer Object
     * @param customerID intial customerID
     * @param customerName intial customerName
     * @param divisionID initial divisionID
     * @param address initial address
     * @param postalCode initial postalCode
     * @param phone initial phone
     * @param createDate initial createDate
     * @param createdBy inital createdBy
     * @param lastUpdate initial lastUpdate
     * @param lastUpdatedBy initial lastUpdatedBy
     */
    public Customer(int customerID, String customerName, int divisionID, String address, String postalCode, String phone, Date createDate, String createdBy, Date lastUpdate, String lastUpdatedBy) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.divisionID = divisionID;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Constructor to initialize Customer object
     * @param customerID initial customerID
     * @param customerName initial customerName
     * @param address initial address
     * @param postalCode initial postalCode
     * @param phone initial phone
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    /**
     * Get the customerID for Customer
     * @return customerID of Customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Set the customerID of a Customer
     * @param customerID new customerID for Customer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get the customerName of a Customer
     * @return customerName of Customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Set the customerName of a Customer
     * @param customerName new customerName of Customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Get the divisionID of Customer
     * @return divisionID of Customer
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Set the divisionID of Customer
     * @param divisionID new divisionID for customer
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Get the address of Customer
     * @return address for Customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address for Customer
     * @param address new address for Customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the postalCode of Customer
     * @return postalCode of Customer
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set the postalCode of Customer
     * @param postalCode new postalCode for Customer
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Get the phone of Customer
     * @return phone of Customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the phone for Customer
     * @param phone new phone for Customer
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the createDate for Customer
     * @return createDate for Customer
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set the createDate for Customer
     * @param createDate new createDate for Customer
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Get createdBy of Customer
     * @return createdBy of Customer
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set createdBy for Customer
     * @param createdBy new createdBy for Customer
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get lastUpdate of Customer
     * @return lastUpdate of Customer
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set lastUpdate for Customer
     * @param lastUpdate new lastUpdate for Customer
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Get lastUpdatedBy of Customer
     * @return lastUpdatedBy of Customer
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Set lastUpdatedBy for Customer
     * @param lastUpdatedBy new lastUpdatedBy for Customer
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
