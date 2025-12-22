package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {
    private boolean isDarkMode;

    public AboutPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new GridBagLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel title = new JLabel("Despre Proiect");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        JTextArea aboutText = new JTextArea();
        aboutText.setText("Această aplicație a fost creată pentru a oferi soluții practice și bazate pe cercetări științifice " +
                "pentru combaterea poluării în România.\n\n" +
                "Toate articolele prezentate sunt bazate pe studii publicate în Google Scholar și oferă " +
                "metode validate științific pentru reducerea impactului asupra mediului. Aplicația include " +
                "peste 50 de soluții concrete organizate pe categorii pentru implementare ușoară.\n\n" +
                "Funcții principale:\n" +
                "• Articole științifice verificate despre poluare și soluții\n" +
                "• Soluții practice organizate pe categorii (acasă, transport, birou, etc.)\n" +
                "• Asistent AI specializat pentru întrebări despre mediu și poluare\n" +
                "• Interfață modernă cu tema întunecată/luminoasă\n\n" +
                "Obiectivele aplicației:\n" +
                "• Educarea publicului despre metodele de combatere a poluării\n" +
                "• Oferirea de soluții practice și accesibile\n" +
                "• Promovarea cercetării științifice românești în domeniul mediului\n" +
                "• Încurajarea acțiunilor individuale și comunitare\n" +
                "• Asistență inteligentă prin AI pentru răspunsuri personalizate\n\n" +
                "Despre AI-ul integrat:\n");

        aboutText.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        aboutText.setOpaque(false);
        aboutText.setEditable(false);
        aboutText.setWrapStyleWord(true);
        aboutText.setLineWrap(true);
        aboutText.setPreferredSize(new Dimension(800, 400));
        gbc.gridy = 1;
        add(aboutText, gbc);
    }
}