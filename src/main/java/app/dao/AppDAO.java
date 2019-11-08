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

package app.dao;

import app.entity.*;
import com.modscleo4.framework.dao.EntityDAO;

/**
 * Use this file to define all your DAO for the application
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class AppDAO {
    public static EntityDAO<Pokemon> pokemonDAO = new EntityDAO<>(Pokemon.class);
    public static EntityDAO<Ability> abilityDAO = new EntityDAO<>(Ability.class);
    public static EntityDAO<Category> categoryDAO = new EntityDAO<>(Category.class);
    public static EntityDAO<Gender> genderDAO = new EntityDAO<>(Gender.class);
    public static EntityDAO<Stats> statsDAO = new EntityDAO<>(Stats.class);
    public static EntityDAO<Type> typeDAO = new EntityDAO<>(Type.class);
}
