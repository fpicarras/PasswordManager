package UserInterface.SimpleDisplayManager.Costumizations;

import javax.swing.*;
import java.awt.*;

public class DefaultText implements CustomText {
    private Color highlightColor = new Color(70, 130, 180, 255);
    private String fontName = "Arial";

    public DefaultText(Color highlightColor, String fontName) {
        this.highlightColor = highlightColor;
        this.fontName = fontName;
    }

    public DefaultText() {
    }

    @Override
    public void setCustomText(JComponent component, int size, boolean bold) {
        Font font = new Font(fontName, bold ? Font.BOLD : Font.PLAIN, size);
        component.setFont(font);
    }

    @Override
    public void highlightComponent(JComponent component) {
        component.setBackground(highlightColor);
    }

    @Override
    public JLabel createCustomLabel(String text, int size, boolean bold) {
        JLabel label = new JLabel(text);
        setCustomText(label, size, bold);
        return label;
    }

    @Override
    public JLabel getWarningLabel(String text) {
        JLabel label = new JLabel("⚠: " + text);
        label.setForeground(Color.YELLOW);
        return label;
    }

    @Override
    public JLabel getErrorLabel(String text) {
        JLabel label = new JLabel("❌: " + text);
        label.setForeground(Color.RED);
        return label;
    }
}
