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

import java.sql.*;
import java.util.List;

/**
 * DB Connection class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Connection {
    /**
     * The DB driver.
     */
    private Driver driver;

    /**
     * The JDBC connection adapter.
     */
    private java.sql.Connection connection;

    /**
     * The JDBC url.
     */
    private String url;

    /**
     * The DB username.
     */
    private String username;

    /**
     * The DB password.
     */
    private String password;

    /**
     * Creates a new DB Connection instance (SQLite).
     *
     * @param db the sqlite .db path
     */
    public Connection(String db) {
        this(Driver.SQLITE, null, null, db, null, null);
    }

    /**
     * Creates a new DB Connection instance.
     *
     * @param host     the hostname to connects to
     * @param port     the port to connects to
     * @param db       the database to use
     * @param username the username to connects to database
     * @param password the password of username
     */
    public Connection(int driver, String host, String port, String db, String username, String password) {
        this.driver = new Driver(driver);
        db = db.trim();

        if (driver == Driver.SQLITE) {
            this.username = null;
            this.password = null;
        } else {
            host = host.trim();
            port = port.trim();
            this.username = username.trim();
            this.password = password;
        }

        String driverName = this.driver.getName();

        switch (driver) {
            case Driver.SQLITE:
                this.url = String.format("jdbc:%s", db);
                break;
            case Driver.MYSQL:
                if (port.equals("")) {
                    port = "3306";
                }

                this.url = String.format("jdbc:%s://%s:%s/%s", driverName, host, port, db);
                break;
            case Driver.POSTGRESQL:
                if (port.equals("")) {
                    port = "5432";
                }

                this.url = String.format("jdbc:%s://%s:%s/%s", driverName, host, port, db);
                break;
            case Driver.SQLSERVER:
                if (port.equals("")) {
                    port = "446";
                }

                this.url = String.format("jdbc:%s://%s:%s/%s", driverName, host, port, db);
                break;
            case Driver.AS400:
                if (port.equals("")) {
                    port = "1433";
                }

                this.url = String.format("jdbc:%s://%s:%s;DatabaseName=%s", driverName, host, port, db);
                break;
        }
    }

    /**
     * Opens the Connection.
     *
     * @throws ClassNotFoundException if the driver was not found.
     * @throws SQLException           if the connection could not be established.
     */
    public void open() throws ClassNotFoundException, SQLException {
        String driverName = this.driver.getClassName();
        Class.forName(driverName);
        this.connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Closes the Connection.
     *
     * @throws SQLException if the connection could not be closed.
     */
    public void close() throws SQLException {
        this.connection.close();
    }

    /**
     * Gets the DB driver.
     *
     * @return the DB driver.
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Runs a SQL statement that does not need to return data.
     *
     * @param sql the SQL string
     * @return <code>true</code> if the first result is a <code>ResultSet</code> object; <code>false</code> if it is an update count or there are no results
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public boolean run(String sql) throws SQLException, ClassNotFoundException {
        this.open();

        Statement stmt = connection.createStatement();
        boolean ret = stmt.execute(sql);
        this.close();
        return ret;
    }

    /**
     * @param sql    the prepared SQL string
     * @param params the values to prepared query
     * @return <code>true</code> if the first result is a <code>ResultSet</code> object; <code>false</code> if it is an update count or there are no results
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public boolean run(String sql, List<Object> params) throws SQLException, ClassNotFoundException {
        this.open();

        PreparedStatement stmt = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        boolean ret = stmt.execute();
        this.close();
        return ret;
    }

    /**
     * @param sql the SQL string
     * @return a <code>ResultSet</code> object that contains the data produced by the given query; never <code>null</code>
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public ResultSet query(String sql) throws SQLException, ClassNotFoundException {
        this.open();

        Statement stmt = connection.createStatement();
        ResultSet ret = stmt.executeQuery(sql);
        this.close();
        return ret;
    }

    /**
     * @param sql    the prepared SQL string
     * @param params the values to prepared query
     * @return a <code>ResultSet</code> object that contains the data produced by the given query; never <code>null</code>
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public ResultSet query(String sql, List<Object> params) throws SQLException, ClassNotFoundException {
        this.open();

        PreparedStatement stmt = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet ret = stmt.executeQuery();
        this.close();
        return ret;
    }

    /**
     * @param sql the SQL string
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public int update(String sql) throws SQLException, ClassNotFoundException {
        this.open();

        Statement stmt = connection.createStatement();
        int ret = stmt.executeUpdate(sql);
        this.close();
        return ret;
    }

    /**
     * @param sql    the prepared SQL string
     * @param params the values to prepared query
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public int update(String sql, List<Object> params) throws SQLException, ClassNotFoundException {
        this.open();

        PreparedStatement stmt = connection.prepareStatement(sql);
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        int ret = stmt.executeUpdate();

        this.close();
        return ret;
    }

    /**
     * @param sql       the prepared SQL string
     * @param params    the values to prepared query
     * @param returning the column to return
     * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    public Object update(String sql, List<Object> params, String returning) throws SQLException, ClassNotFoundException {
        this.open();

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        Object ret = stmt.executeUpdate();

        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            ret = generatedKeys.getObject(returning);
        }

        this.close();
        return ret;
    }

    public Table table(String tableName) {
        return new Table(this, tableName);
    }
}
