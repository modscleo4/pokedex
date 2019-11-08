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

package com.modscleo4.framework.dao;

import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;
import com.modscleo4.framework.database.Table;
import com.modscleo4.framework.entity.IModel;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.ModelCollection;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Entity DAO class.
 *
 * @param <T> the entity class
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class EntityDAO<T extends IModel> implements IEntityDAO<T> {
    /**
     * The entity of the DAO
     */
    private IModel entity;

    /**
     * Creates a new Entity DAO instance
     *
     * @param model the entity class for the DAO
     * @throws IllegalArgumentException if the entity class could not be instantiated
     */
    public EntityDAO(Class<? extends IModel> model) throws IllegalArgumentException {
        try {
            this.entity = model.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public String getTable() {
        return entity.getTable();
    }

    @Override
    public String getFakeTable() {
        return entity.getFakeTable();
    }

    @Override
    public Table getDatabaseTable() {
        return entity.getDatabaseTable();
    }

    @Override
    public String getForeignKey() throws InvalidKeyException {
        return entity.getForeignKey();
    }

    @Override
    public String getKeyName() {
        return entity.getKeyName();
    }

    @Override
    public boolean isIncrementing() {
        return entity.isIncrementing();
    }

    @Override
    public Class<? extends IModel> getModelClass() {
        return this.entity.getClass();
    }

    @Override
    public IModelCollection<T> all() throws IllegalArgumentException, SQLException, ClassNotFoundException {
        IRowCollection rc = getDatabaseTable().select();
        IModelCollection<T> models = new ModelCollection<>();
        rc.forEach(row -> {
            try {
                IModel model = this.getModelClass().getDeclaredConstructor().newInstance();
                model.fromRow(row);
                models.add((T) model);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                throw new IllegalArgumentException("The entity class could not be instantiated.");
            }
        });

        return models;
    }

    @Override
    public IModelCollection<T> page(int page) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        IRowCollection rc = getDatabaseTable().selectPaginated(page);
        IModelCollection<T> models = new ModelCollection<>();
        rc.forEach(row -> {
            try {
                IModel model = this.getModelClass().getDeclaredConstructor().newInstance();
                model.fromRow(row);
                models.add((T) model);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                throw new IllegalArgumentException("The entity class could not be instantiated.");
            }
        });

        return models;
    }

    @Override
    public T find(Object id) throws NoSuchFieldException, IllegalArgumentException, SQLException, ClassNotFoundException {
        if (getKeyName() == null) {
            throw new NoSuchFieldException("There is no primary key set.");
        }

        IRow row = getDatabaseTable().find(this.getKeyName(), id);

        try {
            IModel model = this.getModelClass().getDeclaredConstructor().newInstance();
            model.fromRow(row);
            return (T) model;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IModelCollection<T> where(String column, String comparator, Object value) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        IRowCollection rc = getDatabaseTable().where(column, comparator, value);
        IModelCollection<T> models = new ModelCollection<>();
        rc.forEach(row -> {
            try {
                IModel model = this.getModelClass().getDeclaredConstructor().newInstance();
                model.fromRow(row);
                models.add((T) model);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new IllegalArgumentException("The entity class could not be instantiated.");
            }
        });

        return models;
    }
}
