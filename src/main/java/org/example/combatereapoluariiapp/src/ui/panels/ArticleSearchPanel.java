package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.model.SearchResult;
import org.example.combatereapoluariiapp.src.service.ArticleSearchService;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ArticleSearchPanel extends JPanel {
    private boolean isDarkMode;
    private ArticleSearchService searchService;
    private JTextField searchField;
    private JComboBox<String> sourceComboBox;
    private JButton searchButton;
    private JPanel resultsPanel;
    private JScrollPane resultsScrollPane;
    private JLabel statusLabel;

    public ArticleSearchPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        this.searchService = new ArticleSearchService();
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Căutare Articole Științifice");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        JPanel searchPanel = createSearchPanel();
        mainContent.add(searchPanel, BorderLayout.NORTH);

        createResultsArea();
        mainContent.add(resultsScrollPane, BorderLayout.CENTER);

        JPanel statusPanel = createStatusPanel();
        mainContent.add(statusPanel, BorderLayout.SOUTH);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(60, 60, 70, 150) : new Color(240, 250, 255, 180);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2d.setColor(isDarkMode ? new Color(80, 80, 90) : new Color(200, 220, 240));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel instructionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionsPanel.setOpaque(false);

        JLabel instructionIcon = new JLabel("🔍");
        instructionIcon.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel instructions = new JLabel("<html>Caută articole despre poluare, mediu și sustenabilitate din surse academice de încredere</html>");
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        instructions.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : new Color(60, 60, 60));

        instructionsPanel.add(instructionIcon);
        instructionsPanel.add(instructions);
        panel.add(instructionsPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 16));
        searchField.setBackground(isDarkMode ? new Color(50, 50, 60) : Color.WHITE);
        searchField.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isDarkMode ? new Color(80, 80, 90) : new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        searchField.setPreferredSize(new Dimension(0, 50));

        setupPlaceholder();

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlPanel.setOpaque(false);

        String[] sources = {"Toate sursele", "Google Scholar", "PubMed"};
        sourceComboBox = new JComboBox<>(sources);
        sourceComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        sourceComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        sourceComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        sourceComboBox.setPreferredSize(new Dimension(150, 45));

        searchButton = UIUtils.createStyledButton("🔍 Caută", isDarkMode);
        searchButton.setPreferredSize(new Dimension(120, 45));
        searchButton.addActionListener(e -> performSearch());

        controlPanel.add(new JLabel("Sursă:"));
        controlPanel.add(sourceComboBox);
        controlPanel.add(searchButton);

        inputPanel.add(searchField, BorderLayout.CENTER);
        inputPanel.add(controlPanel, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.CENTER);

        return panel;
    }

    private void setupPlaceholder() {
        String placeholder = "ex: renewable energy pollution reduction, carbon emissions urban areas...";

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        searchField.setText(placeholder);
        searchField.setForeground(Color.GRAY);
    }

    private void createResultsArea() {
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setOpaque(false);

        showEmptyState();

        resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setOpaque(false);
        resultsScrollPane.getViewport().setOpaque(false);
        resultsScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR, 1),
                "Rezultate Căutare",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR
        ));
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setPreferredSize(new Dimension(0, 400));
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        statusLabel = new JLabel("Introdu termenii de căutare și apasă 'Caută' pentru a găsi articole științifice");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));

        panel.add(statusLabel);
        return panel;
    }

    private void showEmptyState() {
        resultsPanel.removeAll();

        JPanel emptyPanel = new JPanel(new GridBagLayout());
        emptyPanel.setOpaque(false);
        emptyPanel.setPreferredSize(new Dimension(0, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel emptyIcon = new JLabel("📚");
        emptyIcon.setFont(new Font("Arial", Font.PLAIN, 48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        emptyPanel.add(emptyIcon, gbc);

        JLabel emptyText = new JLabel("<html><center>Caută articole științifice despre:<br>" +
                "• Poluarea aerului, apei și solului<br>" +
                "• Energie regenerabilă și sustenabilitate<br>" +
                "• Schimbări climatice și soluții verzi<br>" +
                "• Reciclare și economia circulară</center></html>");
        emptyText.setFont(new Font("Arial", Font.PLAIN, 16));
        emptyText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        emptyText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        emptyPanel.add(emptyText, gbc);

        resultsPanel.add(emptyPanel);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void performSearch() {
        String query = searchField.getText().trim();

        if (query.isEmpty() || query.equals("ex: renewable energy pollution reduction, carbon emissions urban areas...")) {
            JOptionPane.showMessageDialog(this,
                    "Te rog să introduci termenii de căutare.",
                    "Căutare lipsă",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        searchButton.setEnabled(false);
        searchField.setEnabled(false);
        sourceComboBox.setEnabled(false);
        searchButton.setText("Caută...");
        statusLabel.setText("Se caută articole... Te rog să aștepți.");

        showLoadingState();

        String selectedSource = (String) sourceComboBox.getSelectedItem();
        String searchSource = mapSourceSelection(selectedSource);

        CompletableFuture<List<SearchResult>> searchFuture = searchService.searchArticles(query, searchSource);

        searchFuture.thenAccept(results -> {
            SwingUtilities.invokeLater(() -> {
                displayResults(results, query);

                searchButton.setEnabled(true);
                searchField.setEnabled(true);
                sourceComboBox.setEnabled(true);
                searchButton.setText("🔍 Caută");

                statusLabel.setText("Căutare completă. " + results.size() + " articole găsite pentru: \"" + query + "\"");
            });
        }).exceptionally(throwable -> {
            SwingUtilities.invokeLater(() -> {
                showErrorState(throwable.getMessage());

                searchButton.setEnabled(true);
                searchField.setEnabled(true);
                sourceComboBox.setEnabled(true);
                searchButton.setText("🔍 Caută");

                statusLabel.setText("Eroare la căutare. Te rog să încerci din nou.");
            });
            return null;
        });
    }

    private String mapSourceSelection(String selection) {
        switch (selection) {
            case "Google Scholar": return "scholar";
            case "PubMed": return "pubmed";
            default: return "all";
        }
    }

    private void showLoadingState() {
        resultsPanel.removeAll();

        JPanel loadingPanel = new JPanel(new GridBagLayout());
        loadingPanel.setOpaque(false);
        loadingPanel.setPreferredSize(new Dimension(0, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loadingIcon = new JLabel("⏳");
        loadingIcon.setFont(new Font("Arial", Font.PLAIN, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        loadingPanel.add(loadingIcon, gbc);

        JLabel loadingText = new JLabel("Se caută articole științifice...");
        loadingText.setFont(new Font("Arial", Font.PLAIN, 18));
        loadingText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        gbc.gridy = 1;
        loadingPanel.add(loadingText, gbc);

        resultsPanel.add(loadingPanel);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void showErrorState(String error) {
        resultsPanel.removeAll();

        JPanel errorPanel = new JPanel(new GridBagLayout());
        errorPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel errorIcon = new JLabel("⚠️");
        errorIcon.setFont(new Font("Arial", Font.PLAIN, 32));
        gbc.gridx = 0;
        gbc.gridy = 0;
        errorPanel.add(errorIcon, gbc);

        JLabel errorText = new JLabel("<html><center>Eroare la căutare<br>Te rog să încerci din nou</center></html>");
        errorText.setFont(new Font("Arial", Font.PLAIN, 16));
        errorText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        errorText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        errorPanel.add(errorText, gbc);

        resultsPanel.add(errorPanel);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void displayResults(List<SearchResult> results, String query) {
        resultsPanel.removeAll();

        if (results.isEmpty()) {
            showNoResults(query);
            return;
        }

        resultsPanel.add(Box.createVerticalStrut(10));

        for (SearchResult result : results) {
            resultsPanel.add(createResultCard(result));
            resultsPanel.add(Box.createVerticalStrut(15));
        }

        resultsPanel.add(Box.createVerticalStrut(20));

        resultsPanel.revalidate();
        resultsPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            resultsScrollPane.getVerticalScrollBar().setValue(0);
        });
    }

    private void showNoResults(String query) {
        JPanel noResultsPanel = new JPanel(new GridBagLayout());
        noResultsPanel.setOpaque(false);
        noResultsPanel.setPreferredSize(new Dimension(0, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel noResultsIcon = new JLabel("🔍");
        noResultsIcon.setFont(new Font("Arial", Font.PLAIN, 48));
        gbc.gridx = 0;
        gbc.gridy = 0;
        noResultsPanel.add(noResultsIcon, gbc);

        JLabel noResultsText = new JLabel("<html><center>Nu s-au găsit articole pentru \"" + query + "\"<br><br>" +
                "Încearcă termeni mai generali sau verifică scrierea</center></html>");
        noResultsText.setFont(new Font("Arial", Font.PLAIN, 16));
        noResultsText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        noResultsText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        noResultsPanel.add(noResultsText, gbc);

        resultsPanel.add(noResultsPanel);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private JPanel createResultCard(SearchResult result) {
        JPanel card = new JPanel(new BorderLayout()) {
            private float hoverAnimation = 0.0f;
            private javax.swing.Timer animationTimer;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int shadowOffset = (int) (2 + hoverAnimation * 2);
                g2d.setColor(new Color(0, 0, 0, (int) (10 + hoverAnimation * 15)));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 15, 15);

                Color bgColor = isDarkMode ? new Color(55, 55, 65) : Color.WHITE;
                if (hoverAnimation > 0) {
                    int brightness = (int) (hoverAnimation * 15);
                    bgColor = new Color(
                            Math.min(255, bgColor.getRed() + brightness),
                            Math.min(255, bgColor.getGreen() + brightness),
                            Math.min(255, bgColor.getBlue() + brightness)
                    );
                }
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 15, 15);

                if (hoverAnimation > 0) {
                    g2d.setColor(new Color(ThemeConstants.PRIMARY_COLOR.getRed(), ThemeConstants.PRIMARY_COLOR.getGreen(),
                            ThemeConstants.PRIMARY_COLOR.getBlue(), (int) (hoverAnimation * 80)));
                    g2d.setStroke(new BasicStroke(1.5f));
                    g2d.drawRoundRect(1, 1, getWidth() - shadowOffset - 2, getHeight() - shadowOffset - 2, 15, 15);
                }
            }

            public void startHoverAnimation(boolean hover) {
                if (animationTimer != null) animationTimer.stop();

                animationTimer = new javax.swing.Timer(16, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (hover) {
                            hoverAnimation = Math.min(1.0f, hoverAnimation + 0.1f);
                        } else {
                            hoverAnimation = Math.max(0.0f, hoverAnimation - 0.1f);
                        }
                        repaint();

                        if ((hover && hoverAnimation >= 1.0f) || (!hover && hoverAnimation <= 0.0f)) {
                            animationTimer.stop();
                        }
                    }
                });
                animationTimer.start();
            }
        };

        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        card.setPreferredSize(new Dimension(0, 180));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("<html><b>" + result.getTitle() + "</b></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel sourceLabel = new JLabel(result.getSource());
        sourceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        sourceLabel.setForeground(isDarkMode ? new Color(150, 150, 160) : new Color(100, 100, 100));
        sourceLabel.setHorizontalAlignment(JLabel.RIGHT);
        headerPanel.add(sourceLabel, BorderLayout.EAST);

        card.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel authorYearLabel = new JLabel(result.getAuthor() + " (" + result.getYear() + ")");
        authorYearLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        authorYearLabel.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(80, 80, 80));
        contentPanel.add(authorYearLabel, BorderLayout.NORTH);

        JTextArea snippetArea = new JTextArea(result.getSnippet());
        snippetArea.setFont(new Font("Arial", Font.PLAIN, 14));
        snippetArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        snippetArea.setOpaque(false);
        snippetArea.setEditable(false);
        snippetArea.setWrapStyleWord(true);
        snippetArea.setLineWrap(true);
        snippetArea.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        contentPanel.add(snippetArea, BorderLayout.CENTER);

        card.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton openButton = UIUtils.createStyledButton("📖 Deschide Articol", isDarkMode);
        openButton.setPreferredSize(new Dimension(160, 35));
        openButton.addActionListener(e -> UIUtils.openWebpage(result.getUrl(), this));
        buttonPanel.add(openButton);

        card.add(buttonPanel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    card.getClass().getDeclaredMethod("startHoverAnimation", boolean.class).invoke(card, true);
                } catch (Exception ex) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(ThemeConstants.PRIMARY_COLOR, 1),
                            BorderFactory.createEmptyBorder(19, 24, 19, 24)
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    card.getClass().getDeclaredMethod("startHoverAnimation", boolean.class).invoke(card, false);
                } catch (Exception ex) {
                    card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
                }
            }
        });

        return card;
    }
}