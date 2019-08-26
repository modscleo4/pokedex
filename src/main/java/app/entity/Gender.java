package app.entity;

import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.Model;

import java.security.InvalidKeyException;
import java.sql.SQLException;

public class Gender extends Model {
    public Gender() {
        table = "genders";
    }

    /**
     * @return the matching hasMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public IModelCollection<Pokemon> pokemons() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (IModelCollection<Pokemon>) this.hasMany(Pokemon.class);
    }

    /**
     * Gets the Gender id.
     *
     * @return the Gender id
     */
    public long getId() {
        return (long) this.get("id");
    }

    /**
     * Gets the Gender name.
     *
     * @return the Gender name
     */
    public String getName() {
        return (String) this.get("name");
    }
}
