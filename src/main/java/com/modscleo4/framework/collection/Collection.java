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

package com.modscleo4.framework.collection;

import com.modscleo4.framework.callback.IFilterCallback;
import com.modscleo4.framework.callback.IMapCallback;
import com.modscleo4.framework.callback.IReduceCallback;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Collection class.
 *
 * @param <T> the type of the Collection
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Collection<T> extends ArrayList<T> implements ICollection<T> {
    @Override
    public T first() {
        if (this.size() == 0) {
            return null;
        }

        return this.get(0);
    }

    @Override
    public T last() {
        if (this.size() == 0) {
            return null;
        }

        return this.get(this.size() - 1);
    }

    @Override
    public ICollection<T> map(IMapCallback<T> callback) {
        ICollection<T> mapped = this;

        AtomicInteger i = new AtomicInteger();
        mapped.parallelStream().forEach(row -> mapped.set(i.getAndIncrement(), callback.method(row)));

        return mapped;
    }

    @Override
    public ICollection<T> filter(IFilterCallback<T> callback) {
        ICollection<T> filtered = new Collection<>();

        AtomicInteger i = new AtomicInteger();
        this.parallelStream().forEach(row -> {
            T r = this.get(i.get());
            if (callback.method(r)) {
                filtered.add(this.get(i.getAndIncrement()));
            }
        });

        return filtered;
    }

    @Override
    public int reduce(IReduceCallback<T, Integer> callback, int accumulator) {
        for (T t : this) {
            accumulator = callback.method(accumulator, t);
        }

        return accumulator;
    }

    @Override
    public long reduce(IReduceCallback<T, Long> callback, long accumulator) {
        for (T t : this) {
            accumulator = callback.method(accumulator, t);
        }

        return accumulator;
    }

    @Override
    public float reduce(IReduceCallback<T, Float> callback, float accumulator) {
        for (T t : this) {
            accumulator = callback.method(accumulator, t);
        }

        return accumulator;
    }

    @Override
    public double reduce(IReduceCallback<T, Double> callback, double accumulator) {
        for (T t : this) {
            accumulator = callback.method(accumulator, t);
        }

        return accumulator;
    }

    @Override
    public String reduce(IReduceCallback<T, String> callback, String accumulator) {
        for (T t : this) {
            accumulator = callback.method(accumulator, t);
        }

        return accumulator;
    }
}
