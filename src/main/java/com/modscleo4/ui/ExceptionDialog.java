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

package com.modscleo4.ui;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * ExceptionDialog class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class ExceptionDialog extends Dialog {
    private Exception exception;
    private JPanel top;
    private JPanel center;
    private JPanel bottom;
    private JLabel icon;
    private JTextPane errorMessage;
    private JScrollPane errorScrollPane;
    private JTextArea exceptionStackTrace;
    private JScrollPane exceptionScrollPane;
    private JButton bClose;

    public ExceptionDialog(Exception exception) {
        this.exception = exception;

        this.setTitle(exception.getClass().getCanonicalName());
        this.setResizable(false);
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        top = new JPanel();
        top.setLayout(null);
        top.setPreferredSize(new Dimension(480, 100));

        icon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
        icon.setLocation(new Point(20, 20));
        icon.setSize(32, 32);
        top.add(icon);

        errorMessage = new JTextPane();
        errorMessage.setFont(errorMessage.getFont().deriveFont(errorMessage.getFont().getStyle() | Font.BOLD, errorMessage.getFont().getSize() + 1));
        errorMessage.setBorder(null);
        errorMessage.setEditable(false);
        errorMessage.setBackground(null);
        errorScrollPane = new JScrollPane(errorMessage);
        errorScrollPane.setBorder(null);
        errorScrollPane.setSize(new Dimension(405, 80));
        errorScrollPane.setLocation(new Point(71, 13));
        errorMessage.setText(String.format("Unhandled exception:\n%s: %s", exception.getClass().getCanonicalName(), exception.getLocalizedMessage()));
        top.add(errorScrollPane);

        center = new JPanel();
        center.setSize(new Dimension(420, 300));
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        exceptionStackTrace = new JTextArea();
        exceptionScrollPane = new JScrollPane(exceptionStackTrace);
        exceptionScrollPane.setPreferredSize(new Dimension(470, 300));
        exceptionStackTrace.setEditable(false);
        String exceptionText = getStackTraceAsString(exception);
        exceptionStackTrace.setText(exceptionText);
        center.add(exceptionScrollPane);

        bottom = new JPanel();
        bClose = new JButton();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));

        bClose.setText("Close");
        bClose.addActionListener(event -> dispose());
        bottom.add(bClose);

        this.add(top, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
        this.pack();
        this.centerScreen();
    }

    public static void show(Exception exception) {
        new ExceptionDialog(exception).setVisible(true);
        System.exit(1);
    }

    private String getStackTraceAsString(Throwable exception) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        exception.printStackTrace(printWriter);
        return result.toString();
    }
}
