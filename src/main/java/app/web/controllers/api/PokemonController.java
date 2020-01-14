package app.web.controllers.api;

import app.entity.Pokemon;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.dao.AppDAO.pokemonDAO;

public class PokemonController extends Controller {
    public void get(HttpServletRequest request, Collection<String> parameters) throws IOException {
        try {
            int page = 1;
            if ((request.getParameter("page") == null)) {
                IModelCollection<Pokemon> pokemons = pokemonDAO.all().sortBy("id");

                this.loadJson(pokemons);
            } else {
                page = Integer.parseInt(request.getParameter("page"));
            }

            if (page <= 0) {
                this.abort(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            IModelCollection<Pokemon> pokemons = pokemonDAO.all().sortBy("id").page(page);

            this.loadJson(pokemons);
        } catch (Exception e) {
            this.abort(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    public void getById(HttpServletRequest request, Collection<String> parameters) throws IOException {
        try {
            long id = 0;
            if ((parameters.size() > 0)) {
                id = Long.parseLong(parameters.get(0));
            }

            if (id <= 0) {
                this.abort(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Pokemon pokemon = pokemonDAO.find(id);

            this.loadJson(pokemon);
        } catch (Exception e) {
            this.abort(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
