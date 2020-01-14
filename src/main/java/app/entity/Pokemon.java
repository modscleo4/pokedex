/*
 * Copyright 2019 Dhiego Cassiano Fogaça Barbosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.entity;

import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.Model;
import com.modscleo4.http.HTTP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Pokemon entity class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Pokemon extends Model {
    public Pokemon() {
        table = "pokemon";
    }

    public Pokemon predecessor() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (Pokemon) this.belongsTo(Pokemon.class, "predecessor_id");
    }

    public Pokemon successor() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (Pokemon) this.belongsTo(Pokemon.class, "successor_id");
    }

    public IModelCollection<Pokemon> successors() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Pokemon>) this.hasMany(Pokemon.class, "predecessor_id");
    }

    public Category category() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (Category) this.belongsTo(Category.class);
    }

    public IModelCollection<Gender> genders() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Gender>) this.belongsToMany(Gender.class);
    }

    public void syncGenders(IModelCollection<Gender> genders) {

    }

    public IModelCollection<Ability> abilities() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Ability>) this.belongsToMany(Ability.class);
    }

    public Stats stats() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (Stats) this.hasOne(Stats.class);
    }

    public IModelCollection<Type> types() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Type>) this.belongsToMany(Type.class);
    }

    public IModelCollection<Weakness> weaknesses() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Weakness>) this.belongsToMany(Weakness.class);
    }

    public Weakness weaknessAgainst(Type t) throws SQLException, InvalidKeyException, ClassNotFoundException {
        return ((IModelCollection<Weakness>) this.belongsToMany(Weakness.class)).where(w -> w.getId() == t.getId()).first();
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

    public double getHeight() {
        return (double) this.get("height");
    }

    public void setHeight(double height) {
        this.set("height", height);
    }

    public long getCategoryId() {
        return (long) this.get("category_id");
    }

    public void setCategoryId(long categoryId) {
        this.set("category_id", categoryId);
    }

    public double getWeight() {
        return (double) this.get("weight");
    }

    public void setWeight(double weight) {
        this.set("weight", weight);
    }

    public long getPredecessorId() {
        if (this.get("predecessor_id") == null) {
            return 0;
        }

        return (long) this.get("predecessor_id");
    }

    public void setPredecessorId(long predecessorId) {
        if (predecessorId <= 0) {
            this.set("predecessor_id", null);
        } else {
            this.set("predecessor_id", predecessorId);
        }
    }

    public long getSuccessorId() {
        if (this.get("successor_id") == null) {
            return 0;
        }

        return (long) this.get("successor_id");
    }

    public void setSuccessorId(long successorId) {
        if (successorId <= 0) {
            this.set("successor_id", null);
        } else {
            this.set("successor_id", successorId);
        }
    }

    public Image getImage() throws IOException {
        String link;

        if (!new File("images").isDirectory()) {
            new File("images").mkdir();
        }

        if (!new File("images/pokemon/").isDirectory()) {
            new File("images/pokemon/").mkdir();
        }

        if (!new File(String.format("images/pokemon/%03d.png", this.getId())).exists()) {
            link = String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", this.getId());
            String file = String.format("images/pokemon/%03d.png", this.getId());
            HTTP.downloadFile(link, file);
        }

        link = String.format("images/pokemon/%03d.png", this.getId());
        File file = new File(link);

        return ImageIO.read(file);
    }
}
