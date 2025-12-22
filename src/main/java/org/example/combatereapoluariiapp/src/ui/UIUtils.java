package org.example.combatereapoluariiapp.src.ui;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.model.Article;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class UIUtils {

    public static JButton createStyledButton(String text, boolean isDarkMode) {
        JButton button = new JButton(text) {
            private float fadeAnimation = 0.0f;
            private Timer animationTimer;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color baseColor = ThemeConstants.PRIMARY_COLOR;
                Color fadeColor = ThemeConstants.BUTTON_HOVER_COLOR;

                if (getModel().isPressed()) {
                    baseColor = ThemeConstants.BUTTON_PRESSED_COLOR;
                    fadeColor = ThemeConstants.PRIMARY_COLOR;
                } else if (getModel().isRollover()) {
                    int red = (int) (baseColor.getRed() + (fadeColor.getRed() - baseColor.getRed()) * fadeAnimation);
                    int green = (int) (baseColor.getGreen() + (fadeColor.getGreen() - baseColor.getGreen()) * fadeAnimation);
                    int blue = (int) (baseColor.getBlue() + (fadeColor.getBlue() - baseColor.getBlue()) * fadeAnimation);
                    baseColor = new Color(red, green, blue);
                }

                GradientPaint gradient = new GradientPaint(
                        0, 0, baseColor,
                        getWidth(), getHeight(), baseColor.brighter()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                if (getModel().isRollover() && fadeAnimation > 0.3f) {
                    Color glowColor = new Color(129, 199, 132, (int) (50 * fadeAnimation));
                    g2d.setColor(glowColor);
                    g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
                }

                g2d.setColor(new Color(0, 0, 0, 30));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 1;
                g2d.drawString(getText(), x + 1, y + 1);

                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);
            }

            public void startAnimation() {
                if (animationTimer != null) animationTimer.stop();

                animationTimer = new Timer(20, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fadeAnimation = Math.min(1.0f, fadeAnimation + 0.08f);
                        repaint();

                        if (fadeAnimation >= 1.0f) {
                            animationTimer.stop();
                        }
                    }
                });
                animationTimer.start();
            }

            public void stopAnimation() {
                if (animationTimer != null) animationTimer.stop();

                animationTimer = new Timer(20, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fadeAnimation = Math.max(0.0f, fadeAnimation - 0.08f);
                        repaint();

                        if (fadeAnimation <= 0.0f) {
                            animationTimer.stop();
                        }
                    }
                });
                animationTimer.start();
            }
        };

        button.setPreferredSize(new Dimension(180, 50));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    button.getClass().getDeclaredMethod("startAnimation").invoke(button);
                } catch (Exception ex) {
                    button.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    button.getClass().getDeclaredMethod("stopAnimation").invoke(button);
                } catch (Exception ex) {
                    button.repaint();
                }
            }
        });

        return button;
    }

    public static JPanel createStatCard(String icon, String value, String label, String desc, boolean isDarkMode) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 20, 20);

                g2d.setColor(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 20, 20);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(280, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setVerticalAlignment(JLabel.CENTER);
        iconLabel.setPreferredSize(new Dimension(60, 60));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(iconLabel, gbc);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(valueLabel, gbc);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Arial", Font.BOLD, 18));
        labelText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        labelText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(labelText, gbc);

        JLabel descText = new JLabel("<html><center>" + desc + "</center></html>");
        descText.setFont(new Font("Arial", Font.PLAIN, 14));
        descText.setForeground(isDarkMode ? new Color(180, 180, 180) : new Color(100, 100, 100));
        descText.setPreferredSize(new Dimension(250, 100));
        descText.setVerticalAlignment(JLabel.TOP);
        descText.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(descText, gbc);

        return card;
    }

    public static void openWebpage(String url, Component parent) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Nu se poate deschide link-ul: " + url + "\n\nVă rugăm să copiați link-ul în browser.",
                    "Informație",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}