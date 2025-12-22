package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.UIUtils;
import org.example.combatereapoluariiapp.src.service.AIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CompletableFuture;

public class QAPanel extends JPanel {
    private boolean isDarkMode;
    private AIService aiService;
    private JTextArea chatArea;
    private JTextField questionField;
    private JButton askButton;
    private JScrollPane chatScrollPane;

    public QAPanel(boolean isDarkMode, AIService aiService) {
        this.isDarkMode = isDarkMode;
        this.aiService = aiService;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        initializeComponents();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Întreabă AI-ul despre Poluare și Mediu");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);

        JPanel instructionsPanel = createInstructionsPanel();
        mainContent.add(instructionsPanel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        chatArea.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        chatArea.setBackground(isDarkMode ? new Color(45, 45, 55) : Color.WHITE);
        chatArea.setEditable(false);
        chatArea.setWrapStyleWord(true);
        chatArea.setLineWrap(true);
        chatArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        chatArea.setText("🤖 Bună! Sunt asistentul AI specializat în probleme de mediu și poluare.\n\n" +
                "Poți să mă întrebi despre:\n" +
                "• Metode de reducere a poluării\n" +
                "• Soluții pentru energie verde\n" +
                "• Reciclare și gestionare deșeuri\n" +
                "• Transport sustenabil\n" +
                "• Protecția naturii și biodiversității\n\n" +
                "Scrie întrebarea ta mai jos și apasă Enter sau butonul 'Întreabă'!");

        chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setOpaque(false);
        chatScrollPane.getViewport().setOpaque(false);
        chatScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR, 2),
                        "Conversație",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 14),
                        isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR
                ),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setPreferredSize(new Dimension(0, 400));

        mainContent.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = createInputPanel();
        mainContent.add(inputPanel, BorderLayout.SOUTH);

        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createInstructionsPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(70, 70, 80, 150) : new Color(220, 255, 220, 150);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2d.setColor(isDarkMode ? new Color(100, 100, 110) : new Color(34, 139, 34, 100));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel infoIcon = new JLabel("💡");
        infoIcon.setFont(new Font("Arial", Font.PLAIN, 24));

        JTextArea instructions = new JTextArea(
                "Sfaturi pentru întrebări mai bune:\n" +
                        "• Fii specific: \"Cum pot reduce emisiile CO2 acasă?\" în loc de \"Cum salvez mediul?\"\n" +
                        "• Menționează contextul: \"În apartament\", \"Pentru o companie mică\", etc.\n" +
                        "• Întreabă despre soluții practice și măsurabile\n" +
                        "• AI-ul răspunde doar la întrebări legate de mediu și poluare"
        );
        instructions.setFont(new Font("Arial", Font.PLAIN, 14));
        instructions.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : new Color(60, 60, 60));
        instructions.setOpaque(false);
        instructions.setEditable(false);
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);

        panel.add(infoIcon, BorderLayout.WEST);
        panel.add(instructions, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        questionField = new JTextField();
        questionField.setFont(new Font("Arial", Font.PLAIN, 16));
        questionField.setBackground(isDarkMode ? new Color(50, 50, 60) : Color.WHITE);
        questionField.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        questionField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isDarkMode ? new Color(80, 80, 90) : new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        questionField.setPreferredSize(new Dimension(0, 50));

        questionField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (questionField.getText().equals("Scrie întrebarea ta aici...")) {
                    questionField.setText("");
                    questionField.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (questionField.getText().isEmpty()) {
                    questionField.setText("Scrie întrebarea ta aici...");
                    questionField.setForeground(Color.GRAY);
                }
            }
        });
        questionField.setText("Scrie întrebarea ta aici...");
        questionField.setForeground(Color.GRAY);

        questionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    askQuestion();
                }
            }
        });

        askButton = UIUtils.createStyledButton("🤖 Întreabă", isDarkMode);
        askButton.setPreferredSize(new Dimension(150, 50));
        askButton.addActionListener(e -> askQuestion());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(askButton);

        panel.add(questionField, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void askQuestion() {
        String question = questionField.getText().trim();

        if (question.isEmpty() || question.equals("Scrie întrebarea ta aici...")) {
            JOptionPane.showMessageDialog(this,
                    "Te rog să scrii o întrebare.",
                    "Întrebare lipsă",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        askButton.setEnabled(false);
        questionField.setEnabled(false);
        askButton.setText("Se gândește...");

        appendToChat("👤 Tu: " + question + "\n\n");
        questionField.setText("");

        appendToChat("🤖 AI: Se gândește la răspuns...\n\n");

        CompletableFuture<String> responseFuture = aiService.askQuestion(question);

        responseFuture.thenAccept(response -> {
            SwingUtilities.invokeLater(() -> {
                String currentText = chatArea.getText();
                int lastLoadingIndex = currentText.lastIndexOf("🤖 AI: Se gândește la răspuns...\n\n");
                if (lastLoadingIndex >= 0) {
                    chatArea.setText(currentText.substring(0, lastLoadingIndex));
                }

                appendToChat("🤖 AI: " + response + "\n\n" + "─".repeat(50) + "\n\n");

                askButton.setEnabled(true);
                questionField.setEnabled(true);
                askButton.setText("🤖 Întreabă");
                questionField.requestFocus();
            });
        }).exceptionally(throwable -> {
            SwingUtilities.invokeLater(() -> {
                String currentText = chatArea.getText();
                int lastLoadingIndex = currentText.lastIndexOf("🤖 AI: Se gândește la răspuns...\n\n");
                if (lastLoadingIndex >= 0) {
                    chatArea.setText(currentText.substring(0, lastLoadingIndex));
                }

                appendToChat("🤖 AI: Îmi pare rău, a apărut o eroare. Te rog să încerci din nou.\n\n" + "─".repeat(50) + "\n\n");

                askButton.setEnabled(true);
                questionField.setEnabled(true);
                askButton.setText("🤖 Întreabă");
                questionField.requestFocus();
            });
            return null;
        });
    }

    private void appendToChat(String message) {
        chatArea.append(message);
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        chatScrollPane.getVerticalScrollBar().setValue(chatScrollPane.getVerticalScrollBar().getMaximum());
    }
}