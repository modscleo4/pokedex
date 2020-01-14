package app.entity;

import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.entity.Model;

import java.security.InvalidKeyException;
import java.sql.SQLException;

public class Role extends Model {
    public Role() {
        table = "roles";
    }

    public ICollection<User> users() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (ICollection<User>) this.hasMany(User.class);
    }

    public long getId() {
        return (long) this.get("id");
    }

    public String getName() {
        return (String) this.get("name");
    }

    public void setName(String name) {
        this.set("name", name);
    }

    public String getDescription() {
        return (String) this.get("description");
    }

    public void setDescription(String description) {
        this.set("description", description);
    }
}
