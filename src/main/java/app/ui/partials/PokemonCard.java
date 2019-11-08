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

package app.ui.partials;

import app.entity.Pokemon;
import app.ui.PokemonDialog;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.ui.component.JImageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.sql.SQLException;

public class PokemonCard extends JPanel {
    private Pokemon pokemon;

    private Font labelFont = new Font("Sans-Serif", Font.BOLD, 12);
    private Font infoFont = new Font("Sans-Serif", Font.PLAIN, 12);

    private JImageView lImage;
    private JLabel lName;
    private JLabel lId;

    private JPanel panelMain;

    private JPanel types;
    private JPanel tempPanel;
    private JLabel lType;

    public PokemonCard(Pokemon pokemon) throws IOException, SQLException, InvalidKeyException, ClassNotFoundException {
        this(pokemon, true, 100);
    }

    public PokemonCard(Pokemon pokemon, boolean click) throws IOException, SQLException, InvalidKeyException, ClassNotFoundException {
        this(pokemon, click, 100);
    }

    public PokemonCard(Pokemon pokemon, boolean click, int imgSize) throws IOException, SQLException, InvalidKeyException, ClassNotFoundException {
        this.pokemon = pokemon;

        this.setLayout(new BorderLayout());

        GridBagLayout mainLayout = new GridBagLayout();
        panelMain = new JPanel(mainLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        this.add(panelMain, BorderLayout.NORTH);

        if (click) {
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new PokemonDialog(pokemon).setVisible(true);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        // Image
        Image image = pokemon.getImage().getScaledInstance(imgSize, imgSize, java.awt.Image.SCALE_SMOOTH);
        lImage = new JImageView(image);
        panelMain.add(lImage, constraints);

        tempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelMain.add(tempPanel, constraints);

        // ID / Name
        lName = new JLabel(pokemon.getName());
        lName.setFont(labelFont);
        tempPanel.add(lName);

        lId = new JLabel(String.format("Nº %03d", pokemon.getId()));
        lId.setFont(infoFont);
        tempPanel.add(lId);

        // Types
        IModelCollection<app.entity.Type> pokemonTypes = pokemon.types();

        GridBagLayout typesLayout = new GridBagLayout();
        JPanel types = new JPanel(typesLayout);
        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.gridy = 0;
        constraints2.insets = new Insets(0, 0, 0, 5);
        panelMain.add(types, constraints);

        pokemonTypes.forEach(type -> {
            lType = new JLabel(String.format("%s", type.getName()));
            lType.setForeground(type.getColor());
            lType.setFont(infoFont);
            types.add(lType, constraints2);
        });
    }
}
