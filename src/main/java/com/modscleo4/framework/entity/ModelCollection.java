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

package com.modscleo4.framework.entity;

import com.modscleo4.framework.callback.IFilterCallback;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.collection.ICollection;

/**
 * Model Collection class.
 *
 * @param <T> the type of the Collection (IModel)
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class ModelCollection<T extends IModel> extends Collection<T> implements IModelCollection<T> {
    @Override
    public IModelCollection<T> where(IFilterCallback<T> callback) {
        ICollection<T> collection = super.filter(callback);
        IModelCollection<T> rowCollection = new ModelCollection<>();
        rowCollection.addAll(collection);

        return rowCollection;
    }

    @Override
    public IModelCollection<T> sortBy(String column) {
        IModelCollection<T> rowCollection = this;
        rowCollection.sort((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }

            return o1.get(column).toString().compareTo(o2.get(column).toString());
        });

        return rowCollection;
    }

    @Override
    public IModelCollection<T> sortByDesc(String column) {
        IModelCollection<T> rowCollection = this;
        rowCollection.sort((o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }

            return o2.get(column).toString().compareTo(o1.get(column).toString());
        });

        return rowCollection;
    }
}
