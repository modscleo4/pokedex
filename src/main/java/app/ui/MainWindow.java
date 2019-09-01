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
import app.ui.partials.PokemonCard;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.ui.ExceptionDialog;
import com.modscleo4.ui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static app.dao.AppDAO.pokemonDAO;

/**
 * Pokemon list window class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class MainWindow extends Window implements ActionListener, ComponentListener, WindowStateListener {
    private int page = 1;

    private IModelCollection<Pokemon> pokemons;

    private GridLayout pokemonListLayout;
    private JPanel pokemonList;
    private JScrollPane scrollPane;

    private JPanel loadMorePanel;
    private JButton loadMore;

    private JMenuBar menuBar;

    private JMenu menu;
    private JMenu help;

    private JMenuItem menuExit;

    private JMenuItem helpAbout;

    public MainWindow() {
        setTitle("Pokedex");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponentListener(this);
        addWindowStateListener(this);

        pokemonListLayout = new GridLayout(0, 4, 5, 5);
        pokemonList = new JPanel(pokemonListLayout);
        pokemonList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        scrollPane = new JScrollPane(pokemonList);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menu = new JMenu("Arquivo");
        menu.setMnemonic('A');
        menuBar.add(menu);

        menuExit = new JMenuItem("Sair");
        menuExit.addActionListener(this);
        menu.add(menuExit);

        help = new JMenu("Ajuda");
        help.setMnemonic('J');
        menuBar.add(help);

        helpAbout = new JMenuItem("Sobre");
        helpAbout.addActionListener(this);
        help.add(helpAbout);

        loadMorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        loadMore = new JButton("Carregar mais");
        loadMore.addActionListener(this);
        loadMorePanel.add(loadMore, constraints);

        try {
            this.pokemons = pokemonDAO.all().sortBy("id");
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }

        loadPokemons();
    }

    private void loadPokemons() {
        try {
            pokemonList.remove(loadMorePanel);

            IModelCollection<Pokemon> pokemons = this.pokemons.page(this.page++);
            for (Pokemon pokemon : pokemons) {
                PokemonCard card = new PokemonCard(pokemon);
                pokemonList.add(card);
            }

            if (pokemons.size() != 0 && this.pokemons.page(this.page).size() != 0) {
                pokemonList.add(loadMorePanel);
            }

            pokemonList.updateUI();
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuExit) {
            setVisible(false);
            dispose();
        } else if (e.getSource() == helpAbout) {
            JOptionPane.showMessageDialog(this, "Trabalho", "Trabalho", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == loadMore) {
            loadPokemons();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int width = this.getWidth();
        int cols = width / 150 - 1;
        pokemonListLayout.setColumns(cols);
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        int width = this.getWidth();
        int cols = width / 150 - 1;
        pokemonListLayout.setColumns(cols);
    }
}
