package app.entity;

import app.dao.AppDAO;
import com.modscleo4.framework.entity.IUser;
import com.modscleo4.framework.security.Password;

import java.security.InvalidKeyException;
import java.sql.SQLException;

public class User extends com.modscleo4.framework.entity.User {
    public User() {
        usernameColumn = "email";
    }

    public Role role() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (Role) this.belongsTo(Role.class);
    }

    public String getName() {
        return (String) this.get("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public long getRoleId() {
        return (long) this.get("role_id");
    }

    public void setRoleId(long id) {
        this.set("role_id", id);
    }

    public boolean isAdmin() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return this.role().getName().equals("admin");
    }

    public static IUser auth(String username, String password) throws SQLException, ClassNotFoundException {
        IUser user = AppDAO.userDAO.where(new User().usernameColumn, "=", username).first();

        if (user != null) {
            if (!Password.matches((user.getPassword()), password)) {
                return null;
            }
        }

        return user;
    }

    public static boolean exists(String username) throws SQLException, ClassNotFoundException {
        User user = AppDAO.userDAO.where(new User().usernameColumn, "=", username).first();

        return user != null;
    }
}
