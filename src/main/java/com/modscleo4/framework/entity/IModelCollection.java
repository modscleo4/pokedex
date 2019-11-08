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
import com.modscleo4.framework.collection.ICollection;

/**
 * Model Collection interface.
 *
 * @param <T> the type of the Collection (IModel)
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public interface IModelCollection<T extends IModel> extends ICollection<T> {
    /**
     * Filters the ModelCollection.
     *
     * @param callback callback function
     * @return a new IModelCollection as result
     */
    IModelCollection<T> where(IFilterCallback<T> callback);

    /**
     * Sorts the ModelCollection ascending.
     *
     * @param column the column to sort
     * @return a new IModelCollection as result
     */
    IModelCollection<T> sortBy(String column);

    /**
     * Sorts the ModelCollection descending.
     *
     * @param column the column to sort
     * @return a new IModelCollection as result
     */
    IModelCollection<T> sortByDesc(String column);

    /**
     * Gets all entities from database with pagination.
     *
     * @param page the desired page
     * @return a new IModelCollection as result
     */
    IModelCollection<T> page(int page);
}
