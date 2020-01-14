package app.web;

import app.dao.AppDAO;
import app.entity.User;
import com.modscleo4.framework.web.Main;

import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.Objects;

public class Auth {
    public static User user() throws ClassNotFoundException, NoSuchFieldException, SQLException {
        if (isLogged()) {
            return AppDAO.userDAO.find(Main.session.getAttribute("__user"));
        }

        return null;
    }

    public static boolean isLogged() {
        return Main.session.getAttribute("__user") != null;
    }

    public static boolean isAdmin() throws SQLException, InvalidKeyException, ClassNotFoundException, NoSuchFieldException {
        return isLogged() && Objects.requireNonNull(user()).isAdmin();
    }
}
