package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.data.DataRepository;
import org.example.combatereapoluariiapp.src.model.Article;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ArticlesPanel extends JPanel {
    private boolean isDarkMode;

    public ArticlesPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Articole Științifice Google Scholar");
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
            gridPanel.add(createArticleCard(article));
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

    private JPanel createArticleCard(Article article) {
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
        card.setPreferredSize(new Dimension(650, 550));

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'><b>" + article.getTitle() + "</b></div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        card.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        JTextArea citation = new JTextArea(article.getCitation());
        citation.setFont(new Font("Arial", Font.ITALIC, 16));
        citation.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(80, 80, 80));
        citation.setOpaque(false);
        citation.setEditable(false);
        citation.setWrapStyleWord(true);
        citation.setLineWrap(true);
        citation.setBorder(BorderFactory.createEmptyBorder(0, 10, 25, 10));

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
        citationPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        citationPanel.add(citation, BorderLayout.CENTER);
        contentPanel.add(citationPanel, BorderLayout.NORTH);

        JTextArea summary = new JTextArea(article.getSummary());
        summary.setFont(new Font("Arial", Font.PLAIN, 22));
        summary.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        summary.setOpaque(false);
        summary.setEditable(false);
        summary.setWrapStyleWord(true);
        summary.setLineWrap(true);
        summary.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        JScrollPane summaryScroll = new JScrollPane(summary);
        summaryScroll.setOpaque(false);
        summaryScroll.getViewport().setOpaque(false);
        summaryScroll.setBorder(null);
        summaryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        summaryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(summaryScroll, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton linkBtn = UIUtils.createStyledButton("📖 Citește pe Google Scholar", isDarkMode);
        linkBtn.setPreferredSize(new Dimension(280, 45));
        linkBtn.addActionListener(e -> UIUtils.openWebpage(article.getLink(), this));
        btnPanel.add(linkBtn);

        contentPanel.add(btnPanel, BorderLayout.SOUTH);
        card.add(contentPanel, BorderLayout.CENTER);

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
}