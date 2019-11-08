package app.web.controllers;

import app.dao.AppDAO;
import app.entity.Pokemon;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.web.controllers.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PokemonController extends Controller {
    public void show(HttpServletRequest request, Collection<String> parameters) throws IOException {
        HttpSession session = request.getSession();

        try {
            long id = Long.parseLong(parameters.get(0));
            Pokemon pokemon = AppDAO.pokemonDAO.find(id);
            session.setAttribute("pokemon", pokemon);
            this.loadView("/pokedex/pokemon/details.jsp");
        } catch (Exception e) {
            this.abort(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
