package UserInterface.SimpleDisplayManager;

import javax.swing.*;

public abstract class Page {
    protected String pageName;
    public DisplayManager displayManager;

    public Page(String pageName, DisplayManager displayManager){
        this.pageName = pageName;
        this.displayManager = displayManager;
    }

    public abstract JPanel generatePage();

    public void goToPage(String pageName){
        displayManager.showPage(pageName);
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public String getPageName(){
        return pageName;
    }
}
