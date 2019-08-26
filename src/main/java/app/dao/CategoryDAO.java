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

package app.dao;

import app.entity.Category;
import com.modscleo4.framework.dao.EntityDAO;

/**
 * Pokemon category DAO class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class CategoryDAO extends EntityDAO<Category> {
    /**
     * Creates a new Category DAO instance.
     *
     * @throws IllegalArgumentException if the entity class could not be instantiated
     */
    public CategoryDAO() throws IllegalArgumentException {
        super(Category.class);
    }
}
