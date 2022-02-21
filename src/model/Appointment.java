package model;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Created By Chris Ortiz
 * This class is used to store Appointment information
 */
public class Appointment {
    private int appointmentID, customerID, userID, contactID;
    private String title, description, location, type, createDate, createdBy, lastUpdateTime, lastUpdateBy;
    private LocalDateTime startDateTime, endDateTime;

    /**
     * Constructor to initialize Appointment Object
     * @param appointmentID intial appointmentID
     * @param customerID intial customerID
     * @param userID inital userID
     * @param title intial title
     * @param description intial description
     * @param location intial location
     * @param type intial type
     * @param startDateTime intial startDateTime
     * @param endDateTime intial endDateTime
     * @param createDate intial createDate
     * @param createdBy intial createdBy
     * @param lastUpdateTime intial lastUpdateTime
     * @param lastUpdateBy intial lastUpdateBy
     * @param contactID initial contactID
     */
    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String createDate, String createdBy, String lastUpdateTime, String lastUpdateBy, int contactID) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdateTime = lastUpdateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.contactID = contactID;
    }

    /**
     *
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     *
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return usertID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return startDateTime
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     *
     * @param startDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     *
     * @return endDateTime
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     *
     * @param endDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     *
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     *
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     *
     * @return lastUpdatedBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     *
     * @param lastUpdateBy
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     *
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
