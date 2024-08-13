package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Utils {
    // Method to copy the specified value to the clipboard
    public static void copyToClipboard(String value, Point mousePosition) {
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        // Show a small message near the mouse position
        showPopupMessage("Copied to clipboard!", mousePosition);
    }

    // Method to show a popup message near the mouse position
    public static void showPopupMessage(String message, Point location) {
        JWindow popup = new JWindow();
        JLabel label = new JLabel(message);
        label.setOpaque(true);
        label.setBackground(new Color(0, 0, 0, 200));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        popup.getContentPane().add(label);
        popup.pack();
        popup.setLocation(location);
        popup.setVisible(true);

        // Hide the popup after 1.5 seconds
        Timer timer = new Timer(1500, e -> popup.setVisible(false));
        timer.setRepeats(false);
        timer.start();
    }
}
