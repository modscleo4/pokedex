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

import app.entity.*;
import app.ui.partials.PokemonCard;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.framework.entity.ModelCollection;
import com.modscleo4.ui.Dialog;
import com.modscleo4.ui.ExceptionDialog;

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
public class PokemonDialog extends Dialog implements ActionListener {
    private Pokemon pokemon;

    private Font labelFont = new Font("Sans-Serif", Font.BOLD, 12);
    private Font infoFont = new Font("Sans-Serif", Font.PLAIN, 12);

    private JPanel panelMain;

    private JLabel lTemp;
    private JPanel tempPanel;

    private JLabel lName;
    private JLabel lId;

    private JLabel lImage;
    private JLabel lDescription;
    private JLabel lHeight;
    private JLabel lCategory;
    private JLabel lWeight;
    private JLabel lGender;
    private JLabel lAbility;

    private JLabel lHP;
    private JLabel lAttack;
    private JLabel lDefense;
    private JLabel lSpecialAttack;
    private JLabel lSpecialDefense;
    private JLabel lSpeed;

    private JLabel lType;
    private JLabel lWeakness;

    public PokemonDialog(Pokemon pokemon) {
        if (pokemon != null) {
            this.pokemon = pokemon;

            this.setTitle(pokemon.getName());
            this.setSize(625, 650);
            this.setMinimumSize(new Dimension(625, 650));
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.setLayout(new BorderLayout());
            this.setModal(true);

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

        bottom.add(this.panel1());
        bottom.add(this.panel2());
        bottom.add(this.panel3());
    }

    private JPanel panel1() {
        FlowLayout panel1Layout = new FlowLayout(FlowLayout.CENTER, 10, 0);
        JPanel panel1 = new JPanel(panel1Layout);
        panel1.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 0));

