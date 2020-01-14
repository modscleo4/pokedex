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

package com.modscleo4.framework.database;

import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;
import com.modscleo4.framework.database.sql.SelectQueryBuilder;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DB Table class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Table {
    /**
     * The Connection adapter.
     */
    private Connection connection;

    /**
     * The table name.
     */
    private String tableName;

    /**
     * Creates a new Table instance.
     *
     * @param connection the Connection adapter
     * @param tableName  the table name
     */
    public Table(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    public Connection getConnection() {
        return connection;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * Gets the next sequence ID.
     *
     * @return the next sequence ID
     */
    public IRow currentID(String primaryKey) throws SQLException, ClassNotFoundException {
        String sql = String.format("SELECT last_value FROM %s_%s_seq;", this.getTableName(), primaryKey);
        ResultSet rs = this.connection.query(sql);

        return IRowCollection.fromResultSet(rs).first();
    }

    /**
     * Runs table SELECT in database.
     *
     * @return all entries in database
     */
    public SelectQueryBuilder select() {
        return new SelectQueryBuilder(this);
    }

    /**
     * Runs table SELECT with pagination in database.
     *
     * @param limit  the desired limit
     * @param offset the desired offset
     * @return all entries in database
     */
    public SelectQueryBuilder selectPaginated(int limit, int offset) {
        return new SelectQueryBuilder(this).limit(limit, offset);
    }

    /**
     * Runs table SELECT with pagination in database (limit = 10).
     *
     * @param offset the desired offset
     * @return all entries in database
     */
    public SelectQueryBuilder selectPaginated(int offset) {
        return selectPaginated(10, offset);
    }

    /**
     * Runs table SELECT with WHERE (id) in database.
     *
     * @param primaryKey the primary key of the table
     * @param id         the value to search for
     * @return the found row in database, null if nothing found
     */
    public SelectQueryBuilder find(String primaryKey, Object id) {
        if (!primaryKey.equals("")) {
            return this.where(primaryKey, "=", id).limit(1);
        }

        return null;
    }

    /**
     * Runs table SELECT with WHERE in database.
     *
     * @param column     the column to compare
     * @param comparator the comparator (<, <=, >, >=, =, <>)
     * @param value      the value to compare
     * @return all entities that match the condition
     */
    public SelectQueryBuilder where(String column, String comparator, Object value) {
        return new SelectQueryBuilder(this).where(column, comparator, value);
    }

    /**
     * Runs table INSERT in database.
     *
     * @param primaryKey the primary key of the table
     * @param data       the data to save
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public void store(String primaryKey, IRow data) throws SQLException, ClassNotFoundException {
        String columns = String.join(", ", data.keySet());

        String[] arr = new String[data.keySet().size()];
        Arrays.fill(arr, "?");
        String vs = String.format(String.join(", ", arr), data.keySet().toArray());

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, vs);

        List<Object> params = new ArrayList<Object>() {{
            data.keySet().forEach(col -> add(data.get(col)));
        }};

        if (primaryKey != null) {
            data.set(primaryKey, connection.update(sql, params, primaryKey));
        } else {
            connection.update(sql, params);
        }
    }

    /**
     * Runs table UPDATE in database.
     *
     * @param primaryKey the primary key of the table
     * @param data       the data to save
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public int update(@NotNull String primaryKey, IRow data) throws SQLException, ClassNotFoundException {
        String[] arr = new String[data.keySet().size()];
        Arrays.fill(arr, "%s = ?");
        String updateString = String.format(String.join(", ", arr), data.keySet().toArray());

        String sql = String.format("UPDATE %s SET %s WHERE %s = ?;", tableName, updateString, primaryKey);

        List<Object> params = new ArrayList<Object>() {{
            data.keySet().forEach(col -> add(data.get(col)));

            add(data.get(primaryKey));
        }};

        return connection.update(sql, params);
    }

    /**
     * Runs table DELETE in database.
     *
     * @param primaryKey the primary key of the table
     * @param data       the data to save
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
     * or (2) 0 for SQL statements that return nothing
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public int delete(@NotNull String primaryKey, IRow data) throws SQLException, ClassNotFoundException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?;", tableName, primaryKey);

        List<Object> params = new ArrayList<Object>() {{
            add(data.get(primaryKey));
        }};

        return connection.update(sql, params);
    }
}
