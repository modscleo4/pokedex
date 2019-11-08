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
import com.modscleo4.framework.callback.IMapCallback;
import com.modscleo4.framework.callback.IReduceCallback;

import java.util.List;

/**
 * Collection interface.
 *
 * @param <T> the type of the Collection
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public interface ICollection<T> extends List<T> {
    /**
     * Gets the first element in the collection.
     *
     * @return the first element
     */
    T first();

    /**
     * Gets the last element in the collection.
     *
     * @return the last element
     */
    T last();

    /**
     * Invokes callback function for each Collection element and a new Collection as result.
     *
     * @param callback callback function
     * @return a new Collection as result
     */
    ICollection<T> map(IMapCallback<T> callback);

    /**
     * Invokes callback function for each Collection element and a new filtered Collection as result.
     *
     * @param callback callback function
     * @return a new Collection as result
     */
    ICollection<T> filter(IFilterCallback<T> callback);

    /**
     * Invokes callback function for each Collection element and a new reduced integer as result.
     *
     * @param callback    callback function
     * @param accumulator accumulator variable
     * @return a new reduced integer as result
     */
    int reduce(IReduceCallback<T, Integer> callback, int accumulator);

    /**
     * Invokes callback function for each Collection element and a new reduced long as result.
     *
     * @param callback    callback function
     * @param accumulator accumulator variable
     * @return a new reduced long as result
     */
    long reduce(IReduceCallback<T, Long> callback, long accumulator);

    /**
     * Invokes callback function for each Collection element and a new reduced float as result.
     *
     * @param callback    callback function
     * @param accumulator accumulator variable
     * @return a new reduced float as result
     */
    float reduce(IReduceCallback<T, Float> callback, float accumulator);

    /**
     * Invokes callback function for each Collection element and a new reduced double as result.
     *
     * @param callback    callback function
     * @param accumulator accumulator variable
     * @return a new reduced double as result
     */
    double reduce(IReduceCallback<T, Double> callback, double accumulator);

    /**
     * Invokes callback function for each Collection element and a new reduced String as result.
     *
     * @param callback    callback function
     * @param accumulator accumulator variable
     * @return a new reduced String as result
     */
    String reduce(IReduceCallback<T, String> callback, String accumulator);
}
