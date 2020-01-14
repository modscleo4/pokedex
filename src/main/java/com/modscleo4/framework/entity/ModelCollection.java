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

package com.modscleo4.framework.entity;

import com.modscleo4.framework.callback.IFilterCallback;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.ICollection;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * Model Collection class.
 *
 * @param <T> the type of the Collection (IModel)
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class ModelCollection<T extends IModel> extends Collection<T> implements IModelCollection<T> {
    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    @Override
    public IModelCollection<T> where(IFilterCallback<T> callback) {
        IModelCollection<T> result = new ModelCollection<>();
        result.addAll(super.filter(callback));

        return result;
    }

    @Override
    public ICollection<Object> column(String column) {
        ICollection<Object> result = new Collection<>();
        for (T t : this) {
            result.add(t.get(column));
        }

        return result;
    }

    @Override
    public T find(String column, Object value) {
        return this.where(row -> row.get(column).equals(value)).first();
    }

    @Override
    public T find(Object value) {
        return this.find("id", value);
    }

    @Override
    public IModelCollection<T> sortBy(String column) {
        IModelCollection<T> result = this;
        result.sort((o1, o2) -> {
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

        return result;
    }

    @Override
    public IModelCollection<T> sortByDesc(String column) {
        IModelCollection<T> result = this;
        result.sort((o1, o2) -> {
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

        return result;
    }

    @Override
    public IModelCollection<T> page(int page) {
        IModelCollection<T> result = new ModelCollection<>();
        int limit = 10;
        int offset = (page - 1) * limit;

        for (int i = 0; i < limit; i++) {
            if (i + offset < this.size()) {
                result.add(this.get(i + offset));
            }
        }

        return result;
    }
}
