package model;

/**
 * Create by Chris Ortiz
 * User class used for storing User information
 */

public class User {
    private Integer id;
    private String username;
    private String password;

    /**
     * This is a constructor to initialize a User object
     * @param id an initial id for User
     * @param username an initial username for User
     * @param password an initial password for User
     */
    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Get Users name
     * @return User username
     */
    public String getUsername() {
        return username;
    }

    /**
     * To set the name of a User
     * @param username a new name for User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get User password
     * @return User password
     */
    public String getPassword() {
        return password;
    }

    /**
     * To set the password of a User
     * @param password a new password for User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the ID of a User
     * @return User ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * To set the ID of a User
     * @param id a new ID for User
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
