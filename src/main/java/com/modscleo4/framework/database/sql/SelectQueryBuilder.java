package com.modscleo4.framework.database.sql;

import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;
import com.modscleo4.framework.database.Table;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class SelectQueryBuilder extends QueryBuilder {
    private Table table;

    private String[] columns;

    private long limit;
    private long offset;

    private ICollection<IRow> orderBy;

    private String[] groupBy;

    /**
     * @param table
     * @param columns
     */
    public SelectQueryBuilder(Table table, String[] columns) {
        super(table.getConnection());
        this.table = table;

        this.columns = columns;
    }

    /**
     * @param table
     */
    public SelectQueryBuilder(Table table) {
        this(table, new String[]{"*"});
    }

    public QueryBuilder limit(long limit, long offset) {
        this.limit = limit;
        this.offset = offset;

        return this;
    }

    public QueryBuilder limit(long limit) {
        this.limit = limit;
        this.offset = 0;

        return this;
    }

    public QueryBuilder orderBy(ICollection<IRow> orderBy) {
        this.orderBy = orderBy;

        return this;
    }

    public QueryBuilder groupBy(String[] groupBy) {
        this.groupBy = groupBy;

        return this;
    }

    public QueryBuilder where(String field, String comparator, Object value) {
        return this;
    }

    @Override
    public String toString() {
        return "" + ";";
    }

    public IRowCollection get() throws SQLException, ClassNotFoundException {
        String sql = String.format(this.toString(), table.getTableName());
        ResultSet rs = this.connection.query(sql);

        return IRowCollection.fromResultSet(rs);
    }
}
