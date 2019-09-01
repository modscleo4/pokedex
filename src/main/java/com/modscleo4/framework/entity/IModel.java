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
import com.modscleo4.framework.database.Connection;
import com.modscleo4.framework.database.Table;

import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Model class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public interface IModel extends IRow {
    /**
     * Gets the table name.
     *
     * @return the table name
     */
    String getTable();

    /**
     * Gets the fake table name.
     *
     * @return the fake table name
     */
    String getFakeTable();

    /**
     * Gets the Connection object.
     *
     * @return the Connection object of the class
     */
    Connection getConnection();

    /**
     * Gets the Table object.
     *
     * @return the Table object of the class
     */
    Table getDatabaseTable();

    /**
     * Gets what the foreign key of the class could be (table_id).
     *
     * @return the possible foreign key
     * @throws InvalidKeyException if
     */
    String getForeignKey() throws InvalidKeyException;

    /**
     * Gets the primary key of the class (if exists).
     *
     * @return the primary key of the class (null if not)
     */
    String getKeyName();

    /**
     * Checks if the table has primary key auto incrementing.
     *
     * @return true if the table has primary key auto incrementing
     */
    boolean isIncrementing();

    /**
     * Save the model in database.
     *
     * @return true if saved
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     */
    IModel save() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException;

    /**
     * Runs hasOne relation between the entities.
     *
     * @param className  the class of related entity
     * @param foreignKey the foreign key column name
     * @param localKey   the local key column name
     * @return the matching hasOne entity
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     */
    IModel hasOne(Class<? extends IModel> className, String foreignKey, String localKey) throws InvalidKeyException, SQLException, ClassNotFoundException;

    /**
     * Runs hasOne relation between the entities.
     *
     * @param className  the class of related entity
     * @param foreignKey the foreign key column name
     * @return the matching hasOne entity
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     */
    IModel hasOne(Class<? extends IModel> className, String foreignKey) throws ClassNotFoundException, InvalidKeyException, SQLException;

    /**
     * Runs hasOne relation between the entities.
     *
     * @param className the class of related entity
     * @return the matching hasOne entity
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     */
    IModel hasOne(Class<? extends IModel> className) throws ClassNotFoundException, InvalidKeyException, SQLException;

    /**
     * Runs hasMany relation between the entities.
     *
     * @param className  the class of related entity
     * @param foreignKey the foreign key column name
     * @param localKey   the local key column name
     * @return the matching hasMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> hasMany(Class<? extends IModel> className, String foreignKey, String localKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs hasMany relation between the entities.
     *
     * @param className  the class of related entity
     * @param foreignKey the foreign key column name
     * @return the matching hasMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> hasMany(Class<? extends IModel> className, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs hasMany relation between the entities.
     *
     * @param className the class of related entity
     * @return the matching hasMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> hasMany(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsTo relation between the entities.
     *
     * @param className  the class of related entity
     * @param foreignKey the foreign key column name
     * @param ownerKey   belongsTo entity
     * @return the matching
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModel belongsTo(Class<? extends IModel> className, String foreignKey, String ownerKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsTo relation between the entities.
     *
     * @param className  the class of related entity
     * @param foreignKey the foreign key column name
     * @return the matching belongsTo entity
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModel belongsTo(Class<? extends IModel> className, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsTo relation between the entities.
     *
     * @param className the class of related entity
     * @return the matching belongsTo entity
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModel belongsTo(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsToMany relation between the entities.
     *
     * @param className       the class of related entity
     * @param table           the pivot table name
     * @param foreignPivotKey the foreign key column name in pivot table
     * @param relatedPivotKey the related key column name in pivot table
     * @param foreignKey      the foreign key column name
     * @param ownerKey        the owner key column name
     * @return the matching belongsToMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String foreignKey, String ownerKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsToMany relation between the entities.
     *
     * @param className       the class of related entity
     * @param table           the pivot table name
     * @param foreignPivotKey the foreign key column name in pivot table
     * @param relatedPivotKey the related key column name in pivot table
     * @param foreignKey      the foreign key column name
     * @return the matching belongsToMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey, String foreignKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsToMany relation between the entities.
     *
     * @param className       the class of related entity
     * @param table           the pivot table name
     * @param foreignPivotKey the foreign key column name in pivot table
     * @param relatedPivotKey the related key column name in pivot table
     * @return the matching belongsToMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey, String relatedPivotKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsToMany relation between the entities.
     *
     * @param className       the class of related entity
     * @param table           the pivot table name
     * @param foreignPivotKey the foreign key column name in pivot table
     * @return the matching belongsToMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table, String foreignPivotKey) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsToMany relation between the entities.
     *
     * @param className the class of related entity
     * @param table     the pivot table name
     * @return the matching belongsToMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className, String table) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;

    /**
     * Runs belongsToMany relation between the entities.
     *
     * @param className the class of related entity
     * @return the matching belongsToMany entities
     * @throws IllegalArgumentException if the related entity could not be instantiated
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws InvalidKeyException      if the primary key could not be obtained
     */
    IModelCollection<? extends IModel> belongsToMany(Class<? extends IModel> className) throws IllegalArgumentException, SQLException, ClassNotFoundException, InvalidKeyException;
}
