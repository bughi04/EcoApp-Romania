package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PollutionTrendsPanel extends JPanel {
    private boolean isDarkMode;
    private JComboBox<String> pollutantComboBox;
    private JComboBox<String> cityComboBox;
    private JComboBox<String> timeRangeComboBox;
    private JPanel chartPanel;
    private JLabel statsLabel;
    private JLabel trendLabel;
    private JTextArea insightsArea;

    // Simulated pollution data
    private static final String[] POLLUTANTS = {"PM2.5", "PM10", "NO2", "SO2", "CO", "O3"};
    private static final String[] CITIES = {"București", "Cluj-Napoca", "Timișoara", "Iași", "Constanța", "Brașov"};
    private static final String[] TIME_RANGES = {"Ultima săptămână", "Ultima lună", "Ultimele 3 luni", "Ultimul an"};

    // Data storage
    private List<DataPoint> currentData;
    private Random random = new Random();

    public PollutionTrendsPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        this.currentData = new ArrayList<>();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
        generateInitialData();
    }

    private void initializeComponents() {
        // Title
        JLabel title = new JLabel("Evoluția Poluării în Timp");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Main content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        // Controls panel
        JPanel controlsPanel = createControlsPanel();
        mainContent.add(controlsPanel, BorderLayout.NORTH);

        // Split panel: Chart + Stats
        JPanel contentSplit = new JPanel(new BorderLayout());
        contentSplit.setOpaque(false);

        // Chart area
        chartPanel = createChartPanel();
        contentSplit.add(chartPanel, BorderLayout.CENTER);

        // Stats panel
        JPanel statsPanel = createStatsPanel();
        contentSplit.add(statsPanel, BorderLayout.SOUTH);

        mainContent.add(contentSplit, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createControlsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)) {
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
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Pollutant selection
        JLabel pollutantLabel = new JLabel("Poluant:");
        pollutantLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pollutantLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        pollutantComboBox = new JComboBox<>(POLLUTANTS);
        pollutantComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        pollutantComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        pollutantComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        pollutantComboBox.addActionListener(e -> updateChart());

        // City selection
        JLabel cityLabel = new JLabel("Oraș:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cityLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        cityComboBox = new JComboBox<>(CITIES);
        cityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        cityComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        cityComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        cityComboBox.addActionListener(e -> updateChart());

        // Time range selection
        JLabel timeLabel = new JLabel("Perioada:");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timeLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        timeRangeComboBox = new JComboBox<>(TIME_RANGES);
        timeRangeComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        timeRangeComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        timeRangeComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        timeRangeComboBox.addActionListener(e -> updateChart());

        // Update button
        JButton updateBtn = UIUtils.createStyledButton("📊 Actualizează", isDarkMode);
        updateBtn.setPreferredSize(new Dimension(150, 35));
        updateBtn.addActionListener(e -> {
            generateDataForSelection();
            updateChart();
        });

        panel.add(pollutantLabel);
        panel.add(pollutantComboBox);
        panel.add(cityLabel);
        panel.add(cityComboBox);
        panel.add(timeLabel);
        panel.add(timeRangeComboBox);
        panel.add(updateBtn);

        return panel;
    }

    private JPanel createChartPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChart(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR, 2),
                        "Grafic Evoluție",
                        0, 0,
                        new Font("Arial", Font.BOLD, 16),
                        isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR
                ),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(800, 400));

        return panel;
    }

    private void drawChart(Graphics g) {
        if (currentData.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Chart dimensions
        int width = chartPanel.getWidth() - 80;
        int height = chartPanel.getHeight() - 100;
        int startX = 60;
        int startY = height + 30;

        // Background
        g2d.setColor(isDarkMode ? new Color(50, 50, 60, 100) : new Color(250, 250, 250, 150));
        g2d.fillRoundRect(startX - 20, 20, width + 40, height + 40, 10, 10);

        // Find min/max values
        double minValue = currentData.stream().mapToDouble(d -> d.value).min().orElse(0);
        double maxValue = currentData.stream().mapToDouble(d -> d.value).max().orElse(100);
        double range = maxValue - minValue;
        if (range == 0) range = 1;

        // Draw axes
        g2d.setColor(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startX, startY, startX + width, startY); // X-axis
        g2d.drawLine(startX, startY, startX, startY - height); // Y-axis

        // Draw grid lines and labels
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
        g2d.setColor(isDarkMode ? new Color(100, 100, 110) : new Color(200, 200, 200));

        for (int i = 0; i <= 5; i++) {
            int y = startY - (height * i / 5);
            g2d.drawLine(startX, y, startX + width, y);

            double value = minValue + (range * i / 5);
            g2d.setColor(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
            g2d.drawString(String.format("%.1f", value), startX - 45, y + 5);
            g2d.setColor(isDarkMode ? new Color(100, 100, 110) : new Color(200, 200, 200));
        }

        // Draw data line
        if (currentData.size() > 1) {
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(ThemeConstants.PRIMARY_COLOR);

            int[] xPoints = new int[currentData.size()];
            int[] yPoints = new int[currentData.size()];

            for (int i = 0; i < currentData.size(); i++) {
                xPoints[i] = startX + (width * i / (currentData.size() - 1));
                double normalizedValue = (currentData.get(i).value - minValue) / range;
                yPoints[i] = startY - (int)(height * normalizedValue);
            }

            g2d.drawPolyline(xPoints, yPoints, currentData.size());

            // Draw data points
            g2d.setColor(isDarkMode ? Color.WHITE : ThemeConstants.BUTTON_PRESSED_COLOR);
            for (int i = 0; i < currentData.size(); i++) {
                g2d.fillOval(xPoints[i] - 4, yPoints[i] - 4, 8, 8);
                g2d.setColor(ThemeConstants.PRIMARY_COLOR);
                g2d.drawOval(xPoints[i] - 4, yPoints[i] - 4, 8, 8);
                g2d.setColor(isDarkMode ? Color.WHITE : ThemeConstants.BUTTON_PRESSED_COLOR);
            }
        }

        // Draw labels
        g2d.setColor(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        // X-axis label
        String timeLabel = "Timp (" + timeRangeComboBox.getSelectedItem() + ")";
        FontMetrics fm = g2d.getFontMetrics();
        int labelWidth = fm.stringWidth(timeLabel);
        g2d.drawString(timeLabel, startX + (width - labelWidth) / 2, startY + 40);

        // Y-axis label
        String pollutant = (String) pollutantComboBox.getSelectedItem();
        String yLabel = pollutant + " (μg/m³)";
        g2d.rotate(-Math.PI / 2);
        g2d.drawString(yLabel, -(startY - height / 2 + fm.stringWidth(yLabel) / 2), startX - 55);
        g2d.rotate(Math.PI / 2);
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Statistics card
        JPanel statsCard = createStatsCard();
        panel.add(statsCard);

        // Trend analysis card
        JPanel trendCard = createTrendCard();
        panel.add(trendCard);

        // Insights card
        JPanel insightsCard = createInsightsCard();
        panel.add(insightsCard);

        return panel;
    }

    private JPanel createStatsCard() {
        JPanel card = new JPanel(new GridBagLayout()) {
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
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel statsTitle = new JLabel("📊 Statistici");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        statsTitle.setForeground(ThemeConstants.PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(statsTitle, gbc);

        statsLabel = new JLabel("<html><center>Valoare medie: --<br>Valoare maximă: --<br>Valoare minimă: --</center></html>");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statsLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        gbc.gridy = 1;
        card.add(statsLabel, gbc);

        return card;
    }

    private JPanel createTrendCard() {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(70, 70, 80, 150) : new Color(255, 250, 240, 180);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel trendTitle = new JLabel("📈 Tendință");
        trendTitle.setFont(new Font("Arial", Font.BOLD, 16));
        trendTitle.setForeground(ThemeConstants.PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(trendTitle, gbc);

        trendLabel = new JLabel("<html><center>📊 Analizând...<br>Vă rog așteptați</center></html>");
        trendLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        trendLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        gbc.gridy = 1;
        card.add(trendLabel, gbc);

        return card;
    }

    private JPanel createInsightsCard() {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(70, 70, 80, 150) : new Color(240, 245, 255, 180);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel insightsTitle = new JLabel("💡 Perspective");
        insightsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        insightsTitle.setForeground(ThemeConstants.PRIMARY_COLOR);
        card.add(insightsTitle, BorderLayout.NORTH);

        insightsArea = new JTextArea();
        insightsArea.setFont(new Font("Arial", Font.PLAIN, 12));
        insightsArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        insightsArea.setOpaque(false);
        insightsArea.setEditable(false);
        insightsArea.setWrapStyleWord(true);
        insightsArea.setLineWrap(true);
        insightsArea.setText("Selectați parametrii și apăsați 'Actualizează' pentru a vedea analiza detaliată.");

        JScrollPane scrollPane = new JScrollPane(insightsArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(0, 80));

        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private void generateInitialData() {
        generateDataForSelection();
        updateChart();
    }

    private void generateDataForSelection() {
        currentData.clear();
        String timeRange = (String) timeRangeComboBox.getSelectedItem();
        String pollutant = (String) pollutantComboBox.getSelectedItem();

        int dataPoints;
        switch (timeRange) {
            case "Ultima săptămână": dataPoints = 7; break;
            case "Ultima lună": dataPoints = 30; break;
            case "Ultimele 3 luni": dataPoints = 90; break;
            case "Ultimul an": dataPoints = 365; break;
            default: dataPoints = 30;
        }

        // Generate realistic data with some trend
        double baseValue = getPollutantBaseValue(pollutant);
        double trend = (random.nextDouble() - 0.5) * 0.1; // -5% to +5% trend

        for (int i = 0; i < Math.min(dataPoints, 50); i++) { // Limit for performance
            double trendEffect = baseValue * trend * i / dataPoints;
            double noise = (random.nextDouble() - 0.5) * baseValue * 0.3;
            double value = Math.max(0, baseValue + trendEffect + noise);

            currentData.add(new DataPoint(i, value));
        }
    }

    private double getPollutantBaseValue(String pollutant) {
        switch (pollutant) {
            case "PM2.5": return 25.0;
            case "PM10": return 40.0;
            case "NO2": return 30.0;
            case "SO2": return 15.0;
            case "CO": return 1.2;
            case "O3": return 80.0;
            default: return 30.0;
        }
    }

    private void updateChart() {
        if (currentData.isEmpty()) return;

        // Update statistics
        double avg = currentData.stream().mapToDouble(d -> d.value).average().orElse(0);
        double max = currentData.stream().mapToDouble(d -> d.value).max().orElse(0);
        double min = currentData.stream().mapToDouble(d -> d.value).min().orElse(0);

        statsLabel.setText(String.format(
                "<html><center>Valoare medie: %.1f<br>Valoare maximă: %.1f<br>Valoare minimă: %.1f</center></html>",
                avg, max, min
        ));

        // Update trend analysis
        analyzeTrend();

        // Update insights
        generateInsights();

        // Repaint chart
        chartPanel.repaint();
    }

    private void analyzeTrend() {
        if (currentData.size() < 2) return;

        double firstHalf = currentData.subList(0, currentData.size() / 2)
                .stream().mapToDouble(d -> d.value).average().orElse(0);
        double secondHalf = currentData.subList(currentData.size() / 2, currentData.size())
                .stream().mapToDouble(d -> d.value).average().orElse(0);

        double change = ((secondHalf - firstHalf) / firstHalf) * 100;

        String trendIcon;
        String trendText;
        String trendColor;

        if (Math.abs(change) < 2) {
            trendIcon = "➡️";
            trendText = "Stabil";
            trendColor = "blue";
        } else if (change > 0) {
            trendIcon = "📈";
            trendText = String.format("Creștere %.1f%%", change);
            trendColor = "red";
        } else {
            trendIcon = "📉";
            trendText = String.format("Scădere %.1f%%", Math.abs(change));
            trendColor = "green";
        }

        trendLabel.setText(String.format(
                "<html><center>%s %s<br><font color='%s'>%s</font></center></html>",
                trendIcon, trendText, trendColor,
                change > 0 ? "Necesită atenție" : change < -5 ? "Îmbunătățire vizibilă" : "Monitorizare"
        ));
    }

    private void generateInsights() {
        String pollutant = (String) pollutantComboBox.getSelectedItem();
        String city = (String) cityComboBox.getSelectedItem();
        String timeRange = (String) timeRangeComboBox.getSelectedItem();

        double avg = currentData.stream().mapToDouble(d -> d.value).average().orElse(0);

        StringBuilder insights = new StringBuilder();
        insights.append(String.format("Analiza pentru %s în %s:\n\n", pollutant, city));

        // Health implications
        if (pollutant.equals("PM2.5") && avg > 25) {
            insights.append("⚠️ Nivelurile PM2.5 depășesc recomandările OMS (25 μg/m³). ");
            insights.append("Recomandare: limitați activitățile în aer liber.\n\n");
        } else if (pollutant.equals("NO2") && avg > 40) {
            insights.append("🚗 Nivelurile NO2 indică trafic intens. ");
            insights.append("Soluție: promovați transportul public și bicicleta.\n\n");
        }

        // Seasonal patterns
        insights.append("📅 Variații sezoniere: ");
        if (timeRange.contains("săptămână")) {
            insights.append("monitorizați zilnic pentru pattern-uri de trafic.\n");
        } else {
            insights.append("observați tendințele pe termen lung pentru planificare.\n");
        }

        // Action recommendations
        insights.append("\n💡 Recomandări:\n");
        insights.append("• Verificați calitatea aerului dimineața\n");
        insights.append("• Plantați vegetație pentru filtrare naturală\n");
        insights.append("• Susțineți politici de transport verde");

        insightsArea.setText(insights.toString());
    }

    // Data point class
    private static class DataPoint {
        int timeIndex;
        double value;

        DataPoint(int timeIndex, double value) {
            this.timeIndex = timeIndex;
            this.value = value;
        }
    }
}