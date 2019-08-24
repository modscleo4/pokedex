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

package com.modscleo4.framework.dao;

import com.modscleo4.framework.database.Table;
import com.modscleo4.framework.entity.IModel;
import com.modscleo4.framework.entity.IModelCollection;

import java.security.InvalidKeyException;
import java.sql.SQLException;

/**
 * Entity DAO interface.
 *
 * @param <T> the entity class
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public interface IEntityDAO<T extends IModel> {
    /**
     * Gets the table name.
     *
     * @return the table name
     */
    String getTable();

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
     * Gets the entity class of the DAO.
     *
     * @return the entity class of the DAO
     */
    Class<? extends IModel> getModelClass();

    /**
     * Gets all entities from database.
     *
     * @return all entities from database
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws IllegalArgumentException if the entity could not be instantiated
     */
    IModelCollection<T> all() throws IllegalArgumentException, SQLException, ClassNotFoundException;

    /**
     * Searches for some entity by primary key in database.
     *
     * @param id the primary key value
     * @return the found entity (null if not found)
     * @throws SQLException           if some DB error occurred
     * @throws ClassNotFoundException if the connection could not be opened
     * @throws NoSuchFieldException   if the primary key is not set
     */
    T find(Object id) throws NoSuchFieldException, IllegalArgumentException, SQLException, ClassNotFoundException;

    /**
     * Searches for all entities in database with some condition.
     *
     * @param column     the column to compare
     * @param comparator the comparator (<, <=, >, >=, =, <>)
     * @param value      the value to compare
     * @return all entities that match the condition
     * @throws SQLException             if some DB error occurred
     * @throws ClassNotFoundException   if the connection could not be opened
     * @throws IllegalArgumentException if the entity could not be instantiated
     */
    IModelCollection<T> where(String column, String comparator, Object value) throws IllegalArgumentException, SQLException, ClassNotFoundException;
}
