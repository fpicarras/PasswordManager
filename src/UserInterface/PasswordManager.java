package UserInterface;

import DataEngine.*;
import UserInterface.SimpleDisplayManager.Costumizations.Customization;
import UserInterface.SimpleDisplayManager.DisplayManager;

public class PasswordManager extends DisplayManager {
    private final DataEngine eng;

    public PasswordManager(DataEngine eng, Customization cus){
        super("Password Manager", 300, 400, cus);
        setIcon(getClass().getResource("/icon.png"));
        setOnClose(eng::save);
        this.eng = eng;

        //Creation of the pages
        LoginPanel login = new LoginPanel(this);
        MainPanel entryManager = new MainPanel(this);

        //Adding the pages to the display manager
        addPage(login);
        addPage(entryManager);

        //Check is file exists
        if(eng.fileExists()){
            showPage(login.getPageName());
        }else {
            SigninPanel signin = new SigninPanel(this);
            addPage(signin);
            showPage(signin.getPageName());
        }

        begin();
    }

    public DataEngine getDataEngine(){
        return eng;
    }
}
