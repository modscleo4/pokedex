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

import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.database.Connection;
import com.modscleo4.framework.database.DB;
import com.modscleo4.framework.database.Table;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Model class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public abstract class Model extends Row implements IModel {
    /**
     * The Connection instance.
     */
    protected Connection connection = DB.getDefault();

    /**
     * The table name.
     */
    protected String table = "";

    /**
     * The fake table name.
     */
    protected String fakeTable = "";

    /**
     * The table primary key column name.
     */
    protected String primaryKey = "id";

    /**
     * If the primary key increments.
     */
    protected boolean incrementing = true;

    /**
     * The Table object.
     */
    private Table dbTable = null;

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public String getFakeTable() {
        return fakeTable;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Table getDatabaseTable() {
        if (dbTable == null || !dbTable.getConnection().equals(getConnection()) || !dbTable.getTableName().equals(getTable())) {
            dbTable = new Table(getConnection(), getTable());
        }

        return dbTable;
    }

    @Override
    public String getForeignKey() throws InvalidKeyException {
        if (this.getKeyName() == null) {
            throw new InvalidKeyException("There is no primary key.");
        }

        String className = this.getClass().getSimpleName().toLowerCase();
        if (!getFakeTable().equals("")) {
            className = this.getClass().getSuperclass().getSimpleName().toLowerCase();
        }

        return String.format("%s_%s", className, this.getKeyName());
    }

    @Override
    public String getKeyName() {
        return primaryKey;
    }

    @Override
    public boolean isIncrementing() {
        return incrementing;
    }

    @Override
    public IModel save() throws SQLException, ClassNotFoundException {
        Table table = getDatabaseTable();
        String pKey = getKeyName();

        if (pKey != null && this.get(pKey) != null) {
            table.update(pKey, this);
        } else {
            table.store(pKey, this);
        }

        return this;
    }

    @Override
    public void delete() throws SQLException, ClassNotFoundException, InvalidKeyException {
        Table table = getDatabaseTable();
        String pKey = getKeyName();

        if (pKey != null && this.get(pKey) != null) {
            table.delete(pKey, this);
        } else {
            throw new InvalidKeyException("There is no primary key.");
        }
    }

    @Override
    public IModel hasOne(Class<? extends IModel> className, String foreignKey, String localKey) throws InvalidKeyException, SQLException, ClassNotFoundException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignKey == null) {
                foreignKey = getForeignKey();
            }

            if (localKey == null) {
                localKey = getKeyName();
            }

            IRow row = entity.getDatabaseTable().where(foreignKey, "=", this.get(localKey)).get().first();
            if (row == null) {
                return null;
            }

            IModel model = className.getDeclaredConstructor().newInstance();
            model.fromRow(row);
            return model;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IModel hasOne(Class<? extends IModel> className, String foreignKey) throws ClassNotFoundException, InvalidKeyException, SQLException {
        return this.hasOne(className, foreignKey, null);
    }

    @Override
    public IModel hasOne(Class<? extends IModel> className) throws ClassNotFoundException, InvalidKeyException, SQLException {
        return this.hasOne(className, null, null);
    }

    @Override
    public IModelCollection<? extends IModel> hasMany(Class<? extends IModel> className, String foreignKey, String localKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignKey == null) {
                foreignKey = getForeignKey();
            }

            if (localKey == null) {
                localKey = getKeyName();
            }

            ICollection<IRow> rows = entity.getDatabaseTable().where(foreignKey, "=", this.get(localKey)).get();
            IModelCollection<IModel> related = new ModelCollection<>();
            for (IRow r : rows) {
                if (r == null) {
                    related.add(null);
                    continue;
                }

                IModel e = className.getDeclaredConstructor().newInstance();
                e.fromRow(r);
                related.add(e);
            }

            return related;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IModelCollection<? extends IModel> hasMany(Class<? extends IModel> className, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.hasMany(className, foreignKey, null);
    }

    @Override
    public IModelCollection<? extends IModel> hasMany(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.hasMany(className, null, null);
    }

    @Override
    public IModel belongsTo(Class<? extends IModel> className, String foreignKey, String ownerKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignKey == null) {
                foreignKey = entity.getForeignKey();
            }

            if (ownerKey == null) {
                ownerKey = entity.getKeyName();
            }

            IRow row = entity.getDatabaseTable().where(ownerKey, "=", this.get(foreignKey)).get().first();
            if (row == null) {
                return null;
            }

            IModel model = className.getDeclaredConstructor().newInstance();
            model.fromRow(row);
            return model;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IModel belongsTo(Class<? extends IModel> className, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsTo(className, foreignKey, null);
    }

    @Override
    public IModel belongsTo(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsTo(className, null, null);
    }

    @Override
    public IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String parentKey, String relatedKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignPivotKey == null) {
                foreignPivotKey = getForeignKey();
            }

            if (relatedPivotKey == null) {
                relatedPivotKey = entity.getForeignKey();
            }

            if (table == null) {
                String t = entity.getFakeTable();
                if (t.equals("")) {
                    t = entity.getTable();
                }

                table = String.format("%s_has_%s", getClass().getSimpleName().toLowerCase(), t);
            }

            if (parentKey == null) {
                parentKey = getKeyName();
            }

            if (relatedKey == null) {
                relatedKey = entity.getKeyName();
            }

            Table pivot = new Table(connection, table);
            ICollection<IRow> searchFor = pivot.where(foreignPivotKey, "=", this.get(parentKey)).get();
            IModelCollection<IModel> related = new ModelCollection<>();
            for (IRow s : searchFor) {
                IRow row = entity.getDatabaseTable().where(relatedKey, "=", s.get(relatedPivotKey)).get().first();
                if (row == null) {
                    related.add(null);
                    continue;
                }

                String finalForeignPivotKey = foreignPivotKey;
                String finalRelatedPivotKey = relatedPivotKey;
                s.forEach((i, v) -> {
                    if (!i.equals(finalForeignPivotKey) && !i.equals(finalRelatedPivotKey)) {
                        row.put(i, v);
                    }
                });

                IModel e = className.getDeclaredConstructor().newInstance();
                e.fromRow(row);
                related.add(e);
            }

            return related;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String parentKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, foreignPivotKey, relatedPivotKey, parentKey, null);
    }

    @Override
    public IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, foreignPivotKey, relatedPivotKey, null, null);
    }

    @Override
    public IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, foreignPivotKey, null, null, null);
    }

    @Override
    public IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, null, null, null, null);
    }

    @Override
    public IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, null, null, null, null, null);
    }

    public void sync(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String parentKey, String relatedKey) {

    }
}
