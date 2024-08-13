package UserInterface;

import DataEngine.Entry;

import javax.swing.*;

public interface Table extends ComponentBuilder {
    public JTable getTable();
    public void updateTableModel();
    public void updateTableModel(java.util.List<Entry> entries);
}
