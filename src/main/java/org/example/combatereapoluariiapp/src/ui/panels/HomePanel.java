package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private boolean isDarkMode;
    private ActionListener solutionsNavigation;

    public HomePanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new GridBagLayout());
        initializeComponents();
    }

    public HomePanel(boolean isDarkMode, ActionListener solutionsNavigation) {
        this.isDarkMode = isDarkMode;
        this.solutionsNavigation = solutionsNavigation;
        setOpaque(false);
        setLayout(new GridBagLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel heroTitle = new JLabel("Împreună pentru un Viitor Mai Curat");
        heroTitle.setFont(new Font("Arial", Font.BOLD, 48));
        heroTitle.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        heroTitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(heroTitle, gbc);

        JTextArea heroDesc = new JTextArea("Descoperă soluții bazate pe cercetări științifice pentru a combate poluarea și a proteja mediul înconjurător. Aplicația oferă informații detaliate despre metode practice și eficiente de reducere a impactului asupra mediului.");
        heroDesc.setFont(new Font("Arial", Font.PLAIN, 20));
        heroDesc.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        heroDesc.setOpaque(false);
        heroDesc.setEditable(false);
        heroDesc.setWrapStyleWord(true);
        heroDesc.setLineWrap(true);
        heroDesc.setPreferredSize(new Dimension(800, 100));
        gbc.gridy = 1;
        add(heroDesc, gbc);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        statsPanel.setOpaque(false);
        statsPanel.setPreferredSize(new Dimension(900, 260));

        for (String[] stat : ThemeConstants.STATS_DATA) {
            statsPanel.add(UIUtils.createStatCard(stat[0], stat[1], stat[2], stat[3], isDarkMode));
        }

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(statsPanel, gbc);

        JButton ctaBtn = UIUtils.createStyledButton("Explorează Soluțiile", isDarkMode);
        ctaBtn.setFont(new Font("Arial", Font.BOLD, 24));
        ctaBtn.setPreferredSize(new Dimension(350, 60));

        if (solutionsNavigation != null) {
            ctaBtn.addActionListener(solutionsNavigation);
        }

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(ctaBtn, gbc);
    }
}