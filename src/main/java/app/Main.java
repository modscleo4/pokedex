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

package app;

import app.database.Connections;
import app.ui.MainWindow;
import com.modscleo4.ui.ExceptionDialog;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main class of project.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Main {
    public static void main(String[] args) {
        try {
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> ExceptionDialog.show(e));

            Connections.envdir = "./";

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            if (!new File(".env").exists()) {
                throw new FileNotFoundException(".env not found.");
            }

            new MainWindow().setVisible(true);
        } catch (Exception e) {
            ExceptionDialog.show(e);
        }
    }
}
