package com.modscleo4.framework.entity;

import com.modscleo4.framework.dao.EntityDAO;
import com.modscleo4.framework.security.Password;

import java.sql.SQLException;

public class User extends Model implements IUser {
    protected String usernameColumn = "email";

    public User() {
        table = "users";
    }

    @Override
    public long getId() {
        return (long) this.get("id");
    }

    @Override
    public void setId(long id) {
        this.set("id", id);
    }

    @Override
    public String getUsername() {
        return (String) this.get(this.usernameColumn);
    }

    @Override
    public void setUsername(String username) {
        this.set(this.usernameColumn, username);
    }

    @Override
    public String getPassword() {
        return (String) this.get("password");
    }

    @Override
    public void setPassword(String password) {
        this.set("password", Password.generateHash(password));
    }

    public static IUser auth(String username, String password) throws SQLException, ClassNotFoundException {
        EntityDAO<User> userDAO = new EntityDAO<>(User.class);
        IUser user = userDAO.where(new User().usernameColumn, "=", username).first();

        if (user != null) {
            if (!Password.matches((user.getPassword()), password)) {
                return null;
            }
        }

        return user;
    }

    public static boolean exists(String username) throws SQLException, ClassNotFoundException {
        EntityDAO<User> userDAO = new EntityDAO<>(User.class);
        User user = userDAO.where(new User().usernameColumn, "=", username).first();

        return user != null;
    }
}
