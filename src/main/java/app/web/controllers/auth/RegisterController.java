package app.web.controllers.auth;

import app.dao.AppDAO;
import app.entity.Role;
import app.entity.User;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.web.Main;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterController extends Controller {
    public RegisterController() {
        this.acl("Guest", new String[]{"form", "register"});
    }

    public void form() throws IOException {
        this.loadTemplate("/auth/register");
    }

    public void register(HttpServletRequest request) throws SQLException, ClassNotFoundException, ServletException, IOException, NoSuchFieldException {
        HttpSession session = Main.session;

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Role role = AppDAO.roleDAO.find(2);

        if (User.exists(username)) {
            this.loadTemplate("/auth/register", new Row() {{
                put("errors", new Row() {{
                    put("username", new Collection<String>() {{
                        add("Username already taken.");
                    }});
                }});
            }});

            return;
        } else {
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setRoleId(role.getId());
            user.setPassword(password);
            user.save();

            session.setAttribute("__user", user.getId());
        }

        this.redirect("/home");
    }
}
