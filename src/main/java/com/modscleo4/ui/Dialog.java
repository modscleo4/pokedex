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

package com.modscleo4.ui;

import com.modscleo4.ui.xml.XMLUI;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;

/**
 * Base Dialog class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class Dialog extends JDialog {
    private Component relativeTo = null;

    public Dialog(String title, Component relativeTo) {
        if (title != null) {
            this.setTitle(title);
        }

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(relativeTo);
        this.setModal(true);
    }

    public Dialog(String title) {
        this(title, null);
    }

    public Dialog() {
        this(null);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);

        this.setLocationRelativeTo(this.relativeTo);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);

        this.setLocationRelativeTo(this.relativeTo);
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);

        this.setLocationRelativeTo(this.relativeTo);
    }

    protected void setContentView(String xml) throws IOException, SAXException, ParserConfigurationException {
        XMLUI.dialogFromResource(xml, this);
    }

    protected void centerScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        setLocation(centerPosX, centerPosY);
    }
}
