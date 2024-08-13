package UserInterface;

import DataEngine.DataEngine;
import DataEngine.Entry;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

        panel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(this::addEntry);
        editButton.addActionListener(this::editEntry);
        deleteButton.addActionListener(this::deleteEntry);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
    }

    @Override
    public Component getComponent() {
        return panel;
    }

    private void addEntry(ActionEvent e) {
        Entry entry = showEntryDialog(null);
        if (entry != null) {
            eng.getEntries().add(entry);
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

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
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
        panel.add(new JLabel("Last modified:      " + (entry == null ? "" : entry.getDate())));

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
}
