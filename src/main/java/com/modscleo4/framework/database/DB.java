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

package com.modscleo4.framework.database;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Default DB connections class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class DB {
    /**
     * Gets a default Connection for the framework.
     *
     * @return a default Connection for the framework.
     */
    public static Connection getDefault() {
        Dotenv dotenv = Dotenv.load();

        Drivers driver = Driver.fromName(dotenv.get("DB_DRIVER", "postgresql"));
        String db = dotenv.get("DB_DATABASE", "java");

        if (driver == Drivers.SQLITE) {
            return new Connection(db);
        } else {
            String host = dotenv.get("DB_HOST", "127.0.0.1");
            String port = dotenv.get("DB_PORT", "5432");
            String username = dotenv.get("DB_USERNAME", "postgres");
            String password = dotenv.get("DB_PASSWORD", "");

            return new Connection(driver, host, port, db, username, password);
        }
    }
}
