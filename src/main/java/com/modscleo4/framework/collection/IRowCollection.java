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

import com.modscleo4.framework.callback.IFilterCallback;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Row Collection interface.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public interface IRowCollection extends ICollection<IRow> {
    /**
     * Create a Row Collection from a ResultSet.
     *
     * @param rs the ResultSet
     * @return return a new RowCollection from ResultSet
     * @throws SQLException if the ResultSet is invalid
     */
    static IRowCollection fromResultSet(ResultSet rs) throws SQLException {
        IRowCollection rowCollection = new RowCollection();

        ResultSetMetaData rsMetadata = rs.getMetaData();
        List<String> c = new ArrayList<>();

        for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
            c.add(rsMetadata.getColumnName(i));
        }

        String[] columns = c.toArray(new String[rsMetadata.getColumnCount()]);

        while (rs.next()) {
            List<Object> v = new ArrayList<>();
            for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
                v.add(rs.getObject(i));
            }
            Object[] values = v.toArray(new Object[rsMetadata.getColumnCount()]);

            IRow row = new Row(columns, values);
            rowCollection.add(row);
        }

        rs.close();

        return rowCollection;
    }

    /**
     * Filters the Collection.
     *
     * @param callback callback function
     * @return a new Collection as result
     */
    IRowCollection where(IFilterCallback<IRow> callback);

    /**
     * Sorts the Collection ascending.
     *
     * @param column the column to sort
     * @return a new Collection as result
     */
    IRowCollection sortBy(String column);

    /**
     * Sorts the Collection descending.
     *
     * @param column the column to sort
     * @return a new Collection as result
     */
    IRowCollection sortByDesc(String column);
}
