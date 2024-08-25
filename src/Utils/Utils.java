// Utils.java
package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Color color = new Color(53, 53, 53, 200);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(7, 20, 7, 20));
        label.setBorder(BorderFactory.createLineBorder(color, 1, true)); // Rounded border
        popup.getContentPane().add(label);
        popup.pack();

        // Adjust the location to ensure the popup is within screen bounds
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = Math.min(location.x, screenSize.width - popup.getWidth());
        int y = Math.min(location.y-popup.getHeight(), screenSize.height - popup.getHeight());
        popup.setLocation(x, y);

        popup.setVisible(true);

        // Fade out the popup after 1.5 seconds
        Timer timer = new Timer(50, new ActionListener() {
            float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0) {
                    popup.setVisible(false);
                    ((Timer) e.getSource()).stop();
                } else {
                    popup.setOpacity(opacity);
                }
            }
        });
        timer.setInitialDelay(450);
        timer.start();
    }
}