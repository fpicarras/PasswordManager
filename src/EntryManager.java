import Crypt.*;
import DataEngine.*;
import JsonHandler.*;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class EntryManager {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchBar;
    private static ArrayList<Entry> entries;
    private static DataEngine eng;

    public EntryManager() {
        frame = new JFrame("Entry Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Override DefaultTableModel to make cells non-editable
        tableModel = new DefaultTableModel(new Object[]{"Title", "Description", "Username", "Email", "Secret", "Last Modified"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // All cells are non-editable
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        // Set custom font and row height
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        table.setFont(tableFont);
        table.setRowHeight(30);

        // Add grid lines between rows
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);

        // Set header font
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));

        // Add a mouse listener to handle double-clicks
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click detected
                    int selectedRow = table.getSelectedRow();
                    int selectedColumn = table.getSelectedColumn();
                    if (selectedRow >= 0) {
                        String columnName = table.getColumnName(selectedColumn);
                        switch (columnName) {
                            case "Secret":
                                copyToClipboard(entries.get(selectedRow).getSecret(), e.getPoint());
                                break;
                            case "Username":
                                copyToClipboard(entries.get(selectedRow).getUsername(), e.getPoint());
                                break;
                            case "Email":
                                copyToClipboard(entries.get(selectedRow).getEmail(), e.getPoint());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });

        // Search bar
        searchBar = new JTextField(20);
        searchBar.addActionListener(this::searchEntries);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchBar);

        // Populate the table with existing entries
        updateTableModel(entries);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(this::addEntry);
        editButton.addActionListener(this::editEntry);
        deleteButton.addActionListener(this::deleteEntry);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save the entries when closing the window
                eng.save("teste.json", entries);
            }
        });
    }

    private void addEntry(ActionEvent e) {
        Entry entry = showEntryDialog(null);
        if (entry != null) {
            entries.add(entry);
            updateTableModel(entries);
        }
    }

    private void editEntry(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Entry entry = entries.get(selectedRow);
            Entry updatedEntry = showEntryDialog(entry);
            if (updatedEntry != null) {
                entries.set(selectedRow, updatedEntry);
                updateTableModel(entries);
            }
        }
    }

    private void deleteEntry(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            entries.remove(selectedRow);
            updateTableModel(entries);
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

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
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

        int result = JOptionPane.showConfirmDialog(null, panel, entry == null ? "Add Entry" : "Edit Entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (entry == null) {
                return new Entry(new JSONObject()
                        .put("title", titleField.getText())
                        .put("description", descriptionField.getText())
                        .put("username", usernameField.getText())
                        .put("email", emailField.getText())
                        .put("secret", secretField.getText())
                        .put("date", new SimpleDateFormat("HH:mm dd/MM/yyyy").format(Calendar.getInstance().getTime())));
            } else {
                entry.setTitle(titleField.getText());
                entry.setDescription(descriptionField.getText());
                entry.setUsername(usernameField.getText());
                entry.setEmail(emailField.getText());
                entry.setSecret(secretField.getText());
                return entry;
            }
        }

        return null;
    }

    // Method to mask the secret with asterisks
    private String maskSecret(String secret) {
        return "*".repeat(secret.length());
    }

    // Method to copy the specified value to the clipboard
    private void copyToClipboard(String value, Point mousePosition) {
        StringSelection stringSelection = new StringSelection(value);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        // Show a small message near the mouse position
        showPopupMessage("Copied to clipboard!", mousePosition);
    }

    // Method to show a popup message near the mouse position
    private void showPopupMessage(String message, Point location) {
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

    // Method to update the table model with entries
    private void updateTableModel(List<Entry> entries) {
        tableModel.setRowCount(0); // Clear the table
        for (Entry entry : entries) {
            tableModel.addRow(new Object[]{
                    entry.getTitle(),
                    entry.getDescription(),
                    entry.getUsername(),
                    entry.getEmail(),
                    maskSecret(entry.getSecret()),
                    entry.getDate()
            });
        }
    }

    // Method to search entries based on the search query
    private void searchEntries(ActionEvent e) {
        String query = searchBar.getText().toLowerCase();
        List<Entry> filteredEntries = entries.stream()
                .filter(entry -> entry.getTitle().toLowerCase().contains(query) || entry.getDescription().toLowerCase().contains(query))
                .collect(Collectors.toList());
        updateTableModel(filteredEntries);
    }

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
        eng = new DataEngine(j, c, "key4567890123456");

        entries = eng.open("teste.json");
        SwingUtilities.invokeLater(EntryManager::new);
    }
}