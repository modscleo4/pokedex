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

import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.IRowCollection;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.collection.RowCollection;
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
     * The Table object.
     */
    private Table dbTable;

    /**
     * The table name.
     */
    protected static String table = "";

    /**
     * The table primary key column name.
     */
    protected static String primaryKey = "id";

    /**
     * If the primary key increments.
     */
    protected static boolean incrementing = true;

    /**
     * Creates a new Model instance.
     */
    public Model() {
        super();
        dbTable = new Table(DB.getConnection(), table);
    }

    /**
     * Creates a new Model instance.
     *
     * @param row the IRow to copy data from
     */
    public Model(IRow row) {
        super(row);
        dbTable = new Table(DB.getConnection(), table);
    }

    @Override
    public String getTable() {
        return table;
    }

    @Override
    public Table getDatabaseTable() {
        return dbTable;
    }

    @Override
    public String getForeignKey() throws InvalidKeyException {
        if (this.getKeyName() == null) {
            throw new InvalidKeyException("There is no primary key.");
        }

        return String.format("%s_%s", this.getClass().getSimpleName().toLowerCase(), this.getKeyName());
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

        if (pKey != null && get(pKey) != null) {
            // Update
            table.update(pKey, this);
        } else {
            // Save
            table.store(pKey, this);
        }

        return this;
    }

    @Override
    public IModel hasOne(Class<? extends IModel> className, String foreignKey, String localKey) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignKey == null) {
                foreignKey = getTable();
            }

            if (localKey == null) {
                localKey = getKeyName();
            }

            IRow row = entity.getDatabaseTable().where(foreignKey, "=", this.get(localKey)).first();
            if (row == null) {
                return null;
            }

            return className.getDeclaredConstructor(IRow.class).newInstance(row);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IModel hasOne(Class<? extends IModel> className, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        return this.hasOne(className, foreignKey, null);
    }

    @Override
    public IModel hasOne(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        return this.hasOne(className, null, null);
    }

    @Override
    public IRowCollection hasMany(Class<? extends IModel> className, String foreignKey, String localKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignKey == null) {
                foreignKey = getForeignKey();
            }

            if (localKey == null) {
                localKey = getKeyName();
            }

            IRowCollection rows = entity.getDatabaseTable().where(foreignKey, "=", this.get(localKey));
            IRowCollection related = new RowCollection();
            for (IRow r : rows) {
                if (r == null) {
                    related.add(null);
                    continue;
                }

                IModel e = className.getDeclaredConstructor(IRow.class).newInstance(r);
                related.add(e);
            }

            return related;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IRowCollection hasMany(Class<? extends IModel> className, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.hasMany(className, foreignKey, null);
    }

    @Override
    public IRowCollection hasMany(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
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

            IRow row = entity.getDatabaseTable().where(ownerKey, "=", this.get(foreignKey)).first();
            if (row == null) {
                return null;
            }

            return className.getDeclaredConstructor(IRow.class).newInstance(row);
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
    public IRowCollection belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String parentKey, String relatedKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        try {
            IModel entity = className.getDeclaredConstructor().newInstance();

            if (foreignPivotKey == null) {
                foreignPivotKey = getForeignKey();
            }

            if (relatedPivotKey == null) {
                relatedPivotKey = entity.getForeignKey();
            }

            if (table == null) {
                table = String.format("%s_has_%s", getClass().getSimpleName(), entity.getTable());
            }

            if (parentKey == null) {
                parentKey = getKeyName();
            }

            if (relatedKey == null) {
                relatedKey = entity.getKeyName();
            }

            Table pivot = new Table(DB.getConnection(), table);
            IRowCollection searchFor = pivot.where(foreignPivotKey, "=", this.get(parentKey));
            IRowCollection related = new RowCollection();
            for (IRow s : searchFor) {
                IRow row = entity.getDatabaseTable().where(relatedKey, "=", s.get(relatedPivotKey)).first();
                if (row == null) {
                    related.add(null);
                    continue;
                }

                IModel e = className.getDeclaredConstructor(IRow.class).newInstance(row);
                related.add(e);
            }

            return related;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("The entity class could not be instantiated.");
        }
    }

    @Override
    public IRowCollection belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String parentKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, foreignPivotKey, relatedPivotKey, parentKey, null);
    }

    @Override
    public IRowCollection belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, foreignPivotKey, relatedPivotKey, null, null);
    }

    @Override
    public IRowCollection belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, foreignPivotKey, null, null, null);
    }

    @Override
    public IRowCollection belongsToMany(Class<? extends IModel> className, String table) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, table, null, null, null, null);
    }

    @Override
    public IRowCollection belongsToMany(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException {
        return this.belongsToMany(className, null, null, null, null, null);
    }
}
