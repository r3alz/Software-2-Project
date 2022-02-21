package model;

/**
 * Created By Chris Ortiz
 * This class is used to store Contact information
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Constructor to initialize Contact Object
     * @param contactID initial contactID
     * @param contactName initial contactName
     * @param email initial email
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Get contactID of Contact
     * @return contactID of Contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Set contactID for Contact
     * @param contactID new contactID for Contact
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Get contactName of Contact
     * @return contactName of Contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Set contactName for Contact
     * @param contactName new contactName for Contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Get email of Contact
     * @return email of Contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email for Contact
     * @param email new email for Contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Override toString for Contact
     * @return contactName (String)
     */
    @Override
    public String toString() {
        return contactName;
    }
}
