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

import app.entity.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pokemon details window class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class PokemonWindow extends Window implements ActionListener {
    private JLabel lTitle;

    private JLabel lImage;
    private JLabel lDescription;
    private JLabel lHeight;
    private JLabel lCategory;
    private JLabel lWeight;

    public PokemonWindow(Pokemon pokemon) {
        super();

        if (pokemon != null) {
            BoxLayout mainLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);

            setLayout(mainLayout);
            setTitle(pokemon.getName());

            setSize(800, 600);
            setMinimumSize(new Dimension(400, 600));
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            FlowLayout topLayout = new FlowLayout(FlowLayout.CENTER, 10, 10);
            JPanel top = new JPanel(topLayout);
            add(top);

            lTitle = new JLabel(String.format("%s | Nº %03d", pokemon.getName(), pokemon.getId()));
            top.add(lTitle);


            GridLayout bottomLayout = new GridLayout(3, 1, 10, 10);
            JPanel bottom = new JPanel(bottomLayout);
            add(bottom);

            GridLayout imageLayout = new GridLayout(1, 2);
            JPanel img = new JPanel(imageLayout);
            bottom.add(img);
            /*JPanel img = new JPanel();
            BoxLayout imageLayout = new BoxLayout(img, BoxLayout.X_AXIS);
            img.setLayout(imageLayout);
            bottom.add(img);*/

            try {
                ImageIcon icon = new ImageIcon(pokemon.getImage());
                Image image = icon.getImage();
                image = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                lImage = new JLabel(new ImageIcon(image));
                lImage.setHorizontalAlignment(JLabel.LEFT);
                img.add(lImage);

                lCategory = new JLabel(pokemon.category().getName());
            } /* Ash */ catch/*um*/ (Exception e) {
                dispose();
            }

            GridLayout descLayout = new GridLayout(2, 1);
            JPanel desc = new JPanel(descLayout);
            desc.setAlignmentX(Panel.RIGHT_ALIGNMENT);
            img.add(desc);

            lDescription = new JLabel(pokemon.getDescription());
            desc.add(lDescription);

            GridLayout infoLayout = new GridLayout(3, 2);
            JPanel info = new JPanel(infoLayout);
            desc.add(info);

            lHeight = new JLabel(String.valueOf(pokemon.getHeight()));
            info.add(lHeight);

            info.add(lCategory);

            lWeight = new JLabel(String.valueOf(pokemon.getWeight()));
            info.add(lWeight);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
