package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ModuleFrame extends JFrame {
    protected final DefaultTableModelAdapter tableModel;
    private final Path storageFile;
    public ModuleFrame(String title, String[] columns, String[][] rows) {
        setTitle("MediCare | " + title); setSize(860, 560); setLocationRelativeTo(null);
        storageFile = Paths.get("data", title.toLowerCase().replace(' ', '_') + ".records");
        tableModel = new DefaultTableModelAdapter(columns, 0);
        loadRows(rows, columns.length);
        JTable table = new JTable(tableModel); table.setRowHeight(34); table.setShowVerticalLines(false); table.setIntercellSpacing(new Dimension(0, 1)); table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); table.getTableHeader().setPreferredSize(new Dimension(0, 36)); table.getTableHeader().setBackground(new Color(18, 55, 63)); table.getTableHeader().setForeground(Color.WHITE); table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        for (int index = 0; index < table.getColumnModel().getColumnCount(); index++) table.getColumnModel().getColumn(index).setPreferredWidth(170);
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() { public Component getTableCellRendererComponent(JTable t, Object value, boolean selected, boolean focused, int row, int column) { Component cell = super.getTableCellRendererComponent(t, value, selected, focused, row, column); setHorizontalAlignment(SwingConstants.CENTER); if (!selected) cell.setBackground(row % 2 == 0 ? Color.WHITE : new Color(247, 250, 249)); return cell; } });
        javax.swing.table.DefaultTableCellRenderer headerRenderer = new javax.swing.table.DefaultTableCellRenderer(); headerRenderer.setHorizontalAlignment(SwingConstants.CENTER); headerRenderer.setForeground(Color.WHITE); headerRenderer.setBackground(new Color(18, 55, 63)); headerRenderer.setFont(new Font("SansSerif", Font.BOLD, 12)); table.getTableHeader().setDefaultRenderer(headerRenderer);
        TableRowSorter<DefaultTableModelAdapter> sorter = new TableRowSorter<>(tableModel); table.setRowSorter(sorter);
        JTextField search = new JTextField(); search.setFont(new Font("SansSerif", Font.PLAIN, 14)); search.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(198, 215, 213)), BorderFactory.createEmptyBorder(9, 12, 9, 12))); search.putClientProperty("JTextField.placeholderText", "Search this workspace");
        search.getDocument().addDocumentListener(new DocumentListener() { public void insertUpdate(DocumentEvent e){filter();} public void removeUpdate(DocumentEvent e){filter();} public void changedUpdate(DocumentEvent e){filter();} private void filter(){ sorter.setRowFilter(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(search.getText()))); } });
        JButton add = new JButton("+  Add record"); add.setForeground(Color.WHITE); add.setBackground(new Color(18, 137, 119)); add.setFont(new Font("SansSerif", Font.BOLD, 13)); add.setFocusPainted(false); add.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); add.addActionListener(e -> addRow(columns));
        JButton delete = new JButton("×"); delete.setToolTipText("Delete selected record permanently"); delete.setForeground(Color.WHITE); delete.setBackground(new Color(190, 73, 68)); delete.setFont(new Font("SansSerif", Font.BOLD, 20)); delete.setFocusPainted(false); delete.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14)); delete.addActionListener(e -> deleteSelected(table));
        JLabel heading = new JLabel(title); heading.setForeground(new Color(18, 55, 63)); heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        JLabel subtitle = new JLabel("Review and manage information in this workspace"); subtitle.setForeground(new Color(93, 119, 120)); subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel titleBlock = new JPanel(); titleBlock.setOpaque(false); titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS)); titleBlock.add(heading); titleBlock.add(Box.createVerticalStrut(5)); titleBlock.add(subtitle);
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0)); actions.setOpaque(false); actions.add(delete); actions.add(add);
        JPanel toolbar = new JPanel(new BorderLayout(12, 0)); toolbar.setOpaque(false); toolbar.add(search, BorderLayout.CENTER); toolbar.add(actions, BorderLayout.EAST);
        JPanel root = new JPanel(new BorderLayout(0, 18)); root.setBackground(new Color(241, 247, 245)); root.setBorder(BorderFactory.createEmptyBorder(25, 28, 25, 28));
        root.add(titleBlock, BorderLayout.NORTH);
        root.add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new BorderLayout()); bottom.setOpaque(false); bottom.add(toolbar, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH); setContentPane(root);
    }
    private void deleteSelected(JTable table) {
        int selectedViewRow = table.getSelectedRow();
        if (selectedViewRow < 0) { JOptionPane.showMessageDialog(this, "Select a record first.", "Delete record", JOptionPane.INFORMATION_MESSAGE); return; }
        int selectedModelRow = table.convertRowIndexToModel(selectedViewRow);
        int answer = JOptionPane.showConfirmDialog(this, "Delete this record permanently?", "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) { tableModel.removeRow(selectedModelRow); saveRows(); }
    }
    private void addRow(String[] columns) {
        JPanel form = new JPanel(new GridLayout(columns.length, 2, 8, 8));
        JTextField[] fields = new JTextField[columns.length];
        for (int index = 0; index < columns.length; index++) {
            fields[index] = new JTextField();
            form.add(new JLabel(columns[index]));
            form.add(fields[index]);
        }
        int result = JOptionPane.showConfirmDialog(this, form, "Add record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Object[] row = new Object[columns.length];
            for (int index = 0; index < columns.length; index++) row[index] = fields[index].getText().trim();
            if (!row[0].toString().isEmpty()) { tableModel.addRow(row); saveRows(); }
        }
    }
    private void loadRows(String[][] defaults, int columnCount) {
        try {
            if (Files.exists(storageFile)) {
                for (String line : Files.readAllLines(storageFile, StandardCharsets.UTF_8)) {
                    String[] encoded = line.split("\\t", -1); Object[] row = new Object[columnCount];
                    for (int index = 0; index < columnCount; index++) row[index] = index < encoded.length ? new String(Base64.getDecoder().decode(encoded[index]), StandardCharsets.UTF_8) : "";
                    tableModel.addRow(row);
                }
                return;
            }
        } catch (IOException | IllegalArgumentException ignored) { }
        for (String[] row : defaults) tableModel.addRow(row);
    }
    private void saveRows() {
        try {
            Files.createDirectories(storageFile.getParent()); List<String> lines = new ArrayList<>();
            for (int row = 0; row < tableModel.getRowCount(); row++) { StringBuilder line = new StringBuilder(); for (int column = 0; column < tableModel.getColumnCount(); column++) { if (column > 0) line.append('\t'); line.append(Base64.getEncoder().encodeToString(String.valueOf(tableModel.getValueAt(row, column)).getBytes(StandardCharsets.UTF_8))); } lines.add(line.toString()); }
            Files.write(storageFile, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) { JOptionPane.showMessageDialog(this, "Could not save this record.", "Save error", JOptionPane.ERROR_MESSAGE); }
    }
    protected static class DefaultTableModelAdapter extends javax.swing.table.DefaultTableModel {
        DefaultTableModelAdapter(String[] columns, int rows) { super(columns, rows); }
        public boolean isCellEditable(int row, int column) { return false; }
    }
}