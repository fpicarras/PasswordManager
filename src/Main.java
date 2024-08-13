import Crypt.*;
import DataEngine.*;
import JsonHandler.*;
import UserInterface.PasswordManager;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Set UI as something modern
            FlatDarkLaf.setup();
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EncryptAlgorithm c = new Picarra(16);
        JsonHandler j = new SimpleJsonHandler();
        DataEngine eng = new DataEngine(j, c);

        SwingUtilities.invokeLater(() -> new PasswordManager(eng));
    }
}
//key4567890123456