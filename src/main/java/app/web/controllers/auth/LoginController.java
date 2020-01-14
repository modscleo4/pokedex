package app.web.controllers.auth;

import app.entity.User;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.entity.IUser;
import com.modscleo4.framework.web.Main;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends Controller {
    public LoginController() {
        this.acl("Guest", new String[]{"form", "login"});
        this.acl("Logged", new String[]{"logout"});
    }

    public void form() throws IOException {
        this.loadTemplate("/auth/login");
    }

    public void login(HttpServletRequest request) throws IOException, ServletException, SQLException, ClassNotFoundException {
        HttpSession session = Main.session;

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        IUser user = User.auth(username, password);
        if (user == null) {
            this.loadTemplate("/auth/login", new Row() {{
                put("errors", new Row() {{
                    put("username", new Collection<String>() {{
                        add("Wrong username/password.");
                    }});
                }});
            }});

            return;
        }

        session.setAttribute("__user", user.getId());

        this.redirect("/home");
    }

    public void logout(HttpServletRequest request) throws IOException {
        HttpSession session = Main.session;
        session.removeAttribute("__user");

        this.redirect("/login");
    }
}
