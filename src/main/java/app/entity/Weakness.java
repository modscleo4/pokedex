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

/**
 * Weakness entity class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Weakness extends Type {
    public Weakness() {
        super();
        fakeTable = "weaknesses";
    }

    public int getEffectiveness() {
        return (int) this.get("effectiveness");
    }

    public String getFormattedEffectiveness() {
        switch (this.getEffectiveness()) {
            case 0:
                return "0";
            case 1:
                return "⅛";
            case 2:
                return "¼";
            case 3:
                return "½";
            case 5:
                return "2";
            case 6:
                return "4";
        }

        return null;
    }

    public void setEffectiveness(int effectiveness) {
        this.set("effectiveness", effectiveness);
    }
}
