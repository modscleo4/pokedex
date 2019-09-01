/*
 Copyright 2019 Dhiego Cassiano Fogaça Barbosa

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package app.entity;

import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
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

    /**
     * Gets the predecessor Pokemon.
     *
     * @return the predecessor Pokemon
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public Pokemon predecessor() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (Pokemon) this.belongsTo(Pokemon.class, "predecessor_id");
    }

    /**
     * Gets the successor Pokemon.
     *
     * @return the successor Pokemon
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public Pokemon successor() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (Pokemon) this.belongsTo(Pokemon.class, "successor_id");
    }

    /**
     * Gets the Pokemon Category.
     *
     * @return the Pokemon Category
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public Category category() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return (Category) this.belongsTo(Category.class);
    }

    /**
     * Gets the Pokemon Genders.
     *
     * @return the Pokemon Genders
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public IModelCollection<Gender> genders() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Gender>) this.belongsToMany(Gender.class);
    }

    /**
     * Gets the Pokemon Abilities.
     *
     * @return the Pokemon Abilities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public IModelCollection<Ability> abilities() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Ability>) this.belongsToMany(Ability.class);
    }

    /**
     * Gets the Pokemon Stats.
     *
     * @return the Pokemon Stats
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     */
    public Stats stats() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (Stats) this.hasOne(Stats.class);
    }

    /**
     * Gets the Pokemon Types.
     *
     * @return the Pokemon Types
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public IModelCollection<Type> types() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Type>) this.belongsToMany(Type.class);
    }

    /**
     * Gets the Pokemon Weaknesses.
     *
     * @return the Pokemon Weaknesses
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public IModelCollection<Weakness> weaknesses() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (IModelCollection<Weakness>) this.belongsToMany(Weakness.class);
    }

    /**
     * Gets the Pokemon id.
     *
     * @return the Pokemon id
     */
    public long getId() {
        return (long) this.get("id");
    }

    /**
     * Gets the Pokemon name.
     *
     * @return the Pokemon name
     */
    public String getName() {
        return (String) this.get("name");
    }

    /**
     * Gets the Pokemon description.
     *
     * @return the Pokemon description
     */
    public String getDescription() {
        return (String) this.get("description");
    }

    /**
     * Gets the Pokemon height.
     *
     * @return the Pokemon height
     */
    public double getHeight() {
        return (double) this.get("height");
    }

    /**
     * Gets the Pokemon weight.
     *
     * @return the Pokemon weight
     */
    public double getWeight() {
        return (double) this.get("weight");
    }

    /**
     * Gets the Pokemon image.
     *
     * @return the Pokemon image
     * @throws IOException if the image was not found
     */
    public BufferedImage getImage() throws IOException {
        String link;

        if (!new File("images").isDirectory()) {
            new File("images").mkdir();
        }

        if (!new File("images/pokemon/").isDirectory()) {
            new File("images/pokemon/").mkdir();
        }

        if (!new File(String.format("images/pokemon/%03d.png", this.getId())).exists()) {
            link = String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", this.getId());
            URL url = new URL(link);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(String.format("images/pokemon/%03d.png", this.getId()));
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }

        link = String.format("images/pokemon/%03d.png", this.getId());
        File file = new File(link);

        return ImageIO.read(file);
    }
}
