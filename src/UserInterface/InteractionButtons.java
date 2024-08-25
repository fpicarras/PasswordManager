package UserInterface;

import DataEngine.DataEngine;
import DataEngine.Entry;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class InteractionButtons implements ComponentBuilder {
    private final Table table;
    private final DataEngine eng;
    private final JPanel panel;

    public InteractionButtons(DataEngine eng, Table table) {
        //Initialize parameters
        this.eng = eng;
        this.table = table;

        panel = new JPanel(new GridLayout(1, 3, 1, 1)); // 1 row, 3 columns, 10px horizontal and vertical gaps
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(this::addEntry);
        editButton.addActionListener(this::editEntry);
        deleteButton.addActionListener(this::deleteEntry);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.setPreferredSize(new Dimension(269, 36));
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    private void addEntry(ActionEvent e) {
        Entry entry = showEntryDialog(null);
        if (entry != null) {
            eng.getEntries().add(entry);
            eng.sort();
            table.updateTableModel();
        }
    }

    private void editEntry(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow >= 0) {
            List<Entry> entries = eng.getEntries();
            Entry entry = entries.get(selectedRow);
            Entry updatedEntry = showEntryDialog(entry);
            if (updatedEntry != null) {
                entries.set(selectedRow, updatedEntry);
                eng.sort();
                table.updateTableModel();
            }
        }
    }

    private void deleteEntry(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        List<Entry> entries = eng.getEntries();
        if (selectedRow >= 0) {
            entries.remove(selectedRow);
            table.updateTableModel();
        }
    }

    private Entry showEntryDialog(Entry entry) {
        JTextField titleField = new JTextField(20);
        JTextField descriptionField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField secretField = new JPasswordField(20);

        if (entry != null) {
            titleField.setText(entry.getTitle());
            descriptionField.setText(entry.getDescription());
            usernameField.setText(entry.getUsername());
            emailField.setText(entry.getEmail());
            secretField.setText(entry.getSecret());
        }

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Secret:"));
        panel.add(secretField);
        panel.add(new JLabel("Last modified:"));
        panel.add(new JLabel(entry == null ? "" : entry.getDate()));

        JPanel generateButtonsPanel = getGenerateButtonsPanel(secretField, emailField, titleField);
        panel.add(generateButtonsPanel);
        
        int result = JOptionPane.showConfirmDialog(null, panel, entry == null ? "Add Entry" : "Edit Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (entry == null) {
                return new Entry(new JSONObject()
                        .put("title", titleField.getText())
                        .put("description", descriptionField.getText())
                        .put("username", usernameField.getText())
                        .put("email", emailField.getText())
                        .put("secret", new String(secretField.getPassword()))
                        .put("date", new SimpleDateFormat("HH:mm dd/MM/yyyy").format(Calendar.getInstance().getTime())));
            } else {
                entry.setTitle(titleField.getText());
                entry.setDescription(descriptionField.getText());
                entry.setUsername(usernameField.getText());
                entry.setEmail(emailField.getText());
                entry.setSecret(new String(secretField.getPassword()));
                return entry;
            }
        }
        return null;
    }

    private JPanel getGenerateButtonsPanel(JPasswordField secretField, JTextField emailField, JTextField titleField) {
        JPanel generateButtonsPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        JButton generatePasswordButton = new JButton("Generate Password");
        JButton generateEmailButton = new JButton("Generate Email");

        generatePasswordButton.addActionListener(e -> {
            //If the field is not empty, prompt the user to confirm
            if (secretField.getPassword().length > 0) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to generate a new password? The current one will be lost.", "Generate Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("Generating password");
                    secretField.setText(eng.generatePassword());
                }
            }else {
                secretField.setText(eng.generatePassword());
            }
        });
        generateEmailButton.addActionListener(e -> {
            //Prompt the user to confirm
            if (!emailField.getText().isEmpty()) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to generate a new email? The current one will be lost.", "Generate Email", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    emailField.setText(eng.generateEmail(titleField.getText()));
                }
            } else {
                emailField.setText(eng.generateEmail(titleField.getText()));
            }
        });

        generateButtonsPanel.add(generatePasswordButton);
        generateButtonsPanel.add(generateEmailButton);
        return generateButtonsPanel;
    }
}
