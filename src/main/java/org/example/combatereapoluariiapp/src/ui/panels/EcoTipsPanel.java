package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EcoTipsPanel extends JPanel {
    private boolean isDarkMode;
    private JLabel tipIconLabel;
    private JTextArea tipTextArea;
    private JLabel tipCategoryLabel;
    private JLabel tipNumberLabel;
    private Random random = new Random();

    // Eco-friendly tips database
    private static final EcoTip[] ECO_TIPS = {
            new EcoTip("🌱", "ENERGIE", "Înlocuiește becurile incandescente cu LED-uri pentru a reduce consumul energetic cu 80% și a economisi 150 lei anual pe o gospodărie medie."),
            new EcoTip("💧", "APĂ", "Instalează economizoare de apă la robinete și duș - poți reduce consumul cu 30% și economisi până la 200 lei pe an la factura de apă."),
            new EcoTip("♻️", "RECICLARE", "Separă deșeurile corect: plasticul în galben, hârtia în albastru, sticla în verde. România reciclează doar 13% vs 48% media UE."),
            new EcoTip("🚗", "TRANSPORT", "O zi pe săptămână fără mașină poate economisi 520 kg CO2 anual și 2.400 lei combustibil. Folosește bicicleta sau transportul public."),
            new EcoTip("🌿", "PLANTE", "Plantele de interior ca ficus sau sansevieria pot filtra 87% din toxinele aerului în 24 de ore și îmbunătățesc calitatea oxigenului."),
            new EcoTip("🛒", "CUMPĂRĂTURI", "Folosește pungi reutilizabile - o pungă de pânză înlocuiește 1000 de pungi de plastic pe durata vieții și previne poluarea oceanelor."),
            new EcoTip("🏠", "ÎNCĂLZIRE", "Reduce temperatura cu 1°C și economisești 6% din factura de încălzire. Izolația termică poate reduce costurile cu până la 40%."),
            new EcoTip("📱", "TEHNOLOGIE", "Oprește aparatele din priză când nu le folosești - în standby consumă 10% din energia totală a casei, aproximativ 100 lei/an."),
            new EcoTip("🌅", "SOLAR", "România are 210 zile însorite/an - un sistem fotovoltaic de 3kW produce 4.500 kWh anual și se amortizează în 6-8 ani."),
            new EcoTip("🌳", "PĂDURI", "Un copac absorb 22 kg CO2 anual și produce oxigen pentru 2 persoane. Participă la plantările din Ziua Pădurii (15 martie)."),
            new EcoTip("🚲", "BICICLETĂ", "5 km cu bicicleta vs mașina economisesc 1 kg CO2 și ard 200 calorii. România are peste 400 km piste urbane în orașele mari."),
            new EcoTip("💡", "SMART HOME", "Termostatul inteligent poate reduce factura de încălzire cu 23%. Programează temperatura: 20°C ziua, 16°C noaptea."),
            new EcoTip("🍃", "AER", "Aerisește casa 10 minute dimineața pentru aer curat. Plantele ca English Ivy elimină 94% din particulele în suspensie."),
            new EcoTip("⚡", "ELECTRONICE", "Laptopul consumă 75% mai puțină energie decât PC-ul desktop. La cumpărarea de electronice, alege clasa energetică A+++."),
            new EcoTip("🌊", "OCEAN", "Evită produsele cu microplastic (scruburi, paste de dinți cu granule). România contribuie cu 4.6 kg plastic/persoană/an în Marea Neagră."),
            new EcoTip("🥬", "ALIMENTAȚIE", "O zi vegetariană pe săptămână economisește 1.900L apă și 3.3 kg CO2. Produsele locale reduc transportul cu până la 90%."),
            new EcoTip("🔋", "BATERII", "Bateriile rechargabile se pot folosi de 1000 ori vs 1 dată cele obișnuite. În 15 ani economisești 4.000 lei și protejezi mediul."),
            new EcoTip("🚿", "DUȘ", "Dus de 5 minute vs 10 minute economisește 75L apă caldă și 2 lei per duș. Anual: 27.000L apă și 730 lei economisiți."),
            new EcoTip("🎯", "OBIECTIVE", "România trebuie să atingă 32% energie regenerabilă până în 2030. Tu poți contribui cu panouri solare prin Casa Verde Fotovoltaice."),
            new EcoTip("🌡️", "CLIMA", "Schimbările climatice vor crește temperatura în România cu 2-5°C până în 2100. Fiecare acțiune individuală contează pentru viitor."),
            new EcoTip("🏭", "INDUSTRIE", "Susține companiile cu certificări ecologice. Produsele cu eticheta EU Ecolabel reduc impactul asupra mediului cu până la 25%."),
            new EcoTip("💻", "DIGITAL", "E-mail-urile stochează 4g CO2 fiecare. Șterge e-mail-urile vechi și dezabonează-te de newsletter-uri pentru a reduce amprenta digitală."),
            new EcoTip("🌈", "DIVERSITATE", "România are 3.700 specii de plante și 33.800 specii animale. Protejează biodiversitatea alegând produse fără testing pe animale."),
            new EcoTip("🧼", "CURĂȚENIE", "Detergenții ecologici se descompun în 7 zile vs 6 luni cei convențională. Protejează apele subterane și ecosistemele acvatice."),
            new EcoTip("🚮", "DEȘEURI", "România produce 280 kg deșeuri/persoană/an. Compostarea deșeurilor organice reduce cu 30% cantitatea și creează îngrășământ natural.")
    };

    private static class EcoTip {
        String icon;
        String category;
        String text;

        EcoTip(String icon, String category, String text) {
            this.icon = icon;
            this.category = category;
            this.text = text;
        }
    }

    public EcoTipsPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
        showRandomTip();
    }

    private void initializeComponents() {
        // Title
        JLabel title = new JLabel("Sfaturi Eco Zilnice");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Main content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        // Tip display panel
        JPanel tipDisplayPanel = createTipDisplayPanel();
        mainContent.add(tipDisplayPanel, BorderLayout.CENTER);

        // Controls panel
        JPanel controlsPanel = createControlsPanel();
        mainContent.add(controlsPanel, BorderLayout.SOUTH);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createTipDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, isDarkMode ? new Color(60, 80, 60) : new Color(220, 255, 220),
                        getWidth(), getHeight(), isDarkMode ? new Color(40, 60, 80) : new Color(200, 230, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                // Subtle border
                g2d.setColor(isDarkMode ? new Color(100, 120, 100) : new Color(34, 139, 34, 100));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setPreferredSize(new Dimension(800, 400));

        // Header with tip number and category
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        tipNumberLabel = new JLabel("Sfat #1");
        tipNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tipNumberLabel.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));
        headerPanel.add(tipNumberLabel, BorderLayout.WEST);

        tipCategoryLabel = new JLabel("ENERGIE");
        tipCategoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tipCategoryLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        tipCategoryLabel.setHorizontalAlignment(JLabel.RIGHT);
        headerPanel.add(tipCategoryLabel, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Main tip content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Large icon
        tipIconLabel = new JLabel("🌱");
        tipIconLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        tipIconLabel.setHorizontalAlignment(JLabel.CENTER);
        tipIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        contentPanel.add(tipIconLabel, BorderLayout.NORTH);

        // Tip text
        tipTextArea = new JTextArea();
        tipTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        tipTextArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        tipTextArea.setOpaque(false);
        tipTextArea.setEditable(false);
        tipTextArea.setWrapStyleWord(true);
        tipTextArea.setLineWrap(true);
        tipTextArea.setFocusable(false);

        JScrollPane textScrollPane = new JScrollPane(tipTextArea);
        textScrollPane.setOpaque(false);
        textScrollPane.getViewport().setOpaque(false);
        textScrollPane.setBorder(null);
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentPanel.add(textScrollPane, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createControlsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setOpaque(false);

        // Previous tip button
        JButton prevButton = UIUtils.createStyledButton("⬅️ Sfat Anterior", isDarkMode);
        prevButton.setPreferredSize(new Dimension(180, 50));
        prevButton.addActionListener(e -> showPreviousTip());
        panel.add(prevButton);

        // Random tip button
        JButton randomButton = UIUtils.createStyledButton("🎲 Sfat Aleatoriu", isDarkMode);
        randomButton.setPreferredSize(new Dimension(180, 50));
        randomButton.addActionListener(e -> showRandomTip());
        panel.add(randomButton);

        // Next tip button
        JButton nextButton = UIUtils.createStyledButton("Sfat Următor ➡️", isDarkMode);
        nextButton.setPreferredSize(new Dimension(180, 50));
        nextButton.addActionListener(e -> showNextTip());
        panel.add(nextButton);

        // Category filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Filtrează:");
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        filterLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        String[] categories = {"TOATE", "ENERGIE", "APĂ", "TRANSPORT", "RECICLARE", "PLANTE", "ALIMENTAȚIE"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        categoryCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryCombo.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        categoryCombo.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        categoryCombo.addActionListener(e -> {
            String selectedCategory = (String) categoryCombo.getSelectedItem();
            if ("TOATE".equals(selectedCategory)) {
                showRandomTip();
            } else {
                showTipByCategory(selectedCategory);
            }
        });

        filterPanel.add(filterLabel);
        filterPanel.add(categoryCombo);

        // Statistics panel
        JPanel statsPanel = createStatsPanel();

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(filterPanel, BorderLayout.NORTH);
        bottomPanel.add(statsPanel, BorderLayout.SOUTH);

        panel.add(bottomPanel);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(50, 50, 60, 120) : new Color(240, 240, 240, 120);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setPreferredSize(new Dimension(600, 80));

        // Quick stats
        panel.add(createMiniStatCard("🌱", String.valueOf(ECO_TIPS.length), "sfaturi"));
        panel.add(createMiniStatCard("📊", "25+", "categorii"));
        panel.add(createMiniStatCard("💰", "2000+", "lei economii/an"));
        panel.add(createMiniStatCard("🌍", "1.5t", "CO2 redus/an"));

        return panel;
    }

    private JPanel createMiniStatCard(String icon, String value, String label) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(iconLabel, gbc);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        valueLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        card.add(valueLabel, gbc);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.PLAIN, 10));
        labelText.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));
        labelText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        card.add(labelText, gbc);

        return card;
    }

    private int currentTipIndex = 0;

    private void showRandomTip() {
        currentTipIndex = random.nextInt(ECO_TIPS.length);
        displayTip(ECO_TIPS[currentTipIndex]);
    }

    private void showNextTip() {
        currentTipIndex = (currentTipIndex + 1) % ECO_TIPS.length;
        displayTip(ECO_TIPS[currentTipIndex]);
    }

    private void showPreviousTip() {
        currentTipIndex = (currentTipIndex - 1 + ECO_TIPS.length) % ECO_TIPS.length;
        displayTip(ECO_TIPS[currentTipIndex]);
    }

    private void showTipByCategory(String category) {
        // Find tips matching the category
        java.util.List<Integer> matchingIndices = new java.util.ArrayList<>();
        for (int i = 0; i < ECO_TIPS.length; i++) {
            if (ECO_TIPS[i].category.equals(category)) {
                matchingIndices.add(i);
            }
        }

        if (!matchingIndices.isEmpty()) {
            int randomIndex = matchingIndices.get(random.nextInt(matchingIndices.size()));
            currentTipIndex = randomIndex;
            displayTip(ECO_TIPS[currentTipIndex]);
        } else {
            showRandomTip(); // Fallback to random tip
        }
    }

    private void displayTip(EcoTip tip) {
        tipIconLabel.setText(tip.icon);
        tipCategoryLabel.setText(tip.category);
        tipTextArea.setText(tip.text);
        tipNumberLabel.setText("Sfat #" + (currentTipIndex + 1));

        // Add a subtle animation effect
        SwingUtilities.invokeLater(() -> {
            tipIconLabel.setFont(new Font("Arial", Font.PLAIN, 60));
            Timer timer = new Timer(100, new ActionListener() {
                private int step = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    step++;
                    int size = 60 + (int)(20 * Math.sin(step * 0.3));
                    tipIconLabel.setFont(new Font("Arial", Font.PLAIN, Math.max(60, Math.min(80, size))));
                    if (step > 20) {
                        ((Timer)e.getSource()).stop();
                        tipIconLabel.setFont(new Font("Arial", Font.PLAIN, 80));
                    }
                }
            });
            timer.start();
        });

        repaint();
    }
}