package app.web.controllers.admin;

import com.modscleo4.framework.web.controller.Controller;

import java.io.IOException;

public class HomeController extends Controller {
    public HomeController() {
        this.acl("Admin", new String[]{"index"});
    }

    public void index() throws IOException {
        this.redirect("/home");
    }
}
