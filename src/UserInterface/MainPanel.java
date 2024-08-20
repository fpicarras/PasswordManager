package UserInterface;

import DataEngine.DataEngine;
import UserInterface.SimpleDisplayManager.Page;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends Page {
    public MainPanel(PasswordManager passwordManager) {
        super("main", passwordManager);
    }

    @Override
    public JPanel generatePage(){
        DataEngine eng = ((PasswordManager)displayManager).getDataEngine();

        Table table = new TableSlim((PasswordManager) displayManager);
        InteractionButtons buttons = new InteractionButtons(eng, table);
        SearchBar search = new SearchBar(eng, table);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(search.getComponent());
        header.add(buttons.getComponent());

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
        buttonsPanel.add(new JButton("Settings"));

        //Button to go to settings
        SettingsPanel settings = new SettingsPanel((PasswordManager) super.displayManager);
        displayManager.addPage(settings);
        buttonsPanel.getComponent(0).addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goToPage("settings");
            }
        });

        buttonsPanel.add(new JButton("Logout"));
        //Button to logout
        buttonsPanel.getComponent(1).addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eng.save();
                eng.deleteEntries();
                goToPage("login");
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
