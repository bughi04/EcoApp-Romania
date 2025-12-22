package org.example.combatereapoluariiapp.src.ui;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.panels.*;
import org.example.combatereapoluariiapp.src.service.AIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;  // Make this an instance variable
    private JPanel contentPanel;    // Make this an instance variable
    private boolean isDarkMode = false;
    private AIService aiService;

    public MainFrame() {
        this.aiService = new AIService();
        setTitle("EcoApp România - Soluții Complete pentru Mediu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 950);
        setLocationRelativeTo(null);

        initializeUI();
        startAnimations();
    }

    private void initializeUI() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                if (!isDarkMode) {
                    GradientPaint gradient = new GradientPaint(0, 0, ThemeConstants.LIGHT_GRADIENT_START,
                            0, getHeight(), ThemeConstants.LIGHT_GRADIENT_END);
                    g2d.setPaint(gradient);
                } else {
                    GradientPaint gradient = new GradientPaint(0, 0, ThemeConstants.DARK_GRADIENT_START,
                            0, getHeight(), ThemeConstants.DARK_GRADIENT_END);
                    g2d.setPaint(gradient);
                }
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();  // Initialize as instance variable
        contentPanel = new JPanel(cardLayout);  // Initialize as instance variable
        contentPanel.setOpaque(false);

        // Add all panels including the new features
        contentPanel.add(new HomePanel(isDarkMode, e -> cardLayout.show(contentPanel, "SOLUTIONS")), "HOME");
        contentPanel.add(new EnhancedArticlesPanel(isDarkMode, aiService), "ARTICLES");
        contentPanel.add(new SolutionsPanel(isDarkMode), "SOLUTIONS");
        contentPanel.add(new ArticleSearchPanel(isDarkMode), "SEARCH");
        contentPanel.add(new QAPanel(isDarkMode, aiService), "QA");
        contentPanel.add(new AboutPanel(isDarkMode), "ABOUT");

        // ENVIRONMENTAL FEATURES
        contentPanel.add(new CarbonCalculatorPanel(isDarkMode), "CARBON");
        contentPanel.add(new PollutionMapPanel(isDarkMode), "MAP");
        contentPanel.add(new EcoTipsPanel(isDarkMode), "TIPS");

        // DATA & INTERACTIVITY FEATURES
        contentPanel.add(new PollutionTrendsPanel(isDarkMode), "TRENDS");
        contentPanel.add(new EnvironmentalQuizPanel(isDarkMode), "QUIZ");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        cardLayout.show(contentPanel, "HOME");
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        // Logo/Title panel with enhanced branding
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);

        JLabel logoLabel = new JLabel("🌱");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 36));

        JLabel appTitle = new JLabel("EcoApp România");
        appTitle.setFont(new Font("Arial", Font.BOLD, 28));
        appTitle.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);

        JLabel subtitle = new JLabel("Platformă Completă pentru Mediu");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        subtitle.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));

        JPanel titleGroup = new JPanel(new BorderLayout());
        titleGroup.setOpaque(false);
        titleGroup.add(appTitle, BorderLayout.NORTH);
        titleGroup.add(subtitle, BorderLayout.SOUTH);

        titlePanel.add(logoLabel);
        titlePanel.add(titleGroup);
        header.add(titlePanel, BorderLayout.WEST);

        // Enhanced navigation panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 5));
        rightPanel.setOpaque(false);

        // Core navigation
        String[] navItems = {"🏠 Acasă", "📚 Articole", "💡 Soluții", "🔍 Căutare", "🤖 AI Q&A"};
        String[] navCards = {"HOME", "ARTICLES", "SOLUTIONS", "SEARCH", "QA"};

        for (int i = 0; i < navItems.length; i++) {
            final String card = navCards[i];
            JButton navBtn = createNavButton(navItems[i], card);
            rightPanel.add(navBtn);
        }

        // Features dropdown menus
        JButton envFeaturesBtn = createEnvironmentalFeaturesDropdown();
        rightPanel.add(envFeaturesBtn);

        JButton dataFeaturesBtn = createDataInteractivityDropdown();
        rightPanel.add(dataFeaturesBtn);

        // About and theme
        JButton aboutBtn = createNavButton("ℹ️ Despre", "ABOUT");
        rightPanel.add(aboutBtn);

        JButton themeBtn = UIUtils.createStyledButton(isDarkMode ? "☀️" : "🌙", isDarkMode);
        themeBtn.setPreferredSize(new Dimension(45, 35));
        themeBtn.setToolTipText(isDarkMode ? "Mod luminos" : "Mod întunecat");
        themeBtn.addActionListener(e -> toggleTheme());
        rightPanel.add(themeBtn);

        header.add(rightPanel, BorderLayout.EAST);

        return header;
    }

    private JButton createNavButton(String text, String card) {
        JButton button = UIUtils.createStyledButton(text, isDarkMode);
        button.setPreferredSize(new Dimension(110, 35));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.addActionListener(e -> cardLayout.show(contentPanel, card));
        return button;
    }

    private JButton createEnvironmentalFeaturesDropdown() {
        JButton button = UIUtils.createStyledButton("🌱 Funcții Eco", isDarkMode);
        button.setPreferredSize(new Dimension(130, 35));
        button.setFont(new Font("Arial", Font.BOLD, 12));

        JPopupMenu popup = createStyledPopupMenu();

        addMenuItem(popup, "🧮 Calculator Carbon", "CARBON",
                "Calculează amprenta ta de carbon personală");
        addMenuItem(popup, "🗺️ Harta Poluării", "MAP",
                "Vezi calitatea aerului în timp real");
        addMenuItem(popup, "💡 Sfaturi Eco Zilnice", "TIPS",
                "Sfaturi practice pentru un stil de viață verde");

        button.addActionListener(e -> popup.show(button, 0, button.getHeight()));
        return button;
    }

    private JButton createDataInteractivityDropdown() {
        JButton button = UIUtils.createStyledButton("📊 Date & Quiz", isDarkMode);
        button.setPreferredSize(new Dimension(130, 35));
        button.setFont(new Font("Arial", Font.BOLD, 12));

        JPopupMenu popup = createStyledPopupMenu();

        addMenuItem(popup, "📈 Evoluția Poluării", "TRENDS",
                "Grafice și analize ale poluării în timp");
        addMenuItem(popup, "🧠 Quiz Ecologic", "QUIZ",
                "Testează-ți cunoștințele despre mediu");

        button.addActionListener(e -> popup.show(button, 0, button.getHeight()));
        return button;
    }

    private JPopupMenu createStyledPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        popup.setBackground(isDarkMode ? new Color(55, 55, 65) : Color.WHITE);
        popup.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isDarkMode ? new Color(80, 80, 90) : new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return popup;
    }

    private void addMenuItem(JPopupMenu popup, String text, String card, String tooltip) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Arial", Font.PLAIN, 13));
        item.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        item.setBackground(isDarkMode ? new Color(55, 55, 65) : Color.WHITE);
        item.setToolTipText(tooltip);
        item.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        item.addActionListener(e -> {
            cardLayout.show(contentPanel, card);
            popup.setVisible(false);
        });

        // Add hover effects
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(isDarkMode ? new Color(75, 75, 85) : new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                item.setBackground(isDarkMode ? new Color(55, 55, 65) : Color.WHITE);
            }
        });

        popup.add(item);
    }

    private void toggleTheme() {
        isDarkMode = !isDarkMode;

        // Show loading indicator
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingUtilities.invokeLater(() -> {
            mainPanel.removeAll();

            JPanel headerPanel = createHeaderPanel();
            mainPanel.add(headerPanel, BorderLayout.NORTH);

            cardLayout = new CardLayout();
            contentPanel = new JPanel(cardLayout);
            contentPanel.setOpaque(false);

            // Recreate all panels with new theme
            contentPanel.add(new HomePanel(isDarkMode, e -> cardLayout.show(contentPanel, "SOLUTIONS")), "HOME");
            contentPanel.add(new EnhancedArticlesPanel(isDarkMode, aiService), "ARTICLES");
            contentPanel.add(new SolutionsPanel(isDarkMode), "SOLUTIONS");
            contentPanel.add(new ArticleSearchPanel(isDarkMode), "SEARCH");
            contentPanel.add(new QAPanel(isDarkMode, aiService), "QA");
            contentPanel.add(new AboutPanel(isDarkMode), "ABOUT");

            // Environmental features
            contentPanel.add(new CarbonCalculatorPanel(isDarkMode), "CARBON");
            contentPanel.add(new PollutionMapPanel(isDarkMode), "MAP");
            contentPanel.add(new EcoTipsPanel(isDarkMode), "TIPS");

            // Data & interactivity features
            contentPanel.add(new PollutionTrendsPanel(isDarkMode), "TRENDS");
            contentPanel.add(new EnvironmentalQuizPanel(isDarkMode), "QUIZ");

            mainPanel.add(contentPanel, BorderLayout.CENTER);

            SwingUtilities.updateComponentTreeUI(this);
            mainPanel.revalidate();
            mainPanel.repaint();

            cardLayout.show(contentPanel, "HOME");
            setCursor(Cursor.getDefaultCursor());
        });
    }

    private void startAnimations() {
        Timer timer = new Timer(100, e -> repaint());
        timer.start();

        // Add welcome message
        SwingUtilities.invokeLater(() -> {
            Timer welcomeTimer = new Timer(2000, e -> {
                showWelcomeMessage();
                ((Timer) e.getSource()).stop();
            });
            welcomeTimer.setRepeats(false);
            welcomeTimer.start();
        });
    }

    private void showWelcomeMessage() {
        JDialog welcomeDialog = new JDialog(this, "Bun venit la EcoApp România!", true);
        welcomeDialog.setSize(500, 400);
        welcomeDialog.setLocationRelativeTo(this);
        welcomeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel dialogPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, isDarkMode ? new Color(60, 80, 60) : new Color(220, 255, 220),
                        getWidth(), getHeight(), isDarkMode ? new Color(40, 60, 80) : new Color(200, 230, 255)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Welcome content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Icon
        JLabel iconLabel = new JLabel("🌍");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(iconLabel, gbc);

        // Title
        JLabel titleLabel = new JLabel("EcoApp România");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        contentPanel.add(titleLabel, gbc);

        // Description
        JTextArea descArea = new JTextArea(
                "Platforma completă pentru protecția mediului în România!\n\n" +
                        "🧮 Calculator amprentă de carbon\n" +
                        "🗺️ Harta poluării în timp real\n" +
                        "📈 Analize și grafice de evoluție\n" +
                        "🧠 Quiz-uri educative interactive\n" +
                        "🤖 Asistent AI specializat\n" +
                        "💡 Sfaturi eco zilnice\n\n" +
                        "Să începem să protejăm mediul împreună!"
        );
        descArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setPreferredSize(new Dimension(350, 200));
        gbc.gridy = 2;
        contentPanel.add(descArea, gbc);

        dialogPanel.add(contentPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton exploreBtn = UIUtils.createStyledButton("🚀 Explorează", isDarkMode);
        exploreBtn.setPreferredSize(new Dimension(120, 40));
        exploreBtn.addActionListener(e -> {
            welcomeDialog.dispose();
            // Now this will work because cardLayout and contentPanel are instance variables
            cardLayout.show(this.contentPanel, "TIPS");
        });
        buttonPanel.add(exploreBtn);

        JButton closeBtn = UIUtils.createStyledButton("Închide", isDarkMode);
        closeBtn.setPreferredSize(new Dimension(100, 40));
        closeBtn.addActionListener(e -> welcomeDialog.dispose());
        buttonPanel.add(closeBtn);

        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        welcomeDialog.add(dialogPanel);
        welcomeDialog.setVisible(true);
    }

    // Add method to show feature tours
    public void showFeatureTour(String featureName) {
        JDialog tourDialog = new JDialog(this, "Ghid Rapid - " + featureName, true);
        tourDialog.setSize(400, 300);
        tourDialog.setLocationRelativeTo(this);

        JPanel tourPanel = new JPanel(new BorderLayout());
        tourPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tourPanel.setBackground(isDarkMode ? new Color(50, 50, 60) : Color.WHITE);

        String tourContent = getTourContent(featureName);

        JTextArea tourText = new JTextArea(tourContent);
        tourText.setFont(new Font("Arial", Font.PLAIN, 14));
        tourText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        tourText.setOpaque(false);
        tourText.setEditable(false);
        tourText.setWrapStyleWord(true);
        tourText.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(tourText);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        tourPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = UIUtils.createStyledButton("Am înțeles!", isDarkMode);
        closeButton.addActionListener(e -> tourDialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        tourPanel.add(buttonPanel, BorderLayout.SOUTH);

        tourDialog.add(tourPanel);
        tourDialog.setVisible(true);
    }

    private String getTourContent(String featureName) {
        switch (featureName) {
            case "CARBON":
                return "🧮 CALCULATOR AMPRENTĂ DE CARBON\n\n" +
                        "Calculează impactul tău asupra mediului:\n" +
                        "• Introdu datele despre transport, energie și stil de viață\n" +
                        "• Vezi rezultatele detaliate pe categorii\n" +
                        "• Primește recomandări personalizate\n" +
                        "• Compară cu media națională\n\n" +
                        "Obiectiv: Sub 2 tone CO2/an pentru compatibilitate climatică!";

            case "MAP":
                return "🗺️ HARTA POLUĂRII\n\n" +
                        "Monitorizează calitatea aerului în România:\n" +
                        "• Selectează orașul din dropdown\n" +
                        "• Vezi indicele AQI și poluanții principali\n" +
                        "• Citește recomandările specifice\n" +
                        "• Observă harta interactivă cu orașe marcate\n\n" +
                        "Culorile: Verde = Bun, Portocaliu = Moderat, Roșu = Nesănătos";

            case "TRENDS":
                return "📈 EVOLUȚIA POLUĂRII\n\n" +
                        "Analizează tendințele în timp:\n" +
                        "• Alege poluantul, orașul și perioada\n" +
                        "• Studiază graficul interactiv\n" +
                        "• Citește analiza de tendință\n" +
                        "• Află statistici și perspective\n\n" +
                        "Perfect pentru a înțelege pattern-urile poluării!";

            case "QUIZ":
                return "🧠 QUIZ ECOLOGIC\n\n" +
                        "Testează cunoștințele despre mediu:\n" +
                        "• Alege nivelul de dificultate\n" +
                        "• Răspunde la 10 întrebări\n" +
                        "• Citește explicațiile detaliate\n" +
                        "• Vezi scorul final și recomandări\n\n" +
                        "Categorii: Ușor, Mediu, Avansat - toate cu context românesc!";

            default:
                return "Explorează această funcție pentru a afla mai multe despre protecția mediului!";
        }
    }
}