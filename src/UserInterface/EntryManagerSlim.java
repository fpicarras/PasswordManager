package UserInterface;

import DataEngine.DataEngine;

import javax.swing.*;
import java.awt.*;

public class EntryManagerSlim extends Page {
    private final DataEngine eng;

    public EntryManagerSlim(DataEngine eng, JPanel mainPanel, CardLayout cardLayout) {
        super(mainPanel, cardLayout, "main");
        this.eng = eng;
    }

    @Override
    public JPanel generatePage(){

        Table table = new TableSlim(eng, mainPanel, cardLayout);
        InteractionButtons buttons = new InteractionButtons(eng, table);
        SearchBar search = new SearchBar(eng, table);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(search.getComponent());
        header.add(buttons.getComponent());

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 1));
        buttonsPanel.add(new JButton("Logout"));
        //Button to logout
        buttonsPanel.getComponent(0).addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eng.save("teste.json");
                eng.deleteEntries();
                cardLayout.show(mainPanel, "login");
            }
        });

        JPanel mainPage = new JPanel(new BorderLayout());
        mainPage.add(header, BorderLayout.NORTH);
        mainPage.add(table.getComponent(), BorderLayout.CENTER);
        mainPage.add(buttonsPanel, BorderLayout.SOUTH);
        mainPage.setSize(300, 400);
        return mainPage;
    }
}
