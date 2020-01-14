package com.modscleo4.framework.entity;

public interface IUser extends IModel {
    /**
     * Gets the User id.
     *
     * @return the User id
     */
    long getId();

    /**
     * Sets the User id.
     *
     * @param id the User id
     */
    void setId(long id);

    /**
     * Gets the User username.
     *
     * @return the User username
     */
    String getUsername();

    /**
     * Sets the User username.
     *
     * @param username the User username
     */
    void setUsername(String username);

    /**
     * Gets the User password (hash).
     *
     * @return the User password
     */
    String getPassword();

    /**
     * Sets the User password (hash).
     *
     * @param password the User password
     */
    void setPassword(String password);


}
