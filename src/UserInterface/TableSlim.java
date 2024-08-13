package UserInterface;

import DataEngine.DataEngine;
import DataEngine.Entry;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static Utils.Utils.copyToClipboard;

public class TableSlim implements Table {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    private final JPanel mainPanel;
    private final CardLayout cardLayout;

    private final DataEngine eng;

    public TableSlim(DataEngine eng, JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.eng = eng;
        this.tableModel = new DefaultTableModel(new Object[]{"Title", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // All cells are non-editable
            }
        };

        table = new JTable(this.tableModel);
        table.setFillsViewportHeight(true);
        //On double-click, open a separate window with the entry, just showing it
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click detected
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        //Render the entry in a separate window
                        Entry entry = eng.getEntries().get(selectedRow);
                        JPanel entryPanel = renderEntry(entry);
                        mainPanel.add(entryPanel, "entry");
                        cardLayout.show(mainPanel, "entry");
                    }
                }
            }
        });


        // Set custom font and row height
        Font tableFont = new Font("Arial", Font.PLAIN, 13);
        table.setFont(tableFont);
        table.setRowHeight(30);

        // Add grid lines between rows
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        // Set header font
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));

        //Scrollpane
        scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Add the entries to the table
        this.updateTableModel();
    }

    @Override
    public Component getComponent() {
        return scrollPane;
    }

    @Override
    public JTable getTable() {
        return table;
    }

    @Override
    public void updateTableModel() {
        // Clear the table
        tableModel.setRowCount(0);
        // Add the entries to the table
        for (Entry entry : eng.getEntries()) {
            tableModel.addRow(new Object[]{entry.getTitle(), entry.getDescription()});
        }
    }

    @Override
    // Method to update the table model with entries
    public void updateTableModel(List<Entry> entries) {
        tableModel.setRowCount(0); // Clear the table
        for (Entry entry : entries) {
            tableModel.addRow(new Object[]{
                    entry.getTitle(),
                    entry.getDescription(),
            });
        }
    }

    // Method to mask the secret with asterisks
    private String maskSecret(String secret) {
        return "*".repeat(secret.length());
    }

    //Render Entry showing all fields, adding the option to copy the gmail, username and secret
    //We use a verical table for this.
    private JPanel renderEntry(Entry entry){
        JPanel frame = new JPanel();

        //Add button to go back
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "main"));
        frame.add(backButton, BorderLayout.NORTH);

        JTable entryTable = new JTable(new DefaultTableModel(new Object[]{"Field", "Value"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // All cells are non-editable
            }
        });
        entryTable.setFillsViewportHeight(true);
        entryTable.setShowGrid(true);
        entryTable.setGridColor(Color.GRAY);
        entryTable.setFont(new Font("Arial", Font.PLAIN, 13));
        entryTable.setRowHeight(30);
        JTableHeader entryHeader = entryTable.getTableHeader();
        entryHeader.setFont(new Font("Arial", Font.BOLD, 18));

        //Add the fields to the table
        DefaultTableModel model = (DefaultTableModel) entryTable.getModel();
        model.addRow(new Object[]{"Title", entry.getTitle()});
        model.addRow(new Object[]{"Description", entry.getDescription()});
        model.addRow(new Object[]{"Username", entry.getUsername()});
        model.addRow(new Object[]{"Email", entry.getEmail()});
        model.addRow(new Object[]{"Secret", maskSecret(entry.getSecret())});
        model.addRow(new Object[]{"Last Modified", entry.getDate()});
        //Center content of cells in the "Field" column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        entryTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        //Limit a table to a window of 300x400
        entryTable.setPreferredScrollableViewportSize(new Dimension(300, 400));

        //Add a mouse listener to handle double-clicks
        entryTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click detected
                    int selectedRow = entryTable.getSelectedRow();
                    int selectedColumn = entryTable.getSelectedColumn();
                    if (selectedRow >= 0) {
                        String columnName = (String) entryTable.getValueAt(selectedRow, 0);
                        switch (columnName) {
                            case "Secret":
                                copyToClipboard(entry.getSecret(), e.getPoint());
                                break;
                            case "Username":
                                copyToClipboard(entry.getUsername(), e.getPoint());
                                break;
                            case "Email":
                                copyToClipboard(entry.getEmail(), e.getPoint());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });

        JScrollPane entryScrollPane = new JScrollPane(entryTable);
        frame.add(entryScrollPane, BorderLayout.CENTER);
        frame.setSize(300, 400);
        return frame;
    }
}
