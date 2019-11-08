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

package app.ui;

import app.dao.AppDAO;
import com.modscleo4.ui.Dialog;
import com.modscleo4.ui.ExceptionDialog;
import com.modscleo4.ui.component.JImageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class AboutDialog extends Dialog implements ActionListener {
    private Font labelFont = new Font("Sans-Serif", Font.BOLD, 16);
    private Font infoFont = new Font("Sans-Serif", Font.PLAIN, 12);

    private JPanel panelMain;

    private JImageView lImage;
    private JLabel lAppName;
    private JLabel lAppVersion;
    private JLabel lAppDesc;
    private JLabel lCopyright;
    private JButton bClose;

    public AboutDialog(Component relativeTo) {
        super("Sobre", relativeTo);

        this.setSize(640, 480);
        this.setMinimumSize(new Dimension(640, 480));
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        GridBagLayout mainLayout = new GridBagLayout();
        panelMain = new JPanel(mainLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(50, 0, 50, 0);
        this.add(panelMain, BorderLayout.NORTH);

        this.addTopPanel(constraints);
        constraints.gridy = 1;
        this.addBottomPanel(constraints);
    }

    public AboutDialog() {
        this(null);
    }

    private void addTopPanel(GridBagConstraints constraints) {
        JPanel top = new JPanel();
        BoxLayout topLayout = new BoxLayout(top, BoxLayout.Y_AXIS);
        top.setLayout(topLayout);
        panelMain.add(top, constraints);

        try {
            JPanel panel1 = new JPanel();
            BoxLayout panel1Layout = new BoxLayout(panel1, BoxLayout.X_AXIS);
            panel1.setLayout(panel1Layout);
            panel1.setAlignmentY(JPanel.TOP_ALIGNMENT);
            //FlowLayout panel1Layout = new FlowLayout(FlowLayout.CENTER, 10, 0);
            //JPanel panel1 = new JPanel(panel1Layout);
            panel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            top.add(panel1);

            Image image = AppDAO.pokemonDAO.find(4).getImage().getScaledInstance(175, 175, java.awt.Image.SCALE_SMOOTH);
            lImage = new JImageView(image);
            panel1.add(lImage);

            JPanel desc = new JPanel();
            BoxLayout descLayout = new BoxLayout(desc, BoxLayout.Y_AXIS);
            desc.setLayout(descLayout);
            desc.setAlignmentY(JPanel.TOP_ALIGNMENT);
            panel1.add(desc, constraints);

            lAppName = new JLabel("Pokédex");
            lAppName.setFont(labelFont);
            desc.add(lAppName);

            lAppVersion = new JLabel("Versão 0.1a");
            lAppVersion.setFont(infoFont);
            desc.add(lAppVersion);

            lAppDesc = new JLabel("Visualize informações de qualquer Pokémon");
            lAppDesc.setFont(infoFont);
            desc.add(lAppDesc);
        } catch (Exception e) {
            ExceptionDialog.show(e);
        }
    }

    private void addBottomPanel(GridBagConstraints constraints) {
        JPanel bottom = new JPanel();
        BoxLayout bottomLayout = new BoxLayout(bottom, BoxLayout.Y_AXIS);
        bottom.setLayout(bottomLayout);
        panelMain.add(bottom, constraints);

        lCopyright = new JLabel("Copyright © 2019 Dhiego Cassiano Fogaça Barbosa");
        lCopyright.setFont(infoFont);
        lCopyright.setAlignmentX(Component.CENTER_ALIGNMENT);
        lCopyright.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bottom.add(lCopyright);

        bClose = new JButton("Fechar");
        bClose.addActionListener(this);
        bClose.setAlignmentX(Component.CENTER_ALIGNMENT);
        bClose.setMargin(new Insets(5,10,5,10));
        bottom.add(bClose);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bClose) {
            this.dispose();
        }
    }
}
