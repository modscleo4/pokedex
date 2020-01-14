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

package com.modscleo4.framework.database.sql;

import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;
import com.modscleo4.framework.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SelectQueryBuilder extends QueryBuilder {
    private Table table;

    private String[] columns;

    private List<Object> params;

    private long limit;
    private long offset;

    private ICollection<String> where;
    private ICollection<IRow> orderBy;
    private ICollection<String> groupBy;

    /**
     * @param table
     * @param columns
     */
    public SelectQueryBuilder(Table table, String[] columns) {
        super(table.getConnection());
        this.table = table;
        this.params = new ArrayList<>();

        this.columns = columns;

        this.where = new Collection<>();
        this.orderBy = new Collection<>();
        this.groupBy = new Collection<>();
    }

    /**
     * @param table
     */
    public SelectQueryBuilder(Table table) {
        this(table, new String[]{"*"});
    }

    public SelectQueryBuilder limit(long limit, long offset) {
        this.limit = limit;
        this.offset = offset;

        return this;
    }

    public SelectQueryBuilder limit(long limit) {
        this.limit = limit;
        this.offset = 0;

        return this;
    }

    public SelectQueryBuilder orderBy(ICollection<IRow> orderBy) {
        this.orderBy = orderBy;

        return this;
    }

    public SelectQueryBuilder groupBy(ICollection<String> groupBy) {
        this.groupBy = groupBy;

        return this;
    }

    public SelectQueryBuilder where(String field, String comparator, Object value) {
        if (where.size() == 0) {
            where.add(String.format("%s %s ?", field, comparator));
        } else {
            where.add(String.format("AND %s %s ?", field, comparator));
        }

        params.add(value);

        return this;
    }

    public SelectQueryBuilder orWhere(String field, String comparator, Object value) {
        if (where.size() == 0) {
            where.add(String.format("%s %s ?", field, comparator));
        } else {
            where.add(String.format("OR %s %s ?", field, comparator));
        }

        params.add(value);

        return this;
    }

    @Override
    public String toString() {
        String sql = String.format("SELECT %s FROM %s", String.join(", ", columns), table.getTableName());
        if (where.size() > 0) {
            sql += " WHERE";
            sql += where.reduce((acc, w) -> String.format("%s %s", acc, w), "");
        }

        if (orderBy.size() > 0) {
            sql += " ORDER BY";
            sql += orderBy.reduce((acc, o) -> String.format("%s %s %s, ", acc, o.get("col"), o.get("mode")), "");
            sql = sql.substring(0, sql.length() - 2);
        }

        if (groupBy.size() > 0) {
            sql += " GROUP BY";
            sql += groupBy.reduce((acc, g) -> String.format("%s %s, ", acc, g), "");
            sql = sql.substring(0, sql.length() - 2);
        }

        return sql + ";";
    }

    public IRowCollection get() throws SQLException, ClassNotFoundException {
        String sql = String.format(this.toString(), table.getTableName());
        ResultSet rs = this.connection.query(sql, params);

        return IRowCollection.fromResultSet(rs);
    }
}
