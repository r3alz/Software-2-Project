package model;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Created By Chris Ortiz
 * This class is used to store Appointment information
 */
public class Appointment {
    private int appointmentID, customerID, userID, contactID;
    private String contact, title, description, location, type, createDate, createdBy, lastUpdateTime, lastUpdateBy;
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
    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, String createDate, String createdBy, String lastUpdateTime, String lastUpdateBy, int contactID, String contact) {
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
        this.contact = contact;
    }

    /**
     * gets the appointment ID
     * @return appointmentID of the appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * sets the appointmentID to new appointmentID
     * @param appointmentID new appointmentID for appointment
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * gets the customerID of appointment
     * @return customerID of appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * sets the customerID of the appointment
     * @param customerID new customerID for appointment
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * gets the userID of appointment
     * @return userID of appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * sets the userID of an appointment
     * @param userID new userID of appointment
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * gets the title of the appointment
     * @return title of appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title of the appointment
     * @param title new title for appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the description of an appointment
     * @return description of an appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description for an appointment
     * @param description new description for an appointment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the location of an appointment
     * @return location of appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location of an appointment
     * @param location new location for appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * gets the type of an appointment
     * @return type of an appointment
     */
    public String getType() {
        return type;
    }

    /**
     * sets the type of an appointment
     * @param type new type for an appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * gets the startDateTime of an appointment
     * @return startDateTime of appointment
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * sets the startDateTime of an appointment
     * @param startDateTime new startDateTime of appointment
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * gets the endDateTime of an appointment
     * @return endDateTime of appointment
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * sets the endDateTime of an appointment
     * @param endDateTime new endDateTime for appointment
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * gets the createDate of an appointment
     * @return createDate of appointment
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * sets the createDate of an appointment
     * @param createDate of appointment
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * gets the createdBy of an appointemnt
     * @return createdBy of appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * sets the createdBy of an appointment
     * @param createdBy new createdBy for appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * gets the lastUpdateTime of an appointment
     * @return lastUpdateTime of appointment
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * sets the lastUpdateTime of an appointment
     * @param lastUpdateTime new lastUpdateTime of an appointment
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * gets the lastUpdateBy of an appointment
     * @return lastUpdateBy of appointment
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * sets the lastUpdateBy of an appointment
     * @param lastUpdateBy new lastUpdateBy for appointment
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * gets the contactID of an appointment
     * @return contactID of appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * sets the contactID of an appointment
     * @param contactID  new contactID for an appointment
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * gets the contact for an appointment
     * @return contact for appointemnt
     */
    public String getContact() {return contact;}

    /**
     * sets the contact for an appointment
     * @param contact new contact for appointment
     */
    public void setContact(String contact) {this.contact = contact;}
}
