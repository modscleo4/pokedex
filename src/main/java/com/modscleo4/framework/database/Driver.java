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

import java.util.Arrays;

/**
 * DB drivers class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Driver {
    /**
     * The supported drivers of the framework.
     */
    private static final String[] dbnames = {"sqlite", "mysql", "postgresql", "sqlserver", "as400"};

    /**
     * The driver name attribute.
     */
    private String name;

    /**
     * Creates a new Driver instance.
     *
     * @param driver the driver to use
     */
    public Driver(Drivers driver) {
        this.setName(driver);
    }

    public static Drivers fromName(String name) {
        switch (name) {
            case "sqlite":
                return Drivers.SQLITE;
            case "mysql":
                return Drivers.MYSQL;
            case "postgresql":
                return Drivers.POSTGRESQL;
            case "sqlserver":
                return Drivers.SQLSERVER;
            case "as400":
                return Drivers.AS400;
            default:
                throw new IllegalArgumentException("Invalid driver name.");
        }
    }

    /**
     * Gets the driver name (postgresql, sqlite, mysql...)
     *
     * @return the driver name
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the driver.
     *
     * @param name the driver name
     */
    private void setName(String name) {
        if (Arrays.asList(Driver.dbnames).contains(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid database driver name");
        }
    }

    /**
     * Changes the driver name.
     *
     * @param driver the new driver
     */
    private void setName(Drivers driver) {
        switch (driver) {
            case SQLITE:
                this.setName("sqlite");
                break;
            case MYSQL:
                this.setName("mysql");
                break;
            case POSTGRESQL:
                this.setName("postgresql");
                break;
            case SQLSERVER:
                this.setName("sqlserver");
                break;
            case AS400:
                this.setName("as400");
                break;
        }
    }

    /**
     * Gets the Class name of the selected driver.
     *
     * @return the Class name of the selected driver.
     */
    public String getClassName() {
        switch (getName()) {
            case "sqlite":
                return "org.sqlite.JDBC";
            case "mysql":
                return "com.mysql.jdbc.Driver";
            case "postgresql":
                return "org.postgresql.Driver";
            case "sqlserver":
                return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
            case "as400":
                return "com.ibm.as400.access.AS400JDBCDriver";
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
