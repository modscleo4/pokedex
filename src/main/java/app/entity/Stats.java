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

import com.modscleo4.framework.entity.Model;

import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Stats entity class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Stats extends Model {
    public Stats() {
        table = "stats";
    }

    /**
     * Gets the Pokemon.
     *
     * @return the Pokemon
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    public Pokemon pokemon() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (Pokemon) this.belongsTo(Pokemon.class);
    }

    /**
     * Gets the Stats id.
     *
     * @return the Stats id
     */
    public long getId() {
        return (long) this.get("id");
    }

    /**
     * Gets the HP.
     *
     * @return the HP
     */
    public int getHP() {
        return (int) this.get("hp");
    }

    /**
     * Gets the attack.
     *
     * @return the attack
     */
    public int getAttack() {
        return (int) this.get("attack");
    }

    /**
     * Gets the defense.
     *
     * @return the defense
     */
    public int getDefense() {
        return (int) this.get("defense");
    }

    /**
     * Gets the special attack.
     *
     * @return the special attack
     */
    public int getSpecialAttack() {
        return (int) this.get("special_attack");
    }

    /**
     * Gets the special defense.
     *
     * @return the special defense
     */
    public int getSpecialDefense() {
        return (int) this.get("special_defense");
    }

    /**
     * Gets the speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return (int) this.get("speed");
    }
}
