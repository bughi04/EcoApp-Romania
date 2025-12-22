package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.data.DataRepository;
import org.example.combatereapoluariiapp.src.model.Article;
import org.example.combatereapoluariiapp.src.service.AIService;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CompletableFuture;

public class EnhancedArticlesPanel extends JPanel {
    private boolean isDarkMode;
    private AIService aiService;

    public EnhancedArticlesPanel(boolean isDarkMode, AIService aiService) {
        this.isDarkMode = isDarkMode;
        this.aiService = aiService;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Articole Științifice cu Rezumat AI");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel gridContainer = new JPanel(new BorderLayout());
        gridContainer.setOpaque(false);

        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 25, 25));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        for (Article article : DataRepository.getArticles()) {
            gridPanel.add(createEnhancedArticleCard(article));
        }

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gridContainer.add(scrollPane, BorderLayout.CENTER);
        add(gridContainer, BorderLayout.CENTER);
    }

    private JPanel createEnhancedArticleCard(Article article) {
        JPanel card = new JPanel(new BorderLayout()) {
            private float hoverAnimation = 0.0f;
            private Timer animationTimer;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int shadowOffset = (int) (3 + hoverAnimation * 2);
                g2d.setColor(new Color(0, 0, 0, (int) (15 + hoverAnimation * 10)));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, 20, 20);

                Color bgColor = isDarkMode ? new Color(55, 55, 65) : Color.WHITE;
                if (hoverAnimation > 0) {
                    int glowIntensity = (int) (hoverAnimation * 20);
                    bgColor = new Color(
                            Math.min(255, bgColor.getRed() + glowIntensity),
                            Math.min(255, bgColor.getGreen() + glowIntensity),
                            Math.min(255, bgColor.getBlue() + glowIntensity)
                    );
                }
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, 20, 20);

                if (hoverAnimation > 0) {
                    g2d.setColor(new Color(ThemeConstants.PRIMARY_COLOR.getRed(), ThemeConstants.PRIMARY_COLOR.getGreen(),
                            ThemeConstants.PRIMARY_COLOR.getBlue(), (int) (hoverAnimation * 100)));
                    g2d.setStroke(new BasicStroke(2.0f));
                    g2d.drawRoundRect(1, 1, getWidth() - shadowOffset - 2, getHeight() - shadowOffset - 2, 20, 20);
                }
            }

            public void startHoverAnimation(boolean hover) {
                if (animationTimer != null) animationTimer.stop();

                animationTimer = new Timer(16, new ActionListener() {
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
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(650, 650)); // Increased height for AI summary

        // Article title
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'><b>" + article.getTitle() + "</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        card.add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Citation panel
        JTextArea citation = new JTextArea(article.getCitation());
        citation.setFont(new Font("Arial", Font.ITALIC, 14));
        citation.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(80, 80, 80));
        citation.setOpaque(false);
        citation.setEditable(false);
        citation.setWrapStyleWord(true);
        citation.setLineWrap(true);
        citation.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        JPanel citationPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(isDarkMode ? new Color(45, 45, 55, 100) : new Color(245, 245, 245, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        citationPanel.setOpaque(false);
        citationPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        citationPanel.add(citation, BorderLayout.CENTER);
        contentPanel.add(citationPanel, BorderLayout.NORTH);

        // Tabbed content for Summary vs Full Text
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(isDarkMode ? new Color(55, 55, 65) : Color.WHITE);
        tabbedPane.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        // Full summary tab
        JPanel fullSummaryPanel = new JPanel(new BorderLayout());
        fullSummaryPanel.setOpaque(false);

        JTextArea summary = new JTextArea(article.getSummary());
        summary.setFont(new Font("Arial", Font.PLAIN, 16));
        summary.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        summary.setOpaque(false);
        summary.setEditable(false);
        summary.setWrapStyleWord(true);
        summary.setLineWrap(true);
        summary.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JScrollPane summaryScroll = new JScrollPane(summary);
        summaryScroll.setOpaque(false);
        summaryScroll.getViewport().setOpaque(false);
        summaryScroll.setBorder(null);
        summaryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        summaryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        summaryScroll.setPreferredSize(new Dimension(0, 200));

        fullSummaryPanel.add(summaryScroll, BorderLayout.CENTER);
        tabbedPane.addTab("📄 Rezumat Complet", fullSummaryPanel);

        // AI Summary tab
        JPanel aiSummaryPanel = createAISummaryPanel(article);
        tabbedPane.addTab("🤖 Rezumat AI", aiSummaryPanel);

        // Key Points tab
        JPanel keyPointsPanel = createKeyPointsPanel(article);
        tabbedPane.addTab("🎯 Puncte Cheie", keyPointsPanel);

        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        JButton linkBtn = UIUtils.createStyledButton("📖 Google Scholar", isDarkMode);
        linkBtn.setPreferredSize(new Dimension(160, 40));
        linkBtn.addActionListener(e -> UIUtils.openWebpage(article.getLink(), this));
        btnPanel.add(linkBtn);

        JButton solutionsBtn = UIUtils.createStyledButton("💡 Vezi Soluții", isDarkMode);
        solutionsBtn.setPreferredSize(new Dimension(140, 40));
        solutionsBtn.addActionListener(e -> showSolutionsDialog(article));
        btnPanel.add(solutionsBtn);

        JButton shareBtn = UIUtils.createStyledButton("📤 Partajează", isDarkMode);
        shareBtn.setPreferredSize(new Dimension(120, 40));
        shareBtn.addActionListener(e -> shareArticle(article));
        btnPanel.add(shareBtn);

        contentPanel.add(btnPanel, BorderLayout.SOUTH);
        card.add(contentPanel, BorderLayout.CENTER);

        // Add hover effects
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    card.getClass().getDeclaredMethod("startHoverAnimation", boolean.class).invoke(card, true);
                } catch (Exception ex) {
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(ThemeConstants.PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(28, 28, 28, 28)
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    card.getClass().getDeclaredMethod("startHoverAnimation", boolean.class).invoke(card, false);
                } catch (Exception ex) {
                    card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
                }
            }
        });

        return card;
    }

    private JPanel createAISummaryPanel(Article article) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Loading/content area
        JTextArea aiSummaryArea = new JTextArea();
        aiSummaryArea.setFont(new Font("Arial", Font.PLAIN, 15));
        aiSummaryArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        aiSummaryArea.setOpaque(false);
        aiSummaryArea.setEditable(false);
        aiSummaryArea.setWrapStyleWord(true);
        aiSummaryArea.setLineWrap(true);
        aiSummaryArea.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        aiSummaryArea.setText("🤖 Apasă butonul pentru a genera rezumatul AI...");

        JScrollPane aiScroll = new JScrollPane(aiSummaryArea);
        aiScroll.setOpaque(false);
        aiScroll.getViewport().setOpaque(false);
        aiScroll.setBorder(null);
        aiScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        aiScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(aiScroll, BorderLayout.CENTER);

        // Generate AI summary button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton generateBtn = UIUtils.createStyledButton("🧠 Generează Rezumat AI", isDarkMode);
        generateBtn.setPreferredSize(new Dimension(200, 35));
        generateBtn.addActionListener(e -> generateAISummary(article, aiSummaryArea, generateBtn));
        buttonPanel.add(generateBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createKeyPointsPanel(Article article) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Extract key points from solutions
        JPanel pointsPanel = new JPanel();
        pointsPanel.setLayout(new BoxLayout(pointsPanel, BoxLayout.Y_AXIS));
        pointsPanel.setOpaque(false);
        pointsPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Add key points with bullet styling
        for (int i = 0; i < Math.min(article.getSolutions().size(), 4); i++) {
            String solution = article.getSolutions().get(i);

            JPanel pointPanel = new JPanel(new BorderLayout());
            pointPanel.setOpaque(false);
            pointPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

            JLabel bulletLabel = new JLabel("🔹");
            bulletLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            bulletLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

            JTextArea pointText = new JTextArea(solution);
            pointText.setFont(new Font("Arial", Font.PLAIN, 14));
            pointText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
            pointText.setOpaque(false);
            pointText.setEditable(false);
            pointText.setWrapStyleWord(true);
            pointText.setLineWrap(true);

            pointPanel.add(bulletLabel, BorderLayout.WEST);
            pointPanel.add(pointText, BorderLayout.CENTER);

            pointsPanel.add(pointPanel);
        }

        JScrollPane pointsScroll = new JScrollPane(pointsPanel);
        pointsScroll.setOpaque(false);
        pointsScroll.getViewport().setOpaque(false);
        pointsScroll.setBorder(null);
        pointsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel.add(pointsScroll, BorderLayout.CENTER);

        return panel;
    }

    private void generateAISummary(Article article, JTextArea aiSummaryArea, JButton generateBtn) {
        generateBtn.setEnabled(false);
        generateBtn.setText("Se generează...");
        aiSummaryArea.setText("🤖 AI-ul analizează articolul și generează rezumatul...\n\nVă rog să așteptați câteva secunde.");

        // Create AI prompt for summarization
        String prompt = "Rezumă acest articol științific despre mediu în română, în maximum 150 cuvinte, " +
                "focalizându-te pe aspectele practice și aplicabile pentru cetățenii români:\n\n" +
                "Titlu: " + article.getTitle() + "\n\n" +
                "Conținut: " + article.getSummary() + "\n\n" +
                "Soluții menționate: " + String.join("; ", article.getSolutions().subList(0, Math.min(3, article.getSolutions().size()))) +
                "\n\nRezumatul să includă: impactul asupra mediului, beneficiile pentru România, și acțiuni concrete.";

        CompletableFuture<String> summaryFuture = aiService.askQuestion(prompt);

        summaryFuture.thenAccept(aiResponse -> {
            SwingUtilities.invokeLater(() -> {
                // Process AI response to extract just the summary
                String cleanSummary = cleanAIResponse(aiResponse);
                aiSummaryArea.setText("🤖 " + cleanSummary);

                generateBtn.setEnabled(true);
                generateBtn.setText("🔄 Regenerează");
            });
        }).exceptionally(throwable -> {
            SwingUtilities.invokeLater(() -> {
                aiSummaryArea.setText("❌ Nu s-a putut genera rezumatul AI.\n\n" +
                        "Rezumat alternativ:\n\n" +
                        generateFallbackSummary(article));

                generateBtn.setEnabled(true);
                generateBtn.setText("🔄 Încearcă din nou");
            });
            return null;
        });
    }

    private String cleanAIResponse(String response) {
        // Remove AI's introductory phrases and keep only the summary
        String cleaned = response.replaceAll("(?i)🤖.*?(?=\\n|$)", "")
                .replaceAll("(?i)sunt specializat.*?(?=\\n\\n|$)", "")
                .replaceAll("(?i)pot răspunde.*?(?=\\n\\n|$)", "")
                .trim();

        // If response is too long, truncate intelligently
        if (cleaned.length() > 800) {
            int lastSentence = cleaned.lastIndexOf('.', 800);
            if (lastSentence > 400) {
                cleaned = cleaned.substring(0, lastSentence + 1);
            } else {
                cleaned = cleaned.substring(0, 800) + "...";
            }
        }

        return cleaned.isEmpty() ? "Rezumatul nu a putut fi generat." : cleaned;
    }

    private String generateFallbackSummary(Article article) {
        // Generate a rule-based summary as fallback
        String title = article.getTitle();
        String summary = article.getSummary();

        StringBuilder fallbackSummary = new StringBuilder();
        fallbackSummary.append("📋 REZUMAT AUTOMAT:\n\n");

        // Extract key sentences from summary (first sentence + sentences with key words)
        String[] sentences = summary.split("\\. ");

        if (sentences.length > 0) {
            fallbackSummary.append(sentences[0]).append(".\n\n");
        }

        // Add key metrics if found
        if (summary.contains("%")) {
            for (String sentence : sentences) {
                if (sentence.contains("%") && sentence.length() < 150) {
                    fallbackSummary.append("📊 ").append(sentence.trim()).append(".\n\n");
                    break;
                }
            }
        }

        // Add practical applications
        if (!article.getSolutions().isEmpty()) {
            fallbackSummary.append("💡 Aplicații practice:\n");
            fallbackSummary.append("• ").append(article.getSolutions().get(0));
        }

        return fallbackSummary.toString();
    }

    private void showSolutionsDialog(Article article) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Soluții Practice", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialogPanel.setBackground(isDarkMode ? new Color(45, 45, 55) : Color.WHITE);

        JLabel titleLabel = new JLabel("💡 Soluții Practice - " + article.getTitle().substring(0, Math.min(50, article.getTitle().length())) + "...");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        dialogPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel solutionsPanel = new JPanel();
        solutionsPanel.setLayout(new BoxLayout(solutionsPanel, BoxLayout.Y_AXIS));
        solutionsPanel.setOpaque(false);

        for (int i = 0; i < article.getSolutions().size(); i++) {
            JPanel solutionPanel = new JPanel(new BorderLayout());
            solutionPanel.setOpaque(false);
            solutionPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

            JLabel numberLabel = new JLabel(String.valueOf(i + 1) + ".");
            numberLabel.setFont(new Font("Arial", Font.BOLD, 14));
            numberLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
            numberLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

            JTextArea solutionText = new JTextArea(article.getSolutions().get(i));
            solutionText.setFont(new Font("Arial", Font.PLAIN, 14));
            solutionText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
            solutionText.setOpaque(false);
            solutionText.setEditable(false);
            solutionText.setWrapStyleWord(true);
            solutionText.setLineWrap(true);

            solutionPanel.add(numberLabel, BorderLayout.WEST);
            solutionPanel.add(solutionText, BorderLayout.CENTER);

            solutionsPanel.add(solutionPanel);
        }

        JScrollPane scrollPane = new JScrollPane(solutionsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        dialogPanel.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = UIUtils.createStyledButton("Închide", isDarkMode);
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    private void shareArticle(Article article) {
        String shareText = "🌍 Articol interesant despre mediu:\n\n" +
                "📰 " + article.getTitle() + "\n\n" +
                "🔗 " + article.getLink() + "\n\n" +
                "Partajat din EcoApp România";

        // Copy to clipboard
        java.awt.datatransfer.StringSelection selection = new java.awt.datatransfer.StringSelection(shareText);
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        JOptionPane.showMessageDialog(this,
                "✅ Textul pentru partajare a fost copiat în clipboard!\n\n" +
                        "Poți să-l lipești acum pe rețelele sociale.",
                "Partajare reușită",
                JOptionPane.INFORMATION_MESSAGE);
    }
}