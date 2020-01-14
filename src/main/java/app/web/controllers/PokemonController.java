package app.web.controllers;

import app.dao.AppDAO;
import app.entity.Pokemon;
import app.entity.Weakness;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.ModelCollection;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PokemonController extends Controller {
    public void show(HttpServletRequest request, Collection<String> parameters) throws IOException {
        try {
            long id = Long.parseLong(parameters.get(0));
            Pokemon pokemon = AppDAO.pokemonDAO.find(id);
            IModelCollection<Weakness> weaknesses = pokemon.weaknesses().sortByDesc("effectiveness").where(w -> w.getEffectiveness() > 3);

            this.loadTemplate("/pokedex/pokemon/details", new Row() {{
                put("pokemon", pokemon);
                put("weaknesses", weaknesses);
                put("evolutions", new ModelCollection<Pokemon>());
            }});
        } catch (Exception e) {
            this.abort(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
