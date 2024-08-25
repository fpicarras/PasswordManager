package UserInterface;

import DataEngine.DataEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SigninPanel extends LoginPanel{
    public SigninPanel(PasswordManager passwordManager) {
        super(passwordManager);
        super.pageName = "Signin";
    }

    @Override
    public JPanel generatePage() {
        DataEngine engine = ((PasswordManager)displayManager).getDataEngine();

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Title
        JLabel titleLabel = displayManager.getCustomization().createCustomLabel("Signin", 24, true);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Add spacing between components
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        JLabel passwordLabel = displayManager.getCustomization().createCustomLabel("Password:", 16, false);
        JPasswordField passwordField = new JPasswordField(20);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        //Second Input Panel
        JLabel passwordLabel2 = displayManager.getCustomization().createCustomLabel("Confirm:", 16, false);
        JPasswordField passwordField2 = new JPasswordField(20);
        inputPanel.add(passwordLabel2);
        inputPanel.add(passwordField2);

        // Signin button
        JButton loginButton = displayManager.getCustomization().createCustomButton("Signin", 16, true);
        displayManager.getCustomization().highlightComponent(loginButton);
        loginButton.setFocusPainted(false);

        ActionListener loginAction = e -> {
            if(match(passwordField.getPassword(), passwordField2.getPassword())){
                engine.setKey(new String(passwordField.getPassword()));
                engine.open();

                passwordField.setText("");
                passwordField2.setText("");
                goToPage("main");
            }else {
                JOptionPane.showMessageDialog(null, "Passwords do not match!", "Signin", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                passwordField2.setText("");
            }
        };

        loginButton.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.CENTER);
        centerPanel.add(loginButton, BorderLayout.SOUTH);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.add(centerPanel);

        loginPanel.add(wrapperPanel, BorderLayout.CENTER);
        return loginPanel;
    }

    //Check if passwords match
    private boolean match(char[] pass1, char[] pass2){
        if(pass1.length != pass2.length) return false;
        for(int i = 0; i < pass1.length; i++){
            if(pass1[i] != pass2[i]) return false;
        }
        return true;
    }
}
