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

import app.dao.PokemonDAO;
import app.entity.Pokemon;
import com.modscleo4.framework.entity.IModelCollection;
import com.modscleo4.ui.ExceptionDialog;
import com.modscleo4.ui.Window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pokemon list window class.
 *
 * @author Dhiego Cassiano Fogaça Barbosa <modscleo4@outlook.com>
 */
public class MainWindow extends Window implements ActionListener {
    private PokemonDAO pokemonDAO = new PokemonDAO();

    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;

    private JMenuBar menuBar;

    private JMenu menu;
    private JMenu help;

    private JMenuItem menuDetails;
    private JMenuItem menuExit;

    private JMenuItem helpAbout;

    public MainWindow() {
        setTitle("Trabalho");
        //setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Descrição");

        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(0).setMinWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(160);
        table.getColumnModel().getColumn(1).setMinWidth(160);

        scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        scrollPane.setBounds(10, 10, 450, 190);
        add(scrollPane);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menu = new JMenu("Arquivo");
        menu.setMnemonic('A');
        menuBar.add(menu);

        menuDetails = new JMenuItem("Detalhes");
        menuDetails.addActionListener(this);
        menu.add(menuDetails);

        menuExit = new JMenuItem("Sair");
        menuExit.addActionListener(this);

        menu.addSeparator();
        menu.add(menuExit);

        help = new JMenu("Ajuda");
        help.setMnemonic('J');
        menuBar.add(help);

        helpAbout = new JMenuItem("Sobre");
        helpAbout.addActionListener(this);
        help.add(helpAbout);

        loadPokemons();
    }

    private void loadPokemons() {
        try {
            IModelCollection<Pokemon> pokemons = pokemonDAO.all().sortBy("id");
            for (Pokemon pokemon : pokemons) {
                long id = pokemon.getId();
                String name = pokemon.getName();
                String description = pokemon.getDescription();

                model.addRow(new Object[]{id, name, description});
            }
        } /* Ash */ catch/*um*/ (Exception e) {
            ExceptionDialog.show(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuDetails) {
            int row = table.getSelectedRow();
            if (row != -1) {
                try {
                    Pokemon pokemon = pokemonDAO.find(row + 1);
                    new PokemonWindow(pokemon).setVisible(true);
                } /* Ash */ catch/*um*/ (Exception ex) {
                    ExceptionDialog.show(ex);
                }
            }
        } else if (e.getSource() == menuExit) {
            setVisible(false);
            dispose();
        } else if (e.getSource() == helpAbout) {
            JOptionPane.showMessageDialog(this, "Trabalho", "Trabalho", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
