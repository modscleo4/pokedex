package app.web.controllers.admin;

import app.dao.AppDAO;
import app.entity.*;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.ModelCollection;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

public class PokemonController extends Controller {
    public PokemonController() {
        this.acl("Admin", new String[]{"index", "create", "edit", "show", "store", "update", "delete"});
    }

    public void index() throws IOException, SQLException, ClassNotFoundException {
        ICollection<Pokemon> pokemon = AppDAO.pokemonDAO.all().sortBy("id");

        this.loadTemplate("/admin/pokemon/index", new Row() {{
            put("pokemon", pokemon);
        }});
    }

    public void create() throws IOException, SQLException, ClassNotFoundException {
        long id = AppDAO.pokemonDAO.nextID();
        ICollection<Pokemon> pokemonL = AppDAO.pokemonDAO.all().sortBy("id");
        ICollection<Category> categories = AppDAO.categoryDAO.all().sortBy("id");
        ICollection<Gender> genders = AppDAO.genderDAO.all().sortBy("id");
        ICollection<Type> types = AppDAO.typeDAO.all().sortBy("id");

        this.loadTemplate("/admin/pokemon/new", new Row() {{
            put("id", id);
            put("pokemonL", pokemonL);
            put("categories", categories);
            put("genders", genders);
            put("types", types);
        }});
    }

    public void edit(Collection<String> parameters) throws IOException, ClassNotFoundException, NoSuchFieldException, SQLException {
        long id = Long.parseLong(parameters.get(0));
        Pokemon pokemon = AppDAO.pokemonDAO.find(id);
        ICollection<Pokemon> pokemonL = AppDAO.pokemonDAO.all().sortBy("id");
        ICollection<Category> categories = AppDAO.categoryDAO.all().sortBy("id");
        ICollection<Gender> genders = AppDAO.genderDAO.all().sortBy("id");
        ICollection<Type> types = AppDAO.typeDAO.all().sortBy("id");

        pokemonL.remove((int) id - 1);

        this.loadTemplate("/admin/pokemon/edit", new Row() {{
            put("pokemon", pokemon);
            put("pokemonL", pokemonL);
            put("categories", categories);
            put("genders", genders);
            put("types", types);
        }});
    }

    public void show(Collection<String> parameters) throws IOException, ClassNotFoundException, NoSuchFieldException, SQLException, InvalidKeyException {
        long id = Long.parseLong(parameters.get(0));
        Pokemon pokemon = AppDAO.pokemonDAO.find(id);
        IModelCollection<Weakness> weaknesses = pokemon.weaknesses().sortByDesc("effectiveness").where(w -> w.getEffectiveness() > 3);

        this.loadTemplate("/admin/pokemon/details", new Row() {{
            put("pokemon", pokemon);
            put("weaknesses", weaknesses);
        }});
    }

    public void store(HttpServletRequest request) throws IOException, ClassNotFoundException, NoSuchFieldException, SQLException {
        boolean _saved = true;

        Pokemon pokemon = new Pokemon();

        pokemon.setName(request.getParameter("name"));
        pokemon.setDescription(request.getParameter("description"));
        pokemon.setHeight(Double.parseDouble(request.getParameter("height").replace(",", ".")));
        pokemon.setCategoryId(Long.parseLong(request.getParameter("category_id")));
        pokemon.setWeight(Double.parseDouble(request.getParameter("weight")));
        pokemon.setPredecessorId(Long.parseLong(request.getParameter("predecessor")));
        pokemon.setSuccessorId(Long.parseLong(request.getParameter("successor")));

        String[] genders = request.getParameterValues("genders[]");
        pokemon.syncGenders(new ModelCollection<Gender>() {{
            for (String gender : genders) {
                add(AppDAO.genderDAO.find(Long.parseLong(gender)));
            }
        }});

        try {
            pokemon.save();
        } catch (Exception e) {
            _saved = false;
        }

        boolean saved = _saved;
        this.redirect("/admin/pokemon", new Row() {{
            put("saved", saved);
            put("action", "store");
        }});
    }

    public void update(HttpServletRequest request, Collection<String> parameters) throws IOException, ClassNotFoundException, NoSuchFieldException, SQLException {
        long id = Long.parseLong(parameters.get(0));
        Pokemon pokemon = AppDAO.pokemonDAO.find(id);
        boolean _saved = true;

        try {
            pokemon.save();
        } catch (Exception e) {
            _saved = false;
        }

        boolean saved = _saved;

        this.redirect("/admin/pokemon", new Row() {{
            put("saved", saved);
            put("action", "update");
        }});
    }

    public void destroy(HttpServletRequest request, Collection<String> parameters) throws IOException, ClassNotFoundException, NoSuchFieldException, SQLException {
        long id = Long.parseLong(parameters.get(0));
        Pokemon pokemon = AppDAO.pokemonDAO.find(id);
        boolean _saved = true;

        try {
            pokemon.delete();
        } catch (Exception e) {
            _saved = false;
        }

        boolean saved = _saved;

        this.redirect("/admin/pokemon", new Row() {{
            put("saved", saved);
            put("action", "destroy");
        }});
    }
}
