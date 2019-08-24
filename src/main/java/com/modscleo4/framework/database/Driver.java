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
     *
     */
    private static final String[] dbnames = {"sqlite", "mysql", "postgresql", "sqlserver", "as400"};

    /**
     *
     */
    private String name;

    /**
     * @param driver
     */
    public Driver(Drivers driver) {
        this.setName(driver);
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    private void setName(String name) {
        if (Arrays.asList(Driver.dbnames).contains(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid database driver name");
        }
    }

    /**
     * @param driver
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
     * @return
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

    /**
     * @return
     */
    public String select() {
        switch (getName()) {
            case "sqlite":
            case "mysql":
            case "postgresql":
            case "sqlserver":
            case "as400":
                return "SELECT * FROM %s";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
