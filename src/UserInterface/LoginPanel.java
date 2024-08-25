// LoginPanel.java
package UserInterface;

import DataEngine.DataEngine;
import UserInterface.SimpleDisplayManager.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends Page {
    public LoginPanel(PasswordManager passwordManager) {
        super("login", passwordManager);
    }

    @Override
    public JPanel generatePage() {
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Title
        JLabel titleLabel = displayManager.getCustomization().createCustomLabel("Login", 24, true);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // Add spacing between components
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        JLabel passwordLabel = displayManager.getCustomization().createCustomLabel("Password", 16, false);
        JPasswordField passwordField = new JPasswordField(20);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Login button
        JButton loginButton = displayManager.getCustomization().createCustomButton("Login", 16, true);
        displayManager.getCustomization().highlightComponent(loginButton);
        loginButton.setFocusPainted(false);

        ActionListener loginAction = e -> {
                if (PasswordCorrect(passwordField.getPassword(), ((PasswordManager) displayManager).getDataEngine())) {
                passwordField.setText("");
                goToPage("main");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid password", "Login", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        };

        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);

        // Center panel to hold input and button
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.CENTER);
        centerPanel.add(loginButton, BorderLayout.SOUTH);

        // Center the login panel in the frame
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(centerPanel);

        loginPanel.add(wrapperPanel, BorderLayout.CENTER);
        return loginPanel;
    }

    private boolean PasswordCorrect(char[] password, DataEngine engine) {
        engine.setKey(new String(password));
        return engine.open();
    }
}