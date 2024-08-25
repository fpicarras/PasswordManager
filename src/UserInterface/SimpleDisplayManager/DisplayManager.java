package UserInterface.SimpleDisplayManager;

import UserInterface.SimpleDisplayManager.Costumizations.Customization;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class DisplayManager {
    private final HashMap<String, Page> pages;
    private final CardLayout cardLayout;
    protected final JPanel mainPanel;
    protected JFrame frame;

    private final Customization customization;

    private final ArrayList<String> renderedPages;

    public DisplayManager(String displayName, int width, int height, Customization aesthetics){
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        pages = new HashMap<>();
        renderedPages = new ArrayList<>();
        frame = new JFrame(displayName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        customization = aesthetics;
    }

    public void begin(){
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public void addPage(Page page){
        pages.put(page.getPageName(), page);
    }

    public void removePage(String pageName){
        pages.remove(pageName);
    }

    public void showPage(String pageName){
        if(!renderedPages.contains(pageName)){
            mainPanel.add(pages.get(pageName).generatePage(), pageName);
            renderedPages.add(pageName);
        }
        cardLayout.show(mainPanel, pageName);
    }

    //Force render the page
    public void showPage(JPanel page){
        mainPanel.add(page, "temp");
        cardLayout.show(mainPanel, "temp");
    }

    public void setIcon(String path){
        try {
            ImageIcon icon = new ImageIcon(path);
            frame.setIconImage(icon.getImage());
        }catch (Exception e){
            System.out.println("Error setting icon: " + e.getMessage());
        }
    }

    public void setIcon(URL path){
        try {
            ImageIcon icon = new ImageIcon(path);
            frame.setIconImage(icon.getImage());
        }catch (Exception e){
            System.out.println("Error setting icon: " + e.getMessage());
        }
    }

    public Customization getCustomization(){
        return customization;
    }

    //Receives a function to execute on window close
    public void setOnClose(Runnable onClose){
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onClose.run();
            }
        });
    }
}
