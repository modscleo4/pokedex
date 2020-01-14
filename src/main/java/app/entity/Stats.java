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

    public Pokemon pokemon() throws SQLException, InvalidKeyException, ClassNotFoundException {
        return (Pokemon) this.belongsTo(Pokemon.class);
    }

    public long getId() {
        return (long) this.get("id");
    }

    public int getHP() {
        return (int) this.get("hp");
    }

    public void setHP(int hp) {
        this.set("hp", hp);
    }

    public int getAttack() {
        return (int) this.get("attack");
    }

    public void setAttack(int attack) {
        this.set("attack", attack);
    }

    public int getDefense() {
        return (int) this.get("defense");
    }

    public void setDefense(int defense) {
        this.set("defense", defense);
    }

    public int getSpecialAttack() {
        return (int) this.get("special_attack");
    }

    public void setSpecialAttack(int specialAttack) {
        this.set("special_attack", specialAttack);
    }

    public int getSpecialDefense() {
        return (int) this.get("special_defense");
    }

    public void setSpecialDefense(int specialDefense) {
        this.set("special_defense", specialDefense);
    }

    public int getSpeed() {
        return (int) this.get("speed");
    }

    public void setSpeed(int speed) {
        this.set("speed", speed);
    }
}
