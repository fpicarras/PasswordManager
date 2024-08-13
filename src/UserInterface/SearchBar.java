package UserInterface;

import DataEngine.DataEngine;
import DataEngine.Entry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class SearchBar implements ComponentBuilder {
    private final JTextField searchBar;
    private final JPanel searchPanel;
    private final DataEngine eng;
    private final Table table;

    public SearchBar(DataEngine eng, Table table) {
        this.eng = eng;
        this.table = table;

        // Search bar
        searchBar = new JTextField(20);
        searchBar.addActionListener(this::searchEntries);

        searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchBar);
    }

    // Method to search entries based on the search query
    private void searchEntries(ActionEvent e) {
        String query = searchBar.getText().toLowerCase();
        List<Entry> entries = eng.getEntries();
        List<Entry> filteredEntries = entries.stream()
                .filter(entry -> entry.getTitle().toLowerCase().contains(query) || entry.getDescription().toLowerCase().contains(query))
                .collect(Collectors.toList());
        table.updateTableModel(filteredEntries);
    }

    @Override
    public Component getComponent() {
        return searchPanel;
    }

    public void focus() {
        searchBar.requestFocus();
        System.out.println("Focused search bar");
    }
}
