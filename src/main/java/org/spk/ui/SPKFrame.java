package org.spk.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.spk.model.CustomerFeedback;
import org.spk.service.DecisionService;
import org.spk.service.ExportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SPKFrame extends JFrame {
    private JTextField nameField;
    private JSpinner qualitySpinner, speedSpinner, priceSpinner, csSpinner;
    private DefaultTableModel tableModel;
    private final List<CustomerFeedback> feedbackList = new ArrayList<>();

    public SPKFrame() {
        setTitle("SPK SAW - Customer Satisfaction");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 5));
        nameField = new JTextField();
        qualitySpinner = createSpinner();
        speedSpinner = createSpinner();
        priceSpinner = createSpinner();
        csSpinner = createSpinner();

        inputPanel.add(new JLabel("Name of Customers :"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Product of Quality (1-5):"));
        inputPanel.add(qualitySpinner);
        inputPanel.add(new JLabel("Speed of Service (1-5):"));
        inputPanel.add(speedSpinner);
        inputPanel.add(new JLabel("Price (1-5):"));
        inputPanel.add(priceSpinner);
        inputPanel.add(new JLabel("Customer Service (1-5):"));
        inputPanel.add(csSpinner);

        JButton addButton = new JButton("Add and Count");
        addButton.addActionListener(e -> addFeedback());
        inputPanel.add(addButton);

        JButton exportButton = new JButton("Export to CSV");
        exportButton.addActionListener(e -> exportToCSV());
        inputPanel.add(exportButton);

        JButton previewButton = new JButton("Preview Normalization");
        previewButton.addActionListener(e -> previewNormalisasi());
        inputPanel.add(previewButton);

        JButton chartButton = new JButton("Show of Graphic Score");
        chartButton.addActionListener(e -> showScoreChart());
        inputPanel.add(chartButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"Rank", "Name", "Score", "Action"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Aksi klik hapus
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && JOptionPane.showConfirmDialog(SPKFrame.this, "Delete this data?") == 0) {
                    feedbackList.remove(row);
                    updateTable();
                }
            }
        });
    }

    private JSpinner createSpinner() {
        return new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
    }

    private void addFeedback() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        CustomerFeedback fb = new CustomerFeedback(
            name,
            (Integer) qualitySpinner.getValue(),
            (Integer) speedSpinner.getValue(),
            (Integer) priceSpinner.getValue(),
            (Integer) csSpinner.getValue()
        );
        feedbackList.add(fb);
        updateTable();
        clearForm();
    }

    private void updateTable() {
        List<Map.Entry<CustomerFeedback, Double>> ranked = DecisionService.rank(feedbackList);
        tableModel.setRowCount(0);
        int rank = 1;
        for (Map.Entry<CustomerFeedback, Double> entry : ranked) {
            tableModel.addRow(new Object[]{
                rank++,
                entry.getKey().customerName,
                String.format("%.4f", entry.getValue()),
                "Delete"
            });
        }
    }

    private void exportToCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("report_of_spk.csv"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            ExportService.exportToCSV(DecisionService.rank(feedbackList), fileChooser.getSelectedFile().getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Saved Successfully.");
        }
    }

    private void clearForm() {
        nameField.setText("");
        qualitySpinner.setValue(1);
        speedSpinner.setValue(1);
        priceSpinner.setValue(1);
        csSpinner.setValue(1);
    }

    private void previewNormalisasi() {
        if (feedbackList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data is empty.");
            return;
        }

        double maxQuality = feedbackList.stream().mapToInt(f -> f.productQuality).max().orElse(1);
        double maxSpeed = feedbackList.stream().mapToInt(f -> f.serviceSpeed).max().orElse(1);
        double maxPrice = feedbackList.stream().mapToInt(f -> f.price).max().orElse(1);
        double maxCS = feedbackList.stream().mapToInt(f -> f.customerService).max().orElse(1);

        String[] cols = {"Name", "Quality", "Speed", "Price", "CS"};
        Object[][] data = new Object[feedbackList.size()][cols.length];

        for (int i = 0; i < feedbackList.size(); i++) {
            CustomerFeedback f = feedbackList.get(i);
            data[i][0] = f.customerName;
            data[i][1] = String.format("%.3f", f.productQuality / maxQuality);
            data[i][2] = String.format("%.3f", f.serviceSpeed / maxSpeed);
            data[i][3] = String.format("%.3f", f.price / maxPrice);
            data[i][4] = String.format("%.3f", f.customerService / maxCS);
        }

        JTable table = new JTable(data, cols);
        JScrollPane scrollPane = new JScrollPane(table);

        JDialog dialog = new JDialog(this, "Matrix Normalization", true);
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(this);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void showScoreChart() {
        if (feedbackList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data is empty.");
            return;
        }

        List<Map.Entry<CustomerFeedback, Double>> ranked = DecisionService.rank(feedbackList);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<CustomerFeedback, Double> entry : ranked) {
            dataset.addValue(entry.getValue(), "Skor", entry.getKey().customerName);
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Customers Visualization Score",
            "Customer",
            "Score of SAW",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        JDialog chartDialog = new JDialog(this, "Graphic of Score", true);
        chartDialog.setSize(700, 400);
        chartDialog.setLocationRelativeTo(this);
        chartDialog.add(chartPanel);
        chartDialog.setVisible(true);
    }
}

