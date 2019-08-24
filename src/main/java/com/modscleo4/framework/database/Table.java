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

package com.modscleo4.framework.database;

import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;

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

    /**
     * Runs table SELECT in database.
     *
     * @return all entries in database
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public IRowCollection select() throws SQLException, ClassNotFoundException {
        String sql = String.format("SELECT * FROM %s;", tableName);
        ResultSet rs = connection.query(sql);

        return IRowCollection.fromResultSet(rs);
    }

    /**
     * Runs table SELECT with WHERE (id) in database.
     *
     * @param primaryKey the primary key of the table
     * @param id         the value to search for
     * @return the found row in database, null if nothing found
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public IRow find(String primaryKey, Object id) throws SQLException, ClassNotFoundException {
        if (!primaryKey.equals("")) {
            String sql = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1;", tableName, primaryKey);

            List<Object> params = new ArrayList<Object>() {{
                add(id);
            }};
            ResultSet rs = connection.query(sql, params);
            ICollection<IRow> rowCollection = IRowCollection.fromResultSet(rs);
            if (rowCollection.size() > 0) {
                return rowCollection.get(0);
            }
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
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public IRowCollection where(String column, String comparator, Object value) throws SQLException, ClassNotFoundException {
        String sql = String.format("SELECT * FROM %s WHERE %s %s ? LIMIT 1;", tableName, column, comparator);

        List<Object> params = new ArrayList<Object>() {{
            add(value);
        }};
        ResultSet rs = connection.query(sql, params);

        return IRowCollection.fromResultSet(rs);
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

        String values = String.join(", ", data.values().toString());
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, vs);

        List<Object> params = new ArrayList<Object>() {{
            add(values);
        }};
        data.set(primaryKey, connection.update(sql, params, primaryKey));
    }

    /**
     * @param primaryKey the primary key of the table
     * @param data       the data to save
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public int update(String primaryKey, IRow data) throws SQLException, ClassNotFoundException {
        String[] arr = new String[data.keySet().size()];
        Arrays.fill(arr, "%s = ?");
        String updateString = String.format(String.join(", ", arr), data.keySet().toArray());

        String sql = String.format("UPDATE %s SET %s WHERE %s = ?;", tableName, updateString, primaryKey);

        List<Object> params = new ArrayList<Object>() {{
            for (String col : data.keySet()) {
                add(data.get(col));
            }

            add(data.get(primaryKey));
        }};

        return connection.update(sql, params);
    }
}
