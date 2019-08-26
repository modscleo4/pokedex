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

package app.ui;

import app.entity.Ability;
import app.entity.Category;
import app.entity.Gender;
import app.entity.Pokemon;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.ui.ExceptionDialog;
import com.modscleo4.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Pokemon details window class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class PokemonWindow extends Window implements ActionListener {
    private Pokemon pokemon;

    private Font labelFont = new Font("Sans-Serif", Font.BOLD, 12);
    private Font infoFont = new Font("Sans-Serif", Font.PLAIN, 12);

    private JPanel panelMain;

    private JLabel lName;
    private JLabel lId;

    private JLabel lImage;
    private JLabel lDescription;
    private JLabel lHeight;
    private JLabel lCategory;
    private JLabel lWeight;
    private JLabel lGender;
    private JLabel lAbility;
    private JLabel lTemp;
    private JPanel tempPanel;

    public PokemonWindow(Pokemon pokemon) {
        if (pokemon != null) {
            this.pokemon = pokemon;

            this.setTitle(pokemon.getName());
            this.setSize(600, 650);
            this.setMinimumSize(new Dimension(600, 650));
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setLayout(new BorderLayout());

            GridBagLayout mainLayout = new GridBagLayout();
            panelMain = new JPanel(mainLayout);
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            this.add(panelMain, BorderLayout.NORTH);

            addTopPanel(constraints);

            addBottomPanel(constraints);
        }
    }

    private void addTopPanel(GridBagConstraints constraints) {
        FlowLayout topLayout = new FlowLayout(FlowLayout.CENTER, 5, 5);
        JPanel top = new JPanel(topLayout);
        panelMain.add(top, constraints);

        lName = new JLabel(String.format("%s", pokemon.getName()));
        lName.setFont(labelFont);
        lTemp = new JLabel("|");
        lTemp.setFont(infoFont);
        lId = new JLabel(String.format("Nº %03d", pokemon.getId()));
        lId.setFont(infoFont);
        top.add(lName);
        top.add(lTemp);
        top.add(lId);
    }

    private void addBottomPanel(GridBagConstraints constraints) {
        JPanel bottom = new JPanel();
        BoxLayout bottomLayout = new BoxLayout(bottom, BoxLayout.Y_AXIS);
        bottom.setLayout(bottomLayout);
        panelMain.add(bottom, constraints);

        FlowLayout imageLayout = new FlowLayout(FlowLayout.CENTER, 5, 0);
        JPanel img = new JPanel(imageLayout);
        img.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 0));
        bottom.add(img);

        try {
            ImageIcon icon = new ImageIcon(pokemon.getImage());
            Image image = icon.getImage();
            image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            lImage = new JLabel(new ImageIcon(image));
            lImage.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            img.add(lImage);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        GridLayout descLayout = new GridLayout(2, 1);
        JPanel desc = new JPanel(descLayout);
        img.add(desc);

        lDescription = new JLabel(String.format("<html>%s</html>", pokemon.getDescription().replace("\\n", "<br />")));
        lDescription.setFont(infoFont);
        desc.add(lDescription);

        GridLayout infoLayout = new GridLayout(3, 2, 5, 5);
        JPanel info = new JPanel(infoLayout);
        info.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        desc.add(info);

        lTemp = new JLabel("Height");
        lTemp.setFont(labelFont);
        lHeight = new JLabel(String.format("%.1f m", pokemon.getHeight()));
        lHeight.setFont(infoFont);

        tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(2, 1));
        tempPanel.add(lTemp);
        tempPanel.add(lHeight);
        info.add(tempPanel);

        try {
            Category category = pokemon.category();

            lTemp = new JLabel("Category");
            lTemp.setFont(labelFont);
            lCategory = new JLabel(String.format("%s", category.getName()));
            lCategory.setFont(infoFont);

            tempPanel = new JPanel();
            tempPanel.setLayout(new GridLayout(2, 1));
            tempPanel.add(lTemp);
            tempPanel.add(lCategory);
            info.add(tempPanel);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        lTemp = new JLabel("Weight");
        lTemp.setFont(labelFont);
        lWeight = new JLabel(String.format("%.1f kg", pokemon.getWeight()));
        lWeight.setFont(infoFont);

        tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(2, 1));
        tempPanel.add(lTemp);
        tempPanel.add(lWeight);
        info.add(tempPanel);

        try {
            IModelCollection<Ability> abilities = pokemon.abilities();

            GridLayout abilitiesLayout = new GridLayout(abilities.size() + 1, 1);
            tempPanel = new JPanel(abilitiesLayout);
            tempPanel.setAlignmentX(Panel.RIGHT_ALIGNMENT);
            lTemp = new JLabel("Abilities");
            lTemp.setFont(labelFont);
            tempPanel.add(lTemp);

            abilities.forEach(ability -> {
                lAbility = new JLabel(ability.getName());
                lAbility.setFont(infoFont);
                lAbility.setToolTipText(ability.getDescription());
                tempPanel.add(lAbility);
            });

            info.add(tempPanel);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        try {
            IModelCollection<Gender> genders = pokemon.genders();
            List<String> gs = new ArrayList<>();
            genders.forEach(g -> gs.add(g.getName()));

            lTemp = new JLabel("Genders");
            lTemp.setFont(labelFont);
            lGender = new JLabel(String.format("%s", String.join(", ", gs)));
            lGender.setFont(infoFont);

            tempPanel = new JPanel();
            tempPanel.setLayout(new GridLayout(2, 1));
            tempPanel.add(lTemp);
            tempPanel.add(lGender);
            info.add(tempPanel);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        bottom.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
