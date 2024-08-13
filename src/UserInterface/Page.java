package UserInterface;

import javax.swing.*;
import java.awt.*;

public abstract class Page {
    protected String previousPage;
    protected Page nextPage;
    protected JPanel mainPanel;
    protected CardLayout cardLayout;
    protected String pageName;

    public Page(JPanel mainPanel, CardLayout cardLayout, String pageName){
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.pageName = pageName;
    }

    public abstract JPanel generatePage();

    public void setPreviousPage(String pageName){
        this.previousPage = pageName;
    }

    public void goBack(){
        cardLayout.show(mainPanel, previousPage);
    }

    public void goNext(){
        mainPanel.add(nextPage.generatePage(), nextPage.getPageName());
        cardLayout.show(mainPanel, nextPage.getPageName());
    }

    public void goToPage(String pageName){
        cardLayout.show(mainPanel, pageName);
    }

    public String getPageName(){
        return pageName;
    }

    public void setNextPage(Page pageName){
        this.nextPage = pageName;
    }
}
