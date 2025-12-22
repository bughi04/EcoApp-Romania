package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.data.DataRepository;

import javax.swing.*;
import java.awt.*;

public class SolutionsPanel extends JPanel {
    private boolean isDarkMode;

    public SolutionsPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Soluții Practice pentru Combaterea Poluării");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel categoriesPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        categoriesPanel.setOpaque(false);

        String[][] categories = DataRepository.getSolutionCategories();

        for (String[] category : categories) {
            categoriesPanel.add(createSolutionCategory(category));
        }

        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainContent.add(scrollPane, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSolutionCategory(String[] data) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 15));
                g2d.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 15, 15);

                GradientPaint gradient = new GradientPaint(0, 0,
                        isDarkMode ? new Color(70, 70, 80) : new Color(250, 250, 250),
                        getWidth(), getHeight(),
                        isDarkMode ? new Color(50, 50, 60) : new Color(240, 240, 240));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(1200, 400));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        header.setOpaque(false);

        JLabel icon = new JLabel(data[0]);
        icon.setFont(new Font("Arial", Font.PLAIN, 36));

        JLabel title = new JLabel(data[1]);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(ThemeConstants.PRIMARY_COLOR);

        header.add(icon);
        header.add(title);
        card.add(header, BorderLayout.NORTH);

        JPanel solutionsPanel = new JPanel();
        solutionsPanel.setLayout(new BoxLayout(solutionsPanel, BoxLayout.Y_AXIS));
        solutionsPanel.setOpaque(false);
        solutionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        for (int i = 2; i < data.length; i++) {
            JTextArea solution = new JTextArea("• " + data[i]);
            solution.setFont(new Font("Arial", Font.PLAIN, 14));
            solution.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
            solution.setOpaque(false);
            solution.setEditable(false);
            solution.setWrapStyleWord(true);
            solution.setLineWrap(true);
            solution.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            solution.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel solutionWrapper = new JPanel(new BorderLayout());
            solutionWrapper.setOpaque(false);
            solutionWrapper.add(solution, BorderLayout.CENTER);
            solutionWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, solution.getPreferredSize().height + 20));

            solutionsPanel.add(solutionWrapper);
        }

        JScrollPane scrollPane = new JScrollPane(solutionsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }
}