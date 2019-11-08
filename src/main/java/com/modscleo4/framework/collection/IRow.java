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

package com.modscleo4.framework.collection;

import java.util.Map;

/**
 * Row interface.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public interface IRow extends Map<String, Object> {
    /**
     * Get all indexes from the row.
     *
     * @return all indexes from the row
     */
    String[] indexes();

    /**
     * Get a value from the row
     *
     * @param key the index of value
     * @return the value of specified index from the row
     */
    Object get(String key);

    /**
     * Set a index from the row with some value
     *
     * @param key   the index of value
     * @param value the new value
     */
    void set(String key, Object value);

    /**
     * Loads up the row with values from another row.
     *
     * @param row the row to copy from
     */
    void fromRow(IRow row);

    String toString();
}
