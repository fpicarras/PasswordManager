import Crypt.*;
import DataEngine.*;
import JsonHandler.*;
import UserInterface.PasswordManager;
import UserInterface.SimpleDisplayManager.Costumizations.DefaultCustomization;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set UI as something modern
            //FlatDarkLaf.setup();
            FlatDarculaLaf.setup();
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EncryptAlgorithm c = new Picarra(16);
        JsonHandler j = new SimpleJsonHandler();
        DataEngine eng = new DataEngine(j, c);

        DefaultCustomization cus = new DefaultCustomization();

        SwingUtilities.invokeLater(() -> new PasswordManager(eng, cus));
    }
}
//key4567890123456