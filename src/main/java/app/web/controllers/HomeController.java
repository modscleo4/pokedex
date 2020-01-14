package app.web.controllers;

import app.entity.Pokemon;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

import static app.dao.AppDAO.pokemonDAO;

public class HomeController extends Controller {
    public HomeController() {
        this.acl("Logged", new String[]{"home"});
    }

    public void index(HttpServletRequest request) throws IOException {
        this.loadTemplate("/pokedex/index");
    }

    public void home(HttpServletRequest request) throws IOException {
        this.loadTemplate("/home");
    }

    public void search(HttpServletRequest request) throws SQLException, ClassNotFoundException, IOException {
        String searchFor = request.getParameter("q");
        IModelCollection<Pokemon> pokemons = pokemonDAO.all().sortBy("id");

        try {
            long id = Long.parseLong(searchFor);
            Pokemon p = pokemons.find(id);

            if (p != null) {
                this.redirect(String.format("/pokemon/%d", p.getId()));
            }
        } catch (NumberFormatException e) {
            IModelCollection<Pokemon> filtered =pokemons.where(p -> p.getName().equalsIgnoreCase(searchFor));

            if (filtered.size() == 1) {
                this.redirect(String.format("/pokemon/%d", filtered.first().getId()));
            } else {
                this.loadTemplate("/pokemon/search", new Row() {{
                    put("pokemons", filtered);
                }});
            }
        }
    }
}
