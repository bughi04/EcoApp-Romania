package org.example.combatereapoluariiapp.src.ui.panels;

import org.example.combatereapoluariiapp.src.constants.ThemeConstants;
import org.example.combatereapoluariiapp.src.ui.UIUtils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class EnvironmentalQuizPanel extends JPanel {
    private boolean isDarkMode;
    private java.util.List<QuizQuestion> allQuestions;
    private java.util.List<QuizQuestion> currentQuiz;
    private int currentQuestionIndex;
    private int correctAnswers;

    // UI Components
    private JLabel questionNumberLabel;
    private JLabel questionTextLabel;
    private ButtonGroup answerGroup;
    private JRadioButton[] answerButtons;
    private JButton nextButton;
    private JButton startQuizButton;
    private JProgressBar progressBar;
    private JLabel scoreLabel;
    private JComboBox<String> difficultyComboBox;

    // CardLayout components
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel resultsPanel; // Keep reference to results panel

    public EnvironmentalQuizPanel(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
        this.allQuestions = new ArrayList<>();
        this.currentQuiz = new ArrayList<>();
        this.answerButtons = new JRadioButton[4];

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        initializeQuestions();
        initializeComponents();
    }

    private void initializeQuestions() {
        // ========== UȘOR (BEGINNER) QUESTIONS ==========
        allQuestions.add(new QuizQuestion(
                "Care este principala cauză a încălzirii globale?",
                new String[]{"Defrișările", "Emisiile de gaze cu efect de seră", "Poluarea apei", "Zgomotul urban"},
                1, "UȘOR",
                "Emisiile de CO2 și alte gaze cu efect de seră din activitățile umane sunt principala cauză a schimbărilor climatice."
        ));

        allQuestions.add(new QuizQuestion(
                "Te procent din suprafața României este acoperită de păduri?",
                new String[]{"15%", "28%", "35%", "42%"},
                1, "UȘOR",
                "România are aproximativ 28% din suprafață acoperită de păduri, sub media UE de 38%."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este simbolul de reciclare pentru plastic?",
                new String[]{"Un triunghi cu numărul 1-7", "O săgeată circulară", "O frunză verde", "Un copac"},
                0, "UȘOR",
                "Simbolul de reciclare pentru plastic este un triunghi format din săgeți cu un număr în interior (1-7)."
        ));

        allQuestions.add(new QuizQuestion(
                "Când se sărbătorește Ziua Pământului?",
                new String[]{"22 aprilie", "5 iunie", "15 martie", "1 octombrie"},
                0, "UȘOR",
                "Ziua Pământului se sărbătorește pe 22 aprilie în întreaga lume pentru conștientizarea protecției mediului."
        ));

        allQuestions.add(new QuizQuestion(
                "Care culoare de coș de gunoi este pentru plastic în România?",
                new String[]{"Galben", "Albastru", "Verde", "Maro"},
                0, "UȘOR",
                "Coșul galben este destinat plasticului și metalului în sistemul de colectare selectivă din România."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce înseamnă LED?",
                new String[]{"Light Emitting Diode", "Low Energy Device", "Light Energy Detector", "Long Efficient Duration"},
                0, "UȘOR",
                "LED înseamnă Light Emitting Diode și consumă cu 80% mai puțină energie decât becurile clasice."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este cea mai mare sursă de oxigen pe Pământ?",
                new String[]{"Pădurile", "Oceanele", "Iarba", "Plantele de apartament"},
                1, "UȘOR",
                "Oceanele produc peste 70% din oxigenul planetei prin fitoplancton, nu pădurile cum se crede des."
        ));

        allQuestions.add(new QuizQuestion(
                "Când se sărbătorește Ziua Pădurii în România?",
                new String[]{"22 aprilie", "15 martie", "5 iunie", "1 octombrie"},
                1, "UȘOR",
                "Ziua Pădurii se sărbătorește pe 15 martie în România, cu acțiuni de plantare în toată țara."
        ));

        allQuestions.add(new QuizQuestion(
                "Care din acestea este o energie regenerabilă?",
                new String[]{"Cărbunele", "Energia solară", "Gazul natural", "Petrolul"},
                1, "UȘOR",
                "Energia solară este regenerabilă pentru că se reînnoiește continuu, spre deosebire de combustibilii fosili."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce se întâmplă cu deșeurile biodegradabile?",
                new String[]{"Persistă veacuri întregi", "Se descompun natural", "Devin toxice", "Se transformă în plastic"},
                1, "UȘOR",
                "Deșeurile biodegradabile se descompun natural prin acțiunea bacteriilor și a naturii în câteva luni sau ani."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este prima regulă a celor 3R pentru mediu?",
                new String[]{"Reciclează", "Reduce", "Reutilizează", "Repară"},
                1, "UȘOR",
                "Prima regulă este 'Reduce' - să consumăm mai puțin, apoi să reutilizăm și în final să reciclăm."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce gaz respiră plantele pentru fotosinteză?",
                new String[]{"Oxigen", "Dioxid de carbon", "Azot", "Hidrogen"},
                1, "UȘOR",
                "Plantele absorb CO2 (dioxid de carbon) din aer pentru fotosinteză și eliberează oxigen."
        ));

        // ========== MEDIU (INTERMEDIATE) QUESTIONS ==========
        allQuestions.add(new QuizQuestion(
                "Care este obiectivul României pentru energia regenerabilă până în 2030?",
                new String[]{"20%", "27%", "32%", "40%"},
                2, "MEDIU",
                "România trebuie să atingă 32% energie din surse regenerabile până în 2030, conform directivelor UE."
        ));

        allQuestions.add(new QuizQuestion(
                "Câte kilograme de CO2 absoarbe un copac matur pe an?",
                new String[]{"10 kg", "22 kg", "50 kg", "100 kg"},
                1, "MEDIU",
                "Un copac matur absoarbe în medie 22 kg de CO2 pe an și produce oxigen pentru 2 persoane."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este programul românesc pentru panouri solare?",
                new String[]{"Casa Verde Plus", "Casa Verde Fotovoltaice", "Solar România", "Energie Verde"},
                1, "MEDIU",
                "Casa Verde Fotovoltaice oferă până la 20.000 lei pentru instalarea panourilor solare la casele particulare."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce înseamnă acronimul PM2.5 în contextul poluării aerului?",
                new String[]{"Particule de 2.5 metri", "Particule sub 2.5 micrometri", "Poluare maximă 2.5%", "Presiune atmosferică 2.5"},
                1, "MEDIU",
                "PM2.5 sunt particulele fine cu diametrul sub 2.5 micrometri, foarte periculoase pentru sănătate."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este rata de reciclare a României comparativ cu media UE?",
                new String[]{"România: 13%, UE: 48%", "România: 25%, UE: 35%", "România: 40%, UE: 50%", "România: 50%, UE: 60%"},
                0, "MEDIU",
                "România reciclează doar 13% din deșeuri, mult sub media UE de 48%. Obiectivul pentru 2025 este 55%."
        ));

        allQuestions.add(new QuizQuestion(
                "Câte grade Celsius s-a încălzit planeta în ultimii 100 de ani?",
                new String[]{"0.5°C", "1.1°C", "2.0°C", "3.5°C"},
                1, "MEDIU",
                "Temperatura globală medie a crescut cu aproximativ 1.1°C față de perioada preindustrială."
        ));

        allQuestions.add(new QuizQuestion(
                "Care sunt Carpații în contextul biodiversității europene?",
                new String[]{"Cel mai mare ecosistem forestier intact", "Cea mai mare rezervă de apă", "Cel mai înalt lanț muntos", "Cea mai mare rezervă de cărbune"},
                0, "MEDIU",
                "Carpații reprezintă cel mai mare ecosistem forestier intact din Europa, cu o biodiversitate excepțională."
        ));

        allQuestions.add(new QuizQuestion(
                "Câte zile însorite pe an are România în medie?",
                new String[]{"150 zile", "180 zile", "210 zile", "250 zile"},
                2, "MEDIU",
                "România are în medie 210 zile însorite pe an, oferind un potențial excelent pentru energia solară."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este durata de viață a unui bec LED față de unul incandescent?",
                new String[]{"De 5 ori mai mult", "De 10 ori mai mult", "De 25 de ori mai mult", "De 50 de ori mai mult"},
                2, "MEDIU",
                "LED-urile durează de 25 de ori mai mult decât becurile incandescente și consumă cu 80% mai puțină energie."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce procent din emisiile globale de CO2 provine din transporturi?",
                new String[]{"10%", "14%", "24%", "35%"},
                2, "MEDIU",
                "Transporturile contribuie cu aproximativ 24% din emisiile globale de CO2, fiind a doua sursă după energia."
        ));

        allQuestions.add(new QuizQuestion(
                "Câți litri de apă sunt necesari pentru producerea unui kg de carne de vită?",
                new String[]{"500 litri", "1.500 litri", "15.000 litri", "50.000 litri"},
                2, "MEDIU",
                "Producerea unui kg de carne de vită necesită aproximativ 15.000 litri de apă pentru furaje și procesare."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este poziția României în UE la capacitatea eoliană instalată?",
                new String[]{"Locul 5", "Locul 8", "Locul 12", "Locul 15"},
                2, "MEDIEU",
                "România se află pe locul 12 în UE la capacitatea eoliană, cu cel mai mare potențial în Dobrogea."
        ));

        // ========== AVANSAT (ADVANCED) QUESTIONS ==========
        allQuestions.add(new QuizQuestion(
                "Care este diferența dintre economia liniară și economia circulară?",
                new String[]{"Liniară: produce-folosește-aruncă, Circulară: reduce-reutilizează-reciclează",
                        "Liniară: rapid, Circulară: lent",
                        "Liniară: pentru companii, Circulară: pentru case",
                        "Nu există diferență"},
                0, "AVANSAT",
                "Economia circulară urmărește să elimine deșeurile prin reutilizarea continuă a resurselor, spre deosebire de modelul liniar tradițional."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este impactul carbon al unui email cu atașament de 1MB?",
                new String[]{"1g CO2", "4g CO2", "19g CO2", "50g CO2"},
                2, "AVANSAT",
                "Un email cu atașament de 1MB generează aproximativ 19g CO2 din cauza energiei necesare pentru stocare și transmitere."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce procent din emisiile globale de CO2 provin din transportul maritim?",
                new String[]{"1%", "3%", "7%", "15%"},
                1, "AVANSAT",
                "Transportul maritim contribuie cu aproximativ 3% din emisiile globale de CO2, dar această cifră crește rapid."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este diferența de eficiență energetică între LED-uri și becurile incandescente?",
                new String[]{"LED-urile consumă cu 20% mai puțin", "LED-urile consumă cu 50% mai puțin",
                        "LED-urile consumă cu 80% mai puțin", "LED-urile consumă cu 90% mai puțin"},
                2, "AVANSAT",
                "LED-urile consumă cu 80% mai puțină energie decât becurile incandescente și durează de 25 de ori mai mult."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este concentrația actuală de CO2 în atmosferă?",
                new String[]{"350 ppm", "410 ppm", "450 ppm", "500 ppm"},
                1, "AVANSAT",
                "Concentrația de CO2 a depășit 410 ppm în 2023, cel mai înalt nivel din ultimii 3 milioane de ani."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce procent din energia electrică a României provine din surse regenerabile?",
                new String[]{"18%", "24%", "31%", "42%"},
                1, "AVANSAT",
                "În 2023, aproximativ 24% din energia electrică a României provenea din surse regenerabile (hidro, eolian, solar)."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este principalul gaz cu efect de seră după CO2?",
                new String[]{"Metan (CH4)", "Protoxid de azot (N2O)", "Ozon (O3)", "Vapor de apă (H2O)"},
                0, "AVANSAT",
                "Metanul (CH4) este al doilea cel mai important gaz cu efect de seră, cu un potențial de încălzire de 25 de ori mai mare decât CO2."
        ));

        allQuestions.add(new QuizQuestion(
                "Câte grade Celsius reprezintă ținta Acordului de la Paris pentru limitarea încălzirii globale?",
                new String[]{"1.5°C", "2.0°C", "2.5°C", "3.0°C"},
                0, "AVANSAT",
                "Acordul de la Paris urmărește limitarea încălzirii globale la 1.5°C față de perioada preindustrială pentru a evita efectele catastrofale."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce procent din defrișarea Amazonului este cauzată de creșterea animalelor?",
                new String[]{"40%", "60%", "80%", "95%"},
                2, "AVANSAT",
                "Aproximativ 80% din defrișarea Amazonului este cauzată de industria creșterii animalelor pentru pășuni și furaje."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este durata de descompunere a unei sticle de plastic în natură?",
                new String[]{"50 de ani", "100 de ani", "450 de ani", "1000 de ani"},
                2, "AVANSAT",
                "O sticlă de plastic se descompune în aproximativ 450 de ani, de aceea reciclarea și reducerea consumului sunt esențiale."
        ));

        allQuestions.add(new QuizQuestion(
                "Care este principala cauză a acidificării oceanelor?",
                new String[]{"Poluarea cu plastic", "Absorbția CO2 din atmosferă", "Deversarea de petrol", "Fertilizatorii agricoli"},
                1, "AVANSAT",
                "Oceanele absorb aproximativ 30% din CO2-ul atmosferic, formând acid carbonic și reducând pH-ul apei marine."
        ));

        allQuestions.add(new QuizQuestion(
                "Ce procent din suprafața Pământului trebuie protejată pentru a preveni colapsul biodiversității?",
                new String[]{"15%", "20%", "30%", "50%"},
                2, "AVANSAT",
                "Experții recomandă protejarea a 30% din suprafața Pământului până în 2030 pentru a preveni colapsul biodiversității."
        ));
    }

    private void initializeComponents() {
        // Title
        JLabel title = new JLabel("Quiz Ecologic Interactiv");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.PRIMARY_COLOR);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Create CardLayout container
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        // Add panels to CardLayout
        cardPanel.add(createWelcomePanel(), "WELCOME");
        cardPanel.add(createQuestionPanel(), "QUESTION");
        resultsPanel = createResultsPanel(); // Store reference
        cardPanel.add(resultsPanel, "RESULTS");

        add(cardPanel, BorderLayout.CENTER);

        // Show welcome screen initially
        cardLayout.show(cardPanel, "WELCOME");
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Welcome icon
        JLabel welcomeIcon = new JLabel("🧠");
        welcomeIcon.setFont(new Font("Arial", Font.PLAIN, 80));
        welcomeIcon.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(welcomeIcon, gbc);

        // Welcome text
        JTextArea welcomeText = new JTextArea(
                "Bun venit la Quiz-ul Ecologic!\n\n" +
                        "Testează-ți cunoștințele despre:\n" +
                        "🌍 Schimbări climatice\n" +
                        "♻️ Reciclare și sustenabilitate\n" +
                        "🌱 Biodiversitate și conservare\n" +
                        "🇷🇴 Politici de mediu din România\n\n" +
                        "Alege nivelul de dificultate și începe!"
        );
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeText.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        welcomeText.setOpaque(false);
        welcomeText.setEditable(false);
        welcomeText.setFocusable(false);
        welcomeText.setWrapStyleWord(true);
        welcomeText.setLineWrap(true);
        welcomeText.setPreferredSize(new Dimension(500, 250));
        gbc.gridy = 1;
        panel.add(welcomeText, gbc);

        // Difficulty selection
        JPanel difficultyPanel = new JPanel(new FlowLayout());
        difficultyPanel.setOpaque(false);

        JLabel difficultyLabel = new JLabel("Nivel dificultate:");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        difficultyLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        difficultyComboBox = new JComboBox<>(new String[]{"TOATE", "UȘOR", "MEDIU", "AVANSAT"});
        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        difficultyComboBox.setBackground(isDarkMode ? new Color(60, 60, 70) : Color.WHITE);
        difficultyComboBox.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);

        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyComboBox);

        gbc.gridy = 2;
        panel.add(difficultyPanel, gbc);

        // Start button
        startQuizButton = UIUtils.createStyledButton("🚀 Începe Quiz-ul", isDarkMode);
        startQuizButton.setPreferredSize(new Dimension(200, 50));
        startQuizButton.addActionListener(e -> startQuiz());
        gbc.gridy = 3;
        panel.add(startQuizButton, gbc);

        return panel;
    }

    private JPanel createQuestionPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = isDarkMode ? new Color(50, 50, 60, 120) : new Color(250, 250, 250, 180);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header with progress
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        questionNumberLabel = new JLabel("Întrebarea 1 din 8");
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionNumberLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        headerPanel.add(questionNumberLabel, BorderLayout.WEST);

        scoreLabel = new JLabel("Scor: 0/0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
        headerPanel.add(scoreLabel, BorderLayout.EAST);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("0%");
        progressBar.setForeground(ThemeConstants.PRIMARY_COLOR);
        progressBar.setBackground(isDarkMode ? new Color(60, 60, 70) : new Color(230, 230, 230));
        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        headerPanel.add(progressBar, BorderLayout.SOUTH);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Question content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        questionTextLabel = new JLabel();
        questionTextLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionTextLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        questionTextLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        contentPanel.add(questionTextLabel, BorderLayout.NORTH);

        // Answer options
        JPanel answersPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        answersPanel.setOpaque(false);

        answerGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JRadioButton();
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            answerButtons[i].setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
            answerButtons[i].setOpaque(false);
            answerButtons[i].setFocusPainted(false);
            answerButtons[i].setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            answerGroup.add(answerButtons[i]);
            answersPanel.add(answerButtons[i]);
        }

        contentPanel.add(answersPanel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        // Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navPanel.setOpaque(false);

        nextButton = UIUtils.createStyledButton("Următoarea ➡️", isDarkMode);
        nextButton.setPreferredSize(new Dimension(150, 40));
        nextButton.addActionListener(e -> nextQuestion());
        navPanel.add(nextButton);

        panel.add(navPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setOpaque(false);

        return panel;
    }

    private void startQuiz() {
        String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
        prepareQuiz(selectedDifficulty);

        currentQuestionIndex = 0;
        correctAnswers = 0;

        showQuestion();
        cardLayout.show(cardPanel, "QUESTION");
    }

    private void prepareQuiz(String difficulty) {
        currentQuiz.clear();

        if ("TOATE".equals(difficulty)) {
            Collections.shuffle(allQuestions);
            currentQuiz.addAll(allQuestions);
        } else {
            List<QuizQuestion> filteredQuestions = new ArrayList<>();
            for (QuizQuestion q : allQuestions) {
                if (q.difficulty.equals(difficulty)) {
                    filteredQuestions.add(q);
                }
            }
            Collections.shuffle(filteredQuestions);
            currentQuiz.addAll(filteredQuestions);
        }
    }

    private void showQuestion() {
        if (currentQuestionIndex >= currentQuiz.size()) {
            showResults();
            return;
        }

        QuizQuestion question = currentQuiz.get(currentQuestionIndex);

        questionNumberLabel.setText(String.format("Întrebarea %d din %d",
                currentQuestionIndex + 1, currentQuiz.size()));
        questionTextLabel.setText("<html>" + question.questionText + "</html>");

        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(question.answers[i]);
            answerButtons[i].setSelected(false);
        }

        scoreLabel.setText(String.format("Scor: %d/%d", correctAnswers, currentQuestionIndex));

        int progress = (int) ((double) currentQuestionIndex / currentQuiz.size() * 100);
        progressBar.setValue(progress);
        progressBar.setString(progress + "%");

        answerGroup.clearSelection();
        nextButton.setText(currentQuestionIndex == currentQuiz.size() - 1 ? "Finalizează" : "Următoarea");
    }

    private void nextQuestion() {
        // Check answer
        int selectedAnswer = -1;
        for (int i = 0; i < 4; i++) {
            if (answerButtons[i].isSelected()) {
                selectedAnswer = i;
                break;
            }
        }

        if (selectedAnswer == -1) {
            JOptionPane.showMessageDialog(this, "Te rog să selectezi un răspuns!",
                    "Răspuns lipsă", JOptionPane.WARNING_MESSAGE);
            return;
        }

        QuizQuestion currentQuestion = currentQuiz.get(currentQuestionIndex);
        if (selectedAnswer == currentQuestion.correctAnswerIndex) {
            correctAnswers++;
        }

        // Show explanation
        showExplanation(currentQuestion, selectedAnswer == currentQuestion.correctAnswerIndex);

        currentQuestionIndex++;

        // Delay before next question
        Timer timer = new Timer(1500, e -> {
            showQuestion();
            ((Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showExplanation(QuizQuestion question, boolean correct) {
        String message = correct ? "✅ Corect!" : "❌ Răspuns greșit";
        message += "\n\n💡 " + question.explanation;

        JOptionPane.showMessageDialog(this, message, "Explicație", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showResults() {
        // FIXED: Clear and rebuild results panel properly
        resultsPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Results icon
        String resultIcon = getResultIcon();
        JLabel iconLabel = new JLabel(resultIcon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        resultsPanel.add(iconLabel, gbc);

        // Score
        JLabel finalScoreLabel = new JLabel(String.format("Scor Final: %d/%d", correctAnswers, currentQuiz.size()));
        finalScoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        finalScoreLabel.setForeground(ThemeConstants.PRIMARY_COLOR);
        finalScoreLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        resultsPanel.add(finalScoreLabel, gbc);

        // Performance message
        JLabel performanceLabel = new JLabel(getPerformanceMessage());
        performanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        performanceLabel.setForeground(isDarkMode ? ThemeConstants.DARK_TEXT_COLOR : ThemeConstants.TEXT_COLOR);
        performanceLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        resultsPanel.add(performanceLabel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JButton restartButton = UIUtils.createStyledButton("🔄 Încearcă din nou", isDarkMode);
        restartButton.setPreferredSize(new Dimension(180, 45));
        restartButton.addActionListener(e -> cardLayout.show(cardPanel, "WELCOME"));
        buttonPanel.add(restartButton);

        gbc.gridy = 3;
        resultsPanel.add(buttonPanel, gbc);

        resultsPanel.revalidate();
        resultsPanel.repaint();

        cardLayout.show(cardPanel, "RESULTS");
    }

    private String getResultIcon() {
        double percentage = (double) correctAnswers / currentQuiz.size() * 100;
        if (percentage >= 90) return "🏆";
        else if (percentage >= 70) return "🥇";
        else if (percentage >= 50) return "👍";
        else return "📚";
    }

    private String getPerformanceMessage() {
        double percentage = (double) correctAnswers / currentQuiz.size() * 100;
        if (percentage >= 90) return "<html><center>Excelent! Ești un adevărat expert în mediu!</center></html>";
        else if (percentage >= 70) return "<html><center>Foarte bine! Ai cunoștințe solide despre mediu.</center></html>";
        else if (percentage >= 50) return "<html><center>Bun început! Mai ai de învățat despre mediu.</center></html>";
        else return "<html><center>Ai nevoie de mai multă practică.</center></html>";
    }

    // Quiz Question class
    private static class QuizQuestion {
        String questionText;
        String[] answers;
        int correctAnswerIndex;
        String difficulty;
        String explanation;

        QuizQuestion(String questionText, String[] answers, int correctAnswerIndex, String difficulty, String explanation) {
            this.questionText = questionText;
            this.answers = answers;
            this.correctAnswerIndex = correctAnswerIndex;
            this.difficulty = difficulty;
            this.explanation = explanation;
        }
    }
}