        try {
            ImageIcon icon = new ImageIcon(pokemon.getImage());
            Image image = icon.getImage().getScaledInstance(175, 175, java.awt.Image.SCALE_SMOOTH);
            lImage = new JLabel(new ImageIcon(image));
            lImage.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            panel1.add(lImage);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        GridLayout descLayout = new GridLayout(2, 1);
        JPanel desc = new JPanel(descLayout);
        panel1.add(desc);

        lDescription = new JLabel(String.format("<html>%s</html>", pokemon.getDescription().replace("\\n", "<br />")));
        lDescription.setFont(infoFont);
        desc.add(lDescription);

        GridBagLayout infoLayout = new GridBagLayout();
        GridBagConstraints infoConstraints = new GridBagConstraints();
        infoConstraints.gridx = 0;
        infoConstraints.gridy = 0;
        infoConstraints.anchor = GridBagConstraints.WEST;
        infoConstraints.insets = new Insets(0, 0, 5, 100);
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
        info.add(tempPanel, infoConstraints);

        try {
            Category category = pokemon.category();

            lTemp = new JLabel("Category");
            lTemp.setFont(labelFont);
            lCategory = new JLabel(String.format("%s", category.getName()));
            lCategory.setFont(infoFont);

            infoConstraints.gridx = 1;
            infoConstraints.gridy = 0;

            tempPanel = new JPanel();
            tempPanel.setLayout(new GridLayout(2, 1));
            tempPanel.add(lTemp);
            tempPanel.add(lCategory);
            info.add(tempPanel, infoConstraints);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        lTemp = new JLabel("Weight");
        lTemp.setFont(labelFont);
        lWeight = new JLabel(String.format("%.1f kg", pokemon.getWeight()));
        lWeight.setFont(infoFont);

        infoConstraints.gridx = 0;
        infoConstraints.gridy = 1;

        tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(2, 1));
        tempPanel.add(lTemp);
        tempPanel.add(lWeight);
        info.add(tempPanel, infoConstraints);

        try {
            IModelCollection<Ability> abilities = pokemon.abilities();

            infoConstraints.gridx = 1;
            infoConstraints.gridy = 1;

            GridLayout abilitiesLayout = new GridLayout(0, 1);
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

            info.add(tempPanel, infoConstraints);
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

            infoConstraints.gridx = 0;
            infoConstraints.gridy = 2;

            tempPanel = new JPanel();
            tempPanel.setLayout(new GridLayout(2, 1));
            tempPanel.add(lTemp);
            tempPanel.add(lGender);
            info.add(tempPanel, infoConstraints);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        return panel1;
    }

    public JPanel panel2() {
        JPanel panel2 = new JPanel();
        BoxLayout panel2Layout = new BoxLayout(panel2, BoxLayout.X_AXIS);
        panel2.setLayout(panel2Layout);
        panel2.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        try {
            Stats pokemonStats = pokemon.stats();

            GridLayout statsLayout = new GridLayout(1, 6, 0, 0);
            JPanel stats = new JPanel(statsLayout);
            stats.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            panel2.add(stats);

            // HP
            lTemp = new JLabel("HP");
            lTemp.setFont(labelFont);
            lTemp.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
            lHP = new JLabel(String.format("%d", pokemonStats.getHP()));
            lHP.setFont(infoFont);

            tempPanel = new JPanel();
            BoxLayout tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
            tempPanel.add(lTemp);
            tempPanel.add(new JLabel(" "));
            tempPanel.add(new JLabel(" "));
            tempPanel.add(lHP);
            stats.add(tempPanel);

            // Attack
            lTemp = new JLabel("Attack");
            lTemp.setFont(labelFont);
            lTemp.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
            lAttack = new JLabel(String.format("%d", pokemonStats.getAttack()));
            lAttack.setFont(infoFont);

            tempPanel = new JPanel();
            tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
            tempPanel.add(lTemp);
            tempPanel.add(new JLabel(" "));
            tempPanel.add(new JLabel(" "));
            tempPanel.add(lAttack);
            stats.add(tempPanel);

            // Defense
            lTemp = new JLabel("Defense");
            lTemp.setFont(labelFont);
            lTemp.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
            lDefense = new JLabel(String.format("%d", pokemonStats.getDefense()));
            lDefense.setFont(infoFont);

            tempPanel = new JPanel();
            tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
            tempPanel.add(lTemp);
            tempPanel.add(new JLabel(" "));
            tempPanel.add(new JLabel(" "));
            tempPanel.add(lDefense);
            stats.add(tempPanel);

            // Special attack
            lTemp = new JLabel("<html><body style='text-align:center'>Special<br />attack</body></html>");
            lTemp.setFont(labelFont);
            lSpecialAttack = new JLabel(String.format("%d", pokemonStats.getSpecialAttack()));
            lSpecialAttack.setFont(infoFont);

            tempPanel = new JPanel();
            tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
            tempPanel.add(lTemp);
            tempPanel.add(new JLabel(" "));
            tempPanel.add(lSpecialAttack);
            stats.add(tempPanel);

            // Special defense
            lTemp = new JLabel("<html><body style='text-align:center'>Special<br />defense</body></html>");
            lTemp.setFont(labelFont);
            lSpecialDefense = new JLabel(String.format("%d", pokemonStats.getSpecialDefense()));
            lSpecialDefense.setFont(infoFont);

            tempPanel = new JPanel();
            tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
            tempPanel.add(lTemp);
            tempPanel.add(new JLabel(" "));
            tempPanel.add(lSpecialDefense);
            stats.add(tempPanel);

            // Speed
            lTemp = new JLabel("Speed");
            lTemp.setFont(labelFont);
            lTemp.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
            lSpeed = new JLabel(String.format("%d", pokemonStats.getSpeed()));
            lSpeed.setFont(infoFont);

            tempPanel = new JPanel();
            tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.add(lTemp);
            tempPanel.add(new JLabel(" "));
            tempPanel.add(new JLabel(" "));
            tempPanel.add(lSpeed);
            stats.add(tempPanel);
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        try {
            IModelCollection<app.entity.Type> pokemonTypes = pokemon.types();
            IModelCollection<Weakness> pokemonWeaknesses = pokemon.weaknesses().sortByDesc("effectiveness");

            tempPanel = new JPanel();
            BoxLayout tempLayout = new BoxLayout(tempPanel, BoxLayout.Y_AXIS);
            tempPanel.setLayout(tempLayout);
            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            panel2.add(tempPanel);

            lTemp = new JLabel("Types");
            lTemp.setFont(labelFont);
            lTemp.setAlignmentX(Component.LEFT_ALIGNMENT);
            tempPanel.add(lTemp);

            GridLayout typesLayout = new GridLayout(0, 3, 5, 5);
            JPanel types = new JPanel(typesLayout);
            types.setAlignmentX(Component.LEFT_ALIGNMENT);
            types.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
            tempPanel.add(types);

            pokemonTypes.forEach(type -> {
                lType = new JLabel(String.format("%s", type.getName()));
                lType.setForeground(type.getColor());
                lType.setFont(infoFont);
                types.add(lType);
            });

            lTemp = new JLabel("Weaknesses");
            lTemp.setFont(labelFont);
            lTemp.setAlignmentX(Component.LEFT_ALIGNMENT);
            tempPanel.add(lTemp);

            GridLayout weaknessesLayout = new GridLayout(0, 3, 5, 5);
            JPanel weaknesses = new JPanel(weaknessesLayout);
            weaknesses.setAlignmentX(Component.LEFT_ALIGNMENT);
            weaknesses.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            tempPanel.add(weaknesses);

            pokemonWeaknesses.forEach(type -> {
                lWeakness = new JLabel(String.format("%s", type.getName()));
                lWeakness.setForeground(type.getColor());
                String eff = "";
                int effectiveness = type.getEffectiveness();
                switch (effectiveness) {
                    case 0:
                        eff = "0";
                        break;
                    case 1:
                        eff = "⅛";
                        break;
                    case 2:
                        eff = "¼";
                        break;
                    case 3:
                        eff = "½";
                        break;
                    case 5:
                        eff = "2";
                        break;
                    case 6:
                        eff = "4";
                        break;
                }

                lWeakness.setToolTipText(String.format("Effectiveness: %s", eff));
                lWeakness.setFont(infoFont);
                weaknesses.add(lWeakness);
            });
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        return panel2;
    }

    public JPanel panel3() {
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        IModelCollection<Pokemon> evolutions = new ModelCollection<>();

        try {
            Pokemon p = this.pokemon;
            while (p.predecessor() != null) {
                p = p.predecessor();
            }

            evolutions.add(p);
            while (p.successor() != null) {
                p = p.successor();
                evolutions.add(p);
            }

            for (Pokemon evolution : evolutions) {
                panel3.add(new PokemonCard(evolution, false));
            }
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        return panel3;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
