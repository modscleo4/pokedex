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

package com.modscleo4.http;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class HTTP {
    public static void downloadFile(String link, String file) {
        new BackgroundWorker(link, file);
    }

    public static void downloadFile(String link) {
        downloadFile(link, null);
    }

    public static class BackgroundWorker extends SwingWorker<Void, Void> {
        private String link;
        private String file;

        private JDialog dialog;
        private JLabel lFile;
        private JProgressBar progressBar;

        BackgroundWorker(String link, String file) {
            this.link = link;
            this.file = file;

            EventQueue.invokeLater(this::execute);

            addPropertyChangeListener(evt -> {
                if (evt.getPropertyName().toLowerCase().equals("progress")) {
                    progressBar.setValue(getProgress());
                }
            });

            dialog = new JDialog();
            dialog.setTitle("Downloading...");
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            dialog.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(5, 5, 5, 5);
            constraints.weightx = 1;

            constraints.gridy = 0;
            lFile = new JLabel();
            dialog.add(lFile, constraints);

            constraints.gridy = 1;
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
            dialog.add(progressBar, constraints);

            dialog.pack();

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        @Override
        protected void done() {
            dialog.dispose();
        }

        @Override
        protected Void doInBackground() throws Exception {
            if (file == null) {
                file = link.substring(link.lastIndexOf('/') + 1);
                file = String.format("%s/%s", new File(".").getCanonicalPath(), file);
            }

            lFile.setText(String.format("Downloading %s...", file.substring(file.lastIndexOf('/') + 1)));
            dialog.pack();

            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            connection.connect();
            long l = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(file);

            byte[] data = new byte[1024];
            int count;

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                setProgress((int) ((total * 100) / l));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

            return null;
        }
    }
}
