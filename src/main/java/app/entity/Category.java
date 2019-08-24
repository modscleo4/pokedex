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

import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;
import com.modscleo4.framework.entity.Model;

import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Category entity class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Category extends Model {
    static {
        table = "categories";
    }

    /**
     * Creates a new Category instance.
     */
    public Category() {
        super();
    }

    /**
     * Creates a new Category instance.
     *
     * @param row the IRow to copy data from
     */
    public Category(IRow row) {
        super(row);
    }

    /**
     * @return the matching hasMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public IRowCollection pokemons() throws IllegalArgumentException, InvalidKeyException, SQLException, ClassNotFoundException {
        return this.hasMany(Pokemon.class);
    }

    /**
     * Gets the Category id.
     *
     * @return the Category id
     */
    public long getId() {
        return (long) this.get("id");
    }

    /**
     * Gets the Category name.
     *
     * @return the Category name
     */
    public String getName() {
        return (String) this.get("name");
    }
}
