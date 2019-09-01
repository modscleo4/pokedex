package com.modscleo4.framework.database.sql;

import com.modscleo4.framework.database.Driver;

public class QueryBuilder {
    private Driver driver;
    private Commands command;
    private String sql;
    private boolean hasWhere = false;
    private boolean hasOrderBy = false;
    private boolean hasGroupBy = false;
    private boolean hasLimit = false;

    public QueryBuilder(Driver driver) {
        this.driver = driver;
    }

    /**
     * Gets the SQL INSERT command for the specific DBMS.
     *
     * @param columns the desired columns
     * @return the SQL INSERT command for the specific DBMS
     */
    public QueryBuilder select(String columns) {
        switch (driver.getName()) {
            case "sqlite":
            case "mysql":
            case "postgresql":
            case "sqlserver":
            case "as400":
                this.sql = "SELECT * FROM %s";
            default:
                this.sql = "";
        }

        return this;
    }

    /**
     * Gets the SQL INSERT command for the specific DBMS.
     *
     * @return the SQL INSERT command for the specific DBMS
     */
    public QueryBuilder select() {
        return this.select("*");
    }

    public QueryBuilder where(String comparator) {
        switch (driver.getName()) {
            case "sqlite":
            case "mysql":
            case "postgresql":
            case "sqlserver":
            case "as400":
                this.sql += String.format(" WHERE %s %s ?", "%d", comparator);
            default:
                this.sql = "";
        }

        return this;
    }

    public QueryBuilder limit() {
        if (!hasLimit) {
            switch (driver.getName()) {
                case "sqlite":
                case "mysql":
                case "postgresql":
                case "sqlserver":
                case "as400":
                    this.hasLimit = true;
                    this.sql += " LIMIT %d";
                    break;
                default:
                    this.sql = "";
                    break;
            }
        }

        return this;
    }

    public QueryBuilder limitOffset() {
        if (!hasLimit) {
            switch (driver.getName()) {
                case "sqlite":
                case "mysql":
                case "postgresql":
                case "sqlserver":
                case "as400":
                    this.hasLimit = true;
                    this.sql += " LIMIT %d OFFSET %d";
                    break;
                default:
                    this.sql = "";
                    break;
            }
        }

        return this;
    }

    public QueryBuilder orderBy() {
        if (!this.hasOrderBy) {
            switch (driver.getName()) {
                case "sqlite":
                case "mysql":
                case "postgresql":
                case "sqlserver":
                case "as400":
                    this.hasOrderBy = true;
                    this.sql += " ORDER BY %s";
                    break;
                default:
                    this.sql = "";
                    break;
            }
        }

        return this;
    }

    public QueryBuilder groupBy() {
        if (!this.hasGroupBy) {
            switch (driver.getName()) {
                case "sqlite":
                case "mysql":
                case "postgresql":
                case "sqlserver":
                case "as400":
                    this.hasGroupBy = true;
                    this.sql += " GROUP BY %s";
                    break;
                default:
                    this.sql = "";
                    break;
            }
        }

        return this;
    }

    @Override
    public String toString() {
        return sql + ";";
    }
}
