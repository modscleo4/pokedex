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

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * Row Collection class.
 */
public class RowCollection extends Collection<IRow> implements IRowCollection {
    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    @Override
    public IRowCollection where(IFilterCallback<IRow> callback) {
        ICollection<IRow> collection = super.filter(callback);
        IRowCollection rowCollection = new RowCollection();
        rowCollection.addAll(collection);

        return rowCollection;
    }

    @Override
    public IRowCollection sortBy(String column) {
        IRowCollection rowCollection = this;
        rowCollection.sort((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }

            if (isNumeric(o1.get(column).toString()) && isNumeric(o2.get(column).toString())) {
                BigDecimal n1 = new BigDecimal(o1.get(column).toString());
                BigDecimal n2 = new BigDecimal(o2.get(column).toString());
                return n1.compareTo(n2);
            } else {
                return o1.get(column).toString().compareTo(o2.get(column).toString());
            }
        });

        return rowCollection;
    }

    @Override
    public IRowCollection sortByDesc(String column) {
        IRowCollection rowCollection = this;
        rowCollection.sort((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }

            if (isNumeric(o1.get(column).toString()) && isNumeric(o2.get(column).toString())) {
                BigDecimal n1 = new BigDecimal(o1.get(column).toString());
                BigDecimal n2 = new BigDecimal(o2.get(column).toString());
                return n2.compareTo(n1);
            } else {
                return o2.get(column).toString().compareTo(o1.get(column).toString());
            }
        });

        return rowCollection;
    }
}
