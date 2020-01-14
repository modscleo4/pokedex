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

import com.google.gson.Gson;

import java.util.LinkedHashMap;

/**
 * Row class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Row extends LinkedHashMap<String, Object> implements IRow {
    /**
     * Creates a new Row instance.
     */
    public Row() {
        super();
    }

    /**
     * Creates a new Row instance.
     *
     * @param indexes the indexes (columns)
     * @param values  the values
     */
    public Row(String[] indexes, Object[] values) {
        super();

        loadData(indexes, values);
    }

    /**
     * Creates a new Row instance.
     *
     * @param row the Row to copy from
     */
    public Row(IRow row) {
        if (row != null) {
            loadData(row.indexes(), row.values().toArray());
        }
    }

    @Override
    public String[] indexes() {
        String[] i = new String[this.size()];
        this.keySet().toArray(i);
        return i;
    }

    @Override
    public Object get(String key) {
        return super.get(key);
    }

    @Override
    public void set(String key, Object value) {
        if (!this.containsKey(key)) {
            this.put(key, value);
        } else {
            this.replace(key, value);
        }
    }

    /**
     * Reassigns all values of the Row.
     *
     * @param values the new values
     */
    private void setValues(Object[] values) {
        if (values.length != this.size()) {
            throw new IllegalArgumentException("indexes and values length does not match.");
        }

        for (int i = 0; i < this.size(); i++) {
            String key = (String) this.keySet().toArray()[i];
            this.set(key, values[i]);
        }
    }

    @Override
    public void fromRow(IRow row) {
        if (row != null) {
            loadData(row.indexes(), row.values().toArray());
        }
    }

    /**
     * Loads up the Row with values.
     *
     * @param indexes the indexes (columns)
     * @param values  the values
     */
    private void loadData(String[] indexes, Object[] values) {
        if (indexes.length != values.length) {
            throw new IllegalArgumentException("indexes and values length does not match.");
        }

        for (int i = 0; i < indexes.length; i++) {
            this.put(indexes[i], values[i]);
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, Row.class);
    }
}
