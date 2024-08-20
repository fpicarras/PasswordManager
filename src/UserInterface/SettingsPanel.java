// SettingsPanel.java
package UserInterface;

import javax.swing.*;
import java.awt.*;
import DataEngine.*;
import UserInterface.SimpleDisplayManager.Page;

public class SettingsPanel extends Page {
    public SettingsPanel(PasswordManager passwordManager) {
        super("settings", passwordManager);
    }

    @Override
    public JPanel generatePage() {
        // Main panel
        JPanel mainPage = new JPanel(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel(new GridLayout(1, 1));
        titlePanel.add(new JLabel("Settings"));

        // Page Divider
        JSeparator separator = new JSeparator();

        // Default mail (to create new mails)
        JPanel defaultMailPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 10, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        defaultMailPanel.add(new JLabel("Default mail:"), gbc);
        gbc.gridy = 1;
        //center the text field, without filling the whole space
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.85;
        JTextField defaultMail = new JTextField(20);
        String mail = ((PasswordManager) displayManager).getDataEngine().getDefaultMail();
        defaultMail.setText(mail);
        defaultMailPanel.add(defaultMail, gbc);
        if(mail.isEmpty()){
            JLabel warning = new JLabel("⚠: Default mail is empty!");
            warning.setForeground(Color.RED);
            gbc.gridy++;
            defaultMailPanel.add(warning, gbc);
        }

        // Change password
        JPanel changePasswordPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        changePasswordPanel.add(new JLabel("Change password:"), gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JPasswordField oldPassword = new JPasswordField(20);
        changePasswordPanel.add(oldPassword, gbc);
        gbc.gridy++;
        JPasswordField newPassword = new JPasswordField(20);
        changePasswordPanel.add(newPassword, gbc);
        gbc.gridy++;
        JPasswordField newPassword2 = new JPasswordField(20);
        changePasswordPanel.add(newPassword2, gbc);

        // Save button or go back button
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");
        buttonsPanel.add(backButton);
        buttonsPanel.add(saveButton);

        // Save button action
        saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DataEngine eng = ((PasswordManager) displayManager).getDataEngine();
                if(!defaultMail.getText().isEmpty() && !defaultMail.getText().equals(eng.getDefaultMail())){
                    eng.setDefaultMail(defaultMail.getText());
                    JOptionPane.showMessageDialog(null, "New mail saved!");
                }
                String oldPasswordString = new String(oldPassword.getPassword());
                String newPasswordString = new String(newPassword.getPassword());
                String newPassword2String = new String(newPassword2.getPassword());

                if(!(oldPasswordString.isEmpty() || newPasswordString.isEmpty() || newPassword2String.isEmpty())){
                    if (oldPasswordString.equals(eng.getKey())) {
                        if (newPasswordString.equals(newPassword2String)) {
                            eng.setKey(newPasswordString);
                            JOptionPane.showMessageDialog(null, "New password saved!");
                            goToPage("main");
                        } else {
                            JOptionPane.showMessageDialog(null, "New passwords do not match");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Old password is incorrect");
                    }
                }
                oldPassword.setText("");
                newPassword.setText("");
                newPassword2.setText("");
                goToPage("main");
            }
        });

        // Back button action
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goToPage("main");
            }
        });

        // Make a panel scrollable, that holds all the changing components
        JPanel scrollPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        scrollPanel.add(defaultMailPanel, gbc);
        gbc.gridy++;
        scrollPanel.add(changePasswordPanel, gbc);
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add components to main panel
        mainPage.add(titlePanel, BorderLayout.NORTH);
        mainPage.add(scrollPane, BorderLayout.CENTER);
        mainPage.add(buttonsPanel, BorderLayout.SOUTH);
        return mainPage;
    }
}