package app.web.controllers;

import com.modscleo4.framework.web.controllers.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HomeController extends Controller {
    public void index(HttpServletRequest request) throws ServletException, IOException {
        this.loadView("/pokedex/index.html");
    }
}
