package UserInterface;

import DataEngine.DataEngine;

import javax.swing.*;
import java.awt.*;

public class PasswordManager {
    public PasswordManager(DataEngine eng){
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        ImageIcon icon = new ImageIcon("src/icon.png");
        frame.setIconImage(icon.getImage());

        //Creation of the pages
        LoginPanel login = new LoginPanel(eng, mainPanel, cardLayout);
        EntryManagerSlim entryManager = new EntryManagerSlim(eng, mainPanel, cardLayout);
        login.setNextPage(entryManager);
        entryManager.setPreviousPage(login.getPageName());

        mainPanel.add(login.generatePage(), login.getPageName());
        frame.add(mainPanel);
        cardLayout.show(mainPanel, login.getPageName());
        frame.setVisible(true);

        //SAve on exit
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                eng.save("teste.json");
            }
        });
    }
}
