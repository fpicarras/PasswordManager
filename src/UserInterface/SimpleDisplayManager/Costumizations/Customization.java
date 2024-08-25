package UserInterface.SimpleDisplayManager.Costumizations;

import javax.swing.*;

public interface Customization {
    void setCustomText(JComponent component, int size, boolean bold);
    void highlightComponent(JComponent component);
    JLabel createCustomLabel(String text, int size, boolean bold);
    JButton createCustomButton(String text, int size, boolean bold);
    JLabel getWarningLabel(String text);
    JLabel getErrorLabel(String text);
}