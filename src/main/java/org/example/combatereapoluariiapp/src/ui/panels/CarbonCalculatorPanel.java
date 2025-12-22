package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.model.CarbonFootprintCalculator;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarbonCalculatorPanel extends JPanel {
    private boolean isDarkMode;

    // Input fields
    private JTextField carKmField;
    private JTextField flightKmField;
    private JTextField busKmField;
    private JTextField trainKmField;
    private JTextField electricityField;
    private JTextField gasField;
    private JComboBox<String> dietComboBox;

    // Results
    private JLabel totalCO2Label;
    private JLabel transportLabel;
    private JLabel energyLabel;
    private JLabel lifestyleLabel;
    private JTextArea recommendationArea;
    private JPanel resultPanel;

    public CarbonCalculatorPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        initializeComponents();
    }

    private void initializeComponents() {
        // Title
        JLabel title = new JLabel("Calculator Amprentă de Carbon");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Create scrollable main content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        // Content panel with two sections
        JPanel contentWrapper = new JPanel(new GridLayout(1, 2, 30, 0));
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input panel (left side)
        JPanel inputPanel = createInputPanel();
        contentWrapper.add(inputPanel);

        // Results panel (right side)
        resultPanel = createResultsPanel();
        contentWrapper.add(resultPanel);

        mainContent.add(contentWrapper, BorderLayout.CENTER);

        // Make it scrollable
        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(60, 60, 70, 150) : new Color(240, 250, 255, 180);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setPreferredSize(new Dimension(450, 600));

        JLabel inputTitle = new JLabel("Introdu datele tale anuale:");
        inputTitle.setFont(new Font("Arial", Font.BOLD, 20));
        inputTitle.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        panel.add(inputTitle, BorderLayout.NORTH);

        // Scrollable form panel
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setOpaque(false);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Transport section
        int row = 0;
        addSectionHeader(formPanel, "🚗 TRANSPORT", row++, gbc);

        carKmField = addInputField(formPanel, "Kilometri cu mașina personală:", "15000", row++, gbc);
        flightKmField = addInputField(formPanel, "Kilometri cu avionul:", "2000", row++, gbc);
        busKmField = addInputField(formPanel, "Kilometri cu autobuzul:", "1000", row++, gbc);
        trainKmField = addInputField(formPanel, "Kilometri cu trenul:", "500", row++, gbc);

        // Energy section
        addSectionHeader(formPanel, "⚡ ENERGIE CASNICĂ", row++, gbc);

        electricityField = addInputField(formPanel, "Electricitate (kWh/lună):", "200", row++, gbc);
        gasField = addInputField(formPanel, "Gaz (kWh/lună):", "150", row++, gbc);

        // Lifestyle section
        addSectionHeader(formPanel, "🍽️ STIL DE VIAȚĂ", row++, gbc);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel dietLabel = new JLabel("Tipul dietei:");
        dietLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dietLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        formPanel.add(dietLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        dietComboBox = new JComboBox<>(new String[]{"omnivore", "vegetarian", "vegan"});
        dietComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        dietComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        dietComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        dietComboBox.setPreferredSize(new Dimension(200, 35));
        formPanel.add(dietComboBox, gbc);

        // Add some vertical space
        gbc.gridx = 0;
        gbc.gridy = row + 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        formPanel.add(Box.createVerticalGlue(), gbc);

        // Wrap form in scroll pane
        JScrollPane formScroll = new JScrollPane(formPanel);
        formScroll.setOpaque(false);
        formScroll.getViewport().setOpaque(false);
        formScroll.setBorder(null);
        formScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        formContainer.add(formScroll, BorderLayout.CENTER);
        panel.add(formContainer, BorderLayout.CENTER);

        // Calculate button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton calculateBtn = UIUtils.createStyledButton("🧮 Calculează", isDarkMode);
        calculateBtn.setPreferredSize(new Dimension(200, 50));
        calculateBtn.addActionListener(new CalculateActionListener());
        buttonPanel.add(calculateBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addSectionHeader(JPanel parent, String title, int row, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0;

        JLabel header = new JLabel(title);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setForeground(ThemeConstants.PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(15, 0, 8, 0));
        parent.add(header, gbc);

        gbc.gridwidth = 1;
        gbc.weightx = 0;
    }

    private JTextField addInputField(JPanel parent, String labelText, String placeholder, int row, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        label.setPreferredSize(new Dimension(250, 25));
        parent.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(isDarkMode ? new Color(50, 50, 60) : Color.WHITE);
        field.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isDarkMode ? new Color(80, 80, 90) : new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(200, 35));
        parent.add(field, gbc);

        return field;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(70, 70, 80, 150) : new Color(250, 255, 250, 180);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setPreferredSize(new Dimension(450, 600));

        JLabel resultTitle = new JLabel("Rezultatele calculului:");
        resultTitle.setFont(new Font("Arial", Font.BOLD, 20));
        resultTitle.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        panel.add(resultTitle, BorderLayout.NORTH);

        // Initial empty state
        showEmptyResults(panel);

        return panel;
    }

    private void showEmptyResults(JPanel panel) {
        // Remove existing content except title
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            if (comp != panel.getComponent(0)) { // Keep the title
                panel.remove(comp);
            }
        }

        JPanel emptyPanel = new JPanel(new GridBagLayout());
        emptyPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel emptyIcon = new JLabel("🌍");
        emptyIcon.setFont(new Font("Arial", Font.PLAIN, 64));
        gbc.gridx = 0;
        gbc.gridy = 0;
        emptyPanel.add(emptyIcon, gbc);

        JLabel emptyText = new JLabel("<html><center>Introdu datele și apasă<br>'Calculează' pentru a vedea<br>amprenta ta de carbon!</center></html>");
        emptyText.setFont(new Font("Arial", Font.PLAIN, 16));
        emptyText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        emptyText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        emptyPanel.add(emptyText, gbc);

        panel.add(emptyPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private void showResults(CarbonFootprintCalculator.CarbonResult result) {
        // Remove existing content except title
        Component[] components = resultPanel.getComponents();
        for (int i = components.length - 1; i >= 1; i--) {
            resultPanel.remove(components[i]);
        }

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        summaryPanel.setOpaque(false);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Total CO2
        JPanel totalPanel = createResultCard("🌍",
                String.format("%.1f", result.getTotalAnnualCO2() / 1000),
                "tone CO2/an", "Total amprentă");
        summaryPanel.add(totalPanel);

        // Transport
        JPanel transportPanel = createResultCard("🚗",
                String.format("%.1f", result.getTransportCO2() / 1000),
                "tone CO2/an", "Transport");
        summaryPanel.add(transportPanel);

        // Energy
        JPanel energyPanel = createResultCard("⚡",
                String.format("%.1f", result.getEnergyCO2() / 1000),
                "tone CO2/an", "Energie");
        summaryPanel.add(energyPanel);

        // Lifestyle
        JPanel lifestylePanel = createResultCard("🍽️",
                String.format("%.1f", result.getLifestyleCO2() / 1000),
                "tone CO2/an", "Stil de viață");
        summaryPanel.add(lifestylePanel);

        contentPanel.add(summaryPanel, BorderLayout.NORTH);

        // Recommendations
        JPanel recPanel = new JPanel(new BorderLayout());
        recPanel.setOpaque(false);
        recPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel recTitle = new JLabel("💡 Recomandări personalizate:");
        recTitle.setFont(new Font("Arial", Font.BOLD, 16));
        recTitle.setForeground(ThemeConstants.PRIMARY_COLOR);
        recPanel.add(recTitle, BorderLayout.NORTH);

        JTextArea recArea = new JTextArea(result.getRecommendation());
        recArea.setFont(new Font("Arial", Font.PLAIN, 14));
        recArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        recArea.setOpaque(false);
        recArea.setEditable(false);
        recArea.setWrapStyleWord(true);
        recArea.setLineWrap(true);
        recArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JScrollPane recScroll = new JScrollPane(recArea);
        recScroll.setOpaque(false);
        recScroll.getViewport().setOpaque(false);
        recScroll.setBorder(null);
        recScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        recScroll.setPreferredSize(new Dimension(0, 200));

        recPanel.add(recScroll, BorderLayout.CENTER);
        contentPanel.add(recPanel, BorderLayout.CENTER);

        resultPanel.add(contentPanel, BorderLayout.CENTER);
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private JPanel createResultCard(String icon, String value, String unit, String label) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 10));
                g2d.fillRoundRect(2, 2, getWidth() - 2, getHeight() - 2, 10, 10);

                g2d.setColor(isDarkMode ? new Color(55, 55, 65) : Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 10, 10);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(180, 120));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(iconLabel, gbc);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        card.add(valueLabel, gbc);

        JLabel unitLabel = new JLabel(unit);
        unitLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        unitLabel.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));
        unitLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        card.add(unitLabel, gbc);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.BOLD, 14));
        labelText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        labelText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 3;
        card.add(labelText, gbc);

        return card;
    }

    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double carKm = parseDouble(carKmField.getText(), 0);
                double flightKm = parseDouble(flightKmField.getText(), 0);
                double busKm = parseDouble(busKmField.getText(), 0);
                double trainKm = parseDouble(trainKmField.getText(), 0);
                double electricity = parseDouble(electricityField.getText(), 0);
                double gas = parseDouble(gasField.getText(), 0);
                String diet = (String) dietComboBox.getSelectedItem();

                CarbonFootprintCalculator.CarbonResult result =
                        CarbonFootprintCalculator.calculateFootprint(
                                carKm, flightKm, busKm, trainKm, electricity, gas, diet);

                showResults(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CarbonCalculatorPanel.this,
                        "Te rog să introduci doar numere în câmpuri.",
                        "Eroare de format",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double parseDouble(String text, double defaultValue) {
        try {
            return text.trim().isEmpty() ? defaultValue : Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}