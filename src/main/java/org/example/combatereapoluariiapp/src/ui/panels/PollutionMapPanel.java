package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PollutionMapPanel extends JPanel {
    private boolean isDarkMode;
    private JPanel mapContainer;
    private JComboBox<String> cityComboBox;
    private JLabel aqiValueLabel;
    private JLabel aqiStatusLabel;
    private JLabel pm25Label;
    private JLabel pm10Label;
    private JLabel coLabel;
    private JLabel no2Label;
    private JTextArea recommendationsArea;

    // Romanian cities with simulated pollution data
    private static final String[][] ROMANIAN_CITIES_DATA = {
            {"București", "78", "Moderat", "35", "45", "1.2", "38", "Evită exercițiile în aer liber între 10-16. Folosește transportul public."},
            {"Cluj-Napoca", "65", "Moderat", "28", "38", "0.9", "32", "Calitate acceptabilă. Preferă mersul pe jos în parcuri."},
            {"Timișoara", "58", "Moderat", "24", "35", "0.8", "28", "Condiții bune pentru activități în aer liber."},
            {"Iași", "72", "Moderat", "32", "42", "1.1", "35", "Limitează timpul petrecut în trafic intens."},
            {"Constanța", "52", "Bun", "20", "30", "0.7", "25", "Calitate bună a aerului datorită briza marine."},
            {"Brașov", "68", "Moderat", "30", "40", "1.0", "33", "Poluare moderată din cauza traficului și industriei."},
            {"Galați", "82", "Nesănătos", "38", "50", "1.4", "42", "Evită activitățile în aer liber. Folosește masca FFP2."},
            {"Ploiești", "75", "Moderat", "34", "44", "1.3", "37", "Poluare din industria petrolieră. Limitează expunerea."},
            {"Craiova", "60", "Moderat", "26", "36", "0.9", "30", "Calitate acceptabilă, dar atenție la orele de vârf."},
            {"Oradea", "55", "Moderat", "22", "32", "0.8", "26", "Condiții relativ bune pentru activități exterioare."}
    };

    public PollutionMapPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
    }

    private void initializeComponents() {
        // Title
        JLabel title = new JLabel("Harta Poluării în România");
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

        // Split content: Map + Data
        JPanel contentSplit = new JPanel(new GridLayout(1, 2, 20, 0));
        contentSplit.setOpaque(false);

        // Map panel
        mapContainer = createMapPanel();
        contentSplit.add(mapContainer);

        // Data panel
        JPanel dataPanel = createDataPanel();
        contentSplit.add(dataPanel);

        mainContent.add(contentSplit, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        // Load default city data
        updateCityData("București");
    }

    private JPanel createControlsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
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

        JLabel selectLabel = new JLabel("🏙️ Selectează orașul:");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);

        String[] cities = new String[ROMANIAN_CITIES_DATA.length];
        for (int i = 0; i < ROMANIAN_CITIES_DATA.length; i++) {
            cities[i] = ROMANIAN_CITIES_DATA[i][0];
        }

        cityComboBox = new JComboBox<>(cities);
        cityComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        cityComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        cityComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        cityComboBox.setPreferredSize(new Dimension(200, 35));
        cityComboBox.addActionListener(e -> {
            String selectedCity = (String) cityComboBox.getSelectedItem();
            updateCityData(selectedCity);
        });

        JButton refreshBtn = UIUtils.createStyledButton("🔄 Actualizează", isDarkMode);
        refreshBtn.setPreferredSize(new Dimension(150, 35));
        refreshBtn.addActionListener(e -> {
            String selectedCity = (String) cityComboBox.getSelectedItem();
            updateCityData(selectedCity);
            JOptionPane.showMessageDialog(this, "Datele au fost actualizate pentru " + selectedCity);
        });

        panel.add(selectLabel);
        panel.add(cityComboBox);
        panel.add(refreshBtn);

        return panel;
    }

    private JPanel createMapPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(50, 50, 60) : Color.WHITE;
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw Romania outline (accurate coordinates)
                drawRomaniaMap(g2d);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(400, 400));

        JLabel mapTitle = new JLabel("Harta Interactivă România");
        mapTitle.setFont(new Font("Arial", Font.BOLD, 18));
        mapTitle.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        mapTitle.setHorizontalAlignment(JLabel.CENTER);
        panel.add(mapTitle, BorderLayout.NORTH);

        return panel;
    }

    private void drawRomaniaMap(Graphics2D g2d) {
        // Optional: clear background
        g2d.setColor(isDarkMode ? new Color(70, 70, 80, 100) : new Color(200, 220, 240, 100));
        g2d.fillRect(0, 0, mapContainer.getWidth(), mapContainer.getHeight());

        // Draw only city markers
        markCityOnMap(g2d, "București", 220, 280, getAQIColor(78));
        markCityOnMap(g2d, "Cluj-Napoca", 160, 150, getAQIColor(65));
        markCityOnMap(g2d, "Timișoara", 90, 220, getAQIColor(58));
        markCityOnMap(g2d, "Iași", 280, 130, getAQIColor(72));
        markCityOnMap(g2d, "Constanța", 320, 290, getAQIColor(52));
        markCityOnMap(g2d, "Brașov", 200, 200, getAQIColor(68));
        markCityOnMap(g2d, "Galați", 290, 200, getAQIColor(82));
        markCityOnMap(g2d, "Ploiești", 230, 250, getAQIColor(75));
        markCityOnMap(g2d, "Craiova", 140, 300, getAQIColor(60));
        markCityOnMap(g2d, "Oradea", 80, 110, getAQIColor(55));

        drawMapLegend(g2d);
    }

    private void markCityOnMap(Graphics2D g2d, String cityName, int x, int y, Color color) {
        // City dot
        g2d.setColor(color);
        g2d.fillOval(x - 8, y - 8, 16, 16);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x - 8, y - 8, 16, 16);

        // City name
        g2d.setColor(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(cityName);
        g2d.drawString(cityName, x - textWidth/2, y + 25);
    }

    private void drawMapLegend(Graphics2D g2d) {
        int legendX = 20;
        int legendY = 320;

        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.setColor(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        g2d.drawString("Calitatea aerului:", legendX, legendY);

        // Legend items
        String[] legendLabels = {"Bun (0-50)", "Moderat (51-100)", "Nesănătos (101+)"};
        Color[] legendColors = {new Color(46, 125, 50), new Color(255, 152, 0), new Color(211, 47, 47)};

        for (int i = 0; i < legendLabels.length; i++) {
            int itemY = legendY + 20 + (i * 20);
            g2d.setColor(legendColors[i]);
            g2d.fillOval(legendX, itemY - 8, 12, 12);
            g2d.setColor(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
            g2d.drawString(legendLabels[i], legendX + 20, itemY + 3);
        }
    }

    private Color getAQIColor(int aqi) {
        if (aqi <= 50) return new Color(46, 125, 50);        // Green - Good
        else if (aqi <= 100) return new Color(255, 152, 0);  // Orange - Moderate
        else return new Color(211, 47, 47);                  // Red - Unhealthy
    }

    private JPanel createDataPanel() {
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

        JLabel dataTitle = new JLabel("Date Calitate Aer");
        dataTitle.setFont(new Font("Arial", Font.BOLD, 20));
        dataTitle.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        dataTitle.setHorizontalAlignment(JLabel.CENTER);
        panel.add(dataTitle, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // AQI Display
        JPanel aqiPanel = createAQIPanel();
        contentPanel.add(aqiPanel, BorderLayout.NORTH);

        // Pollutants Panel
        JPanel pollutantsPanel = createPollutantsPanel();
        contentPanel.add(pollutantsPanel, BorderLayout.CENTER);

        // Recommendations Panel
        JPanel recPanel = createRecommendationsPanel();
        contentPanel.add(recPanel, BorderLayout.SOUTH);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAQIPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel aqiLabel = new JLabel("Indicele Calității Aerului (AQI)");
        aqiLabel.setFont(new Font("Arial", Font.BOLD, 16));
        aqiLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(aqiLabel, gbc);

        aqiValueLabel = new JLabel("78");
        aqiValueLabel.setFont(new Font("Arial", Font.BOLD, 48));
        aqiValueLabel.setForeground(getAQIColor(78));
        aqiValueLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        panel.add(aqiValueLabel, gbc);

        aqiStatusLabel = new JLabel("Moderat");
        aqiStatusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        aqiStatusLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        aqiStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        panel.add(aqiStatusLabel, gbc);

        return panel;
    }

    private JPanel createPollutantsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR, 1),
                "Poluanți Principali (μg/m³)",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR
        ));

        // PM2.5
        JPanel pm25Panel = createPollutantCard("PM2.5", "35", "Particule fine");
        pm25Label = (JLabel) pm25Panel.getComponent(1);
        panel.add(pm25Panel);

        // PM10
        JPanel pm10Panel = createPollutantCard("PM10", "45", "Particule mari");
        pm10Label = (JLabel) pm10Panel.getComponent(1);
        panel.add(pm10Panel);

        // CO
        JPanel coPanel = createPollutantCard("CO", "1.2", "Monoxid carbon");
        coLabel = (JLabel) coPanel.getComponent(1);
        panel.add(coPanel);

        // NO2
        JPanel no2Panel = createPollutantCard("NO2", "38", "Dioxid azot");
        no2Label = (JLabel) no2Panel.getComponent(1);
        panel.add(no2Panel);

        return panel;
    }

    private JPanel createPollutantCard(String name, String value, String description) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(isDarkMode ? new Color(55, 55, 65) : Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2d.setColor(isDarkMode ? new Color(80, 80, 90) : new Color(220, 220, 220));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        card.add(nameLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        card.add(valueLabel, BorderLayout.CENTER);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descLabel.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));
        descLabel.setHorizontalAlignment(JLabel.CENTER);
        card.add(descLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createRecommendationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR, 1),
                "💡 Recomandări",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR
        ));

        recommendationsArea = new JTextArea();
        recommendationsArea.setFont(new Font("Arial", Font.PLAIN, 13));
        recommendationsArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        recommendationsArea.setOpaque(false);
        recommendationsArea.setEditable(false);
        recommendationsArea.setWrapStyleWord(true);
        recommendationsArea.setLineWrap(true);
        recommendationsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(recommendationsArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0, 80));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void updateCityData(String cityName) {
        // Find city data
        String[] cityData = null;
        for (String[] city : ROMANIAN_CITIES_DATA) {
            if (city[0].equals(cityName)) {
                cityData = city;
                break;
            }
        }

        if (cityData != null) {
            int aqi = Integer.parseInt(cityData[1]);

            aqiValueLabel.setText(cityData[1]);
            aqiValueLabel.setForeground(getAQIColor(aqi));
            aqiStatusLabel.setText(cityData[2]);

            pm25Label.setText(cityData[3]);
            pm10Label.setText(cityData[4]);
            coLabel.setText(cityData[5]);
            no2Label.setText(cityData[6]);

            recommendationsArea.setText(cityData[7]);

            // Repaint map to highlight selected city
            mapContainer.repaint();
        }
    }
}