/**
 * Class Name: QuizModule
 * Description: This class manages the quiz GUI, question display, answer checking,
 * score calculation, final result display, reward generation, and score saving
 * for the SDG 3 Quiz Application.
 * Creator: Rosfanida ak Benjamin 106171
 * Tester:
 */

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// QuizModule is a GUI class for the quiz.
// It extends JFrame because this quiz will open as a GUI window.
// It implements Quiz interface, so it must provide the methods from Quiz.
public class QuizModule extends JFrame implements Quiz {

    // Main method to run the quiz directly for testing.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QuizModule quizModule = new QuizModule();
                quizModule.startQuiz();
            }
        });
    }

    // ArrayList is used to store all quiz questions.
    private ArrayList<Question> questions;

    // currentQuestionIndex keeps track of the current question number.
    private int currentQuestionIndex = 0;

    // score stores the user's total correct answers.
    private int score = 0;

    // This variable stores the time when the quiz starts.
    private long startTime;

    // These variables are used to save the user's score into the storage system.
    private String username;
    private Storage storageManager;

    // GUI components used in the quiz window.
    private JLabel lblQuestionNumber;
    private JLabel lblQuestionType;
    private JTextArea txtQuestion;
    private JTextField txtAnswer;
    private JTextArea txtFeedback;
    private JLabel lblScore;
    private JButton btnSubmit;
    private JButton btnNext;
    private JButton btnCancel;

    // Colour theme used for the quiz GUI.
    private final Color COLOR_BG = new Color(30, 45, 47);
    private final Color COLOR_LIGHT_HEADER = new Color(23, 34, 36);
    private final Color COLOR_SDG_GREEN = new Color(76, 175, 80);
    private final Color COLOR_CARD_BG = new Color(38, 54, 56);
    private final Color COLOR_TEXT_FIELD = new Color(38, 56, 58);
    private final Color COLOR_BORDER = new Color(70, 85, 87);

    // Default constructor.
    public QuizModule() {
        this("", null);
    }

    // Constructor used when QuizModule is opened from mainAppFrame.
    public QuizModule(String username, Storage storageManager) {
        this.username = username;
        this.storageManager = storageManager;

        setTitle("SDG 3: Good Health & Well-being Quiz");
        setSize(420, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        questions = new ArrayList<>();

        loadSDGQuestions();

        startTime = System.currentTimeMillis();

        createQuizGUI();

        displayQuestion();
    }

    @Override
    public void startQuiz() {
        setVisible(true);
    }

    @Override
    public void addQuestion(Question question) {
        questions.add(question);
    }

    @Override
    public boolean evaluateAnswer(Question question, String userAnswer) {
        String answer = userAnswer.trim();
        String correctAnswer = question.getAnswer().trim();

        // Make all answers not case-sensitive.
        return answer.equalsIgnoreCase(correctAnswer);
    }

    // This method is used to create the full quiz GUI layout.
    private void createQuizGUI() {

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(COLOR_BG);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_LIGHT_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel lblTitle = new JLabel("Knowledge Check");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);

        lblScore = new JLabel(score + " pts");
        lblScore.setFont(new Font("Arial", Font.BOLD, 14));
        lblScore.setForeground(COLOR_SDG_GREEN);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblScore, BorderLayout.EAST);

        containerPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblSection = new JLabel("SDG 3 HEALTH QUIZ");
        lblSection.setFont(new Font("Arial", Font.BOLD, 15));
        lblSection.setForeground(new Color(140, 155, 157));
        lblSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(lblSection);
        contentPanel.add(Box.createVerticalStrut(15));

        JPanel quizCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
            }
        };

        quizCard.setOpaque(false);
        quizCard.setLayout(new BoxLayout(quizCard, BoxLayout.Y_AXIS));
        quizCard.setBorder(new EmptyBorder(25, 20, 25, 20));
        quizCard.setMaximumSize(new Dimension(370, 620));
        quizCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Question number - centered
        lblQuestionNumber = new JLabel("", SwingConstants.CENTER);
        lblQuestionNumber.setFont(new Font("Arial", Font.BOLD, 20));
        lblQuestionNumber.setForeground(Color.WHITE);
        lblQuestionNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblQuestionNumber.setMaximumSize(new Dimension(330, 30));
        lblQuestionNumber.setPreferredSize(new Dimension(330, 30));

        // Question type - centered
        lblQuestionType = new JLabel("", SwingConstants.CENTER);
        lblQuestionType.setFont(new Font("Arial", Font.BOLD, 11));
        lblQuestionType.setForeground(COLOR_SDG_GREEN);
        lblQuestionType.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblQuestionType.setMaximumSize(new Dimension(330, 25));
        lblQuestionType.setPreferredSize(new Dimension(330, 25));

        // Question text area
        txtQuestion = new JTextArea();
        txtQuestion.setEditable(false);
        txtQuestion.setLineWrap(true);
        txtQuestion.setWrapStyleWord(true);
        txtQuestion.setOpaque(false);
        txtQuestion.setForeground(Color.WHITE);
        txtQuestion.setFont(new Font("Arial", Font.BOLD, 16));
        txtQuestion.setBorder(null);
        txtQuestion.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane questionScroll = new JScrollPane(txtQuestion);
        questionScroll.setBorder(null);
        questionScroll.setOpaque(false);
        questionScroll.getViewport().setOpaque(false);
        questionScroll.setMaximumSize(new Dimension(330, 200));
        questionScroll.setPreferredSize(new Dimension(330, 200));
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Make question scrollbar smaller only
        questionScroll.getVerticalScrollBar().setUnitIncrement(16);
        questionScroll.getVerticalScrollBar().setPreferredSize(new Dimension(6, 0));

        // Your Answer label - centered
        JLabel lblAnswer = new JLabel("Your Answer", SwingConstants.CENTER);
        lblAnswer.setFont(new Font("Arial", Font.BOLD, 14));
        lblAnswer.setForeground(new Color(140, 155, 157));
        lblAnswer.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAnswer.setMaximumSize(new Dimension(330, 25));
        lblAnswer.setPreferredSize(new Dimension(330, 25));

        txtAnswer = new JTextField();
        txtAnswer.setMaximumSize(new Dimension(330, 45));
        txtAnswer.setPreferredSize(new Dimension(330, 45));
        txtAnswer.setBackground(COLOR_TEXT_FIELD);
        txtAnswer.setForeground(Color.WHITE);
        txtAnswer.setCaretColor(Color.WHITE);
        txtAnswer.setFont(new Font("Arial", Font.PLAIN, 16));

        txtAnswer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        txtAnswer.setOpaque(true);

        // Feedback text area - can show full feedback
        txtFeedback = new JTextArea(" ");
        txtFeedback.setEditable(false);
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        txtFeedback.setOpaque(false);
        txtFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        txtFeedback.setForeground(Color.WHITE);
        txtFeedback.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtFeedback.setMaximumSize(new Dimension(330, 80));
        txtFeedback.setPreferredSize(new Dimension(330, 80));
        txtFeedback.setBorder(null);

        btnSubmit = createGreenButton("Submit Answer");
        btnNext = createGreenButton("Next Question");
        btnCancel = createCancelButton("X Cancel Quiz");

        btnNext.setEnabled(false);

        btnSubmit.addActionListener(e -> checkAnswer());
        btnNext.addActionListener(e -> nextQuestion());

        btnCancel.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel the quiz?",
                    "Cancel Quiz",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        quizCard.add(lblQuestionNumber);
        quizCard.add(Box.createVerticalStrut(8));
        quizCard.add(lblQuestionType);
        quizCard.add(Box.createVerticalStrut(20));
        quizCard.add(questionScroll);
        quizCard.add(Box.createVerticalStrut(20));
        quizCard.add(lblAnswer);
        quizCard.add(Box.createVerticalStrut(8));
        quizCard.add(txtAnswer);
        quizCard.add(Box.createVerticalStrut(15));
        quizCard.add(txtFeedback);
        quizCard.add(Box.createVerticalStrut(15));
        quizCard.add(btnSubmit);
        quizCard.add(Box.createVerticalStrut(10));
        quizCard.add(btnNext);
        quizCard.add(Box.createVerticalStrut(10));
        quizCard.add(btnCancel);

        contentPanel.add(quizCard);
        contentPanel.add(Box.createVerticalStrut(20));

        JLabel lblInstruction = new JLabel(
                "<html><body style='color:#8c9b9d; font-family:Arial; font-size:12px;'>"
                        + "For True or False questions, answer exactly: True or False.<br>"
                        + "Other answers are not case-sensitive."
                        + "</body></html>"
        );

        lblInstruction.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblInstruction);

        containerPanel.add(contentPanel, BorderLayout.CENTER);

        add(containerPanel);
    }

    // This method creates a green rounded button.
    private JButton createGreenButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isEnabled()) {
                    g2.setColor(COLOR_SDG_GREEN);
                } else {
                    g2.setColor(new Color(80, 95, 96));
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.dispose();

                super.paintComponent(g);
            }
        };

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setMaximumSize(new Dimension(330, 45));
        button.setPreferredSize(new Dimension(330, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    // This method creates the cancel button.
    private JButton createCancelButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(new Color(180, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(330, 45));
        button.setPreferredSize(new Dimension(330, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    // This method displays the current question on the GUI.
    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        lblQuestionNumber.setText(
                "Question " + (currentQuestionIndex + 1) + " of " + questions.size()
        );

        String typeText = "";

        switch (currentQuestion.getQuestionType()) {
            case MULTIPLE_CHOICE:
                typeText = "Multiple Choice";
                break;

            case TRUE_FALSE:
                typeText = "True or False";
                break;

            case FILL_IN_THE_BLANK:
                typeText = "Fill in the Blank";
                break;

            case SHORT_ANSWER:
                typeText = "Short Answer";
                break;
        }

        lblQuestionType.setText("Question Type: " + typeText);

        txtQuestion.setText(currentQuestion.getQuestionText());
        txtQuestion.setCaretPosition(0);

        txtAnswer.setText("");

        txtFeedback.setText(" ");

        btnSubmit.setEnabled(true);
        btnNext.setEnabled(false);
    }

    // This method checks the user's answer after clicking Submit.
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        String userAnswer = txtAnswer.getText().trim();

        if (userAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an answer.",
                    "No Answer",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (evaluateAnswer(currentQuestion, userAnswer)) {
            txtFeedback.setText("Correct!");
            txtFeedback.setForeground(COLOR_SDG_GREEN);
            score++;

            lblScore.setText(score + " pts");
        } else {
            txtFeedback.setText(
                    "Wrong.\nCorrect answer: " + currentQuestion.getAnswer()
            );
            txtFeedback.setForeground(Color.RED);
        }

        btnSubmit.setEnabled(false);
        btnNext.setEnabled(true);
    }

    // This method moves to the next question.
    private void nextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            showFinalResult();
        }
    }

    // This part links QuizModule with Member 4's GamificationModule.
    private void showFinalResult() {

        long endTime = System.currentTimeMillis();
        long durationMillis = endTime - startTime;

        long seconds = durationMillis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        String durationText = minutes + " min " + seconds + " sec";

        try {
            GamificationModule gamification = new GamificationModule();
            Reward reward = gamification.generateReward(score);

            JOptionPane.showMessageDialog(
                    this,
                    "Quiz Completed!\n"
                            + "Your final score is: " + score + " / " + questions.size()
                            + "\nTime taken: " + durationText
                            + "\n\nReward Result"
                            + "\nBadge: " + reward.getBadge()
                            + "\nStars: " + reward.getStars()
                            + "\nMessage: " + reward.getMotivationalMessage(),
                    "Quiz Result",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (GamificationException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Quiz Completed!\n"
                            + "Your final score is: " + score + " / " + questions.size()
                            + "\nTime taken: " + durationText
                            + "\nGamification error: " + e.getMessage(),
                    "Quiz Result",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        saveFinalScore();

        dispose();
    }

    // This method saves the final quiz score into the storage system.
    private void saveFinalScore() {
        if (storageManager != null && username != null && !username.trim().isEmpty()) {
            try {
                storageManager.saveScore(username, score);

                JOptionPane.showMessageDialog(
                        this,
                        "Your score has been saved successfully.",
                        "Score Saved",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (CustomStorageException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Score could not be saved: " + e.getMessage(),
                        "Storage Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // This method loads 20 SDG 3 questions into the quiz.
    private void loadSDGQuestions() {
        // Question 1: Multiple choice
        addQuestion(new Question(
                "Which topic focuses on protecting mothers, babies, and young children before, during, and after birth?\n"
                        + "A. Mental Health\n"
                        + "B. Maternal and Child Health\n"
                        + "C. Injury Prevention\n"
                        + "D. Universal Health Care",
                "B",
                QuestionType.MULTIPLE_CHOICE
        ));
         // Question 2: Multiple choice
        addQuestion(new Question(
                "Which action is important for maternal and child health?\n"
                        + "A. Skipping antenatal check-ups\n"
                        + "B. Avoiding immunisation\n"
                        + "C. Attending antenatal check-ups\n"
                        + "D. Ignoring danger signs",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));
        
         // Question 3: Multiple choice
        addQuestion(new Question(
                "Which of the following is an example of a communicable disease?\n"
                        + "A. Diabetes\n"
                        + "B. Heart disease\n"
                        + "C. Dengue\n"
                        + "D. Cancer",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));
        
         // Question 4: Multiple choice
        addQuestion(new Question(
                "How can we reduce mosquito breeding?\n"
                        + "A. Remove stagnant water\n"
                        + "B. Share personal items\n"
                        + "C. Avoid vaccination\n"
                        + "D. Eat more sugar",
                "A",
                QuestionType.MULTIPLE_CHOICE
        ));

         // Question 5: Multiple choice
        addQuestion(new Question(
                "Which disease is a non-communicable disease?\n"
                        + "A. COVID-19\n"
                        + "B. Measles\n"
                        + "C. Tuberculosis\n"
                        + "D. Diabetes",
                "D",
                QuestionType.MULTIPLE_CHOICE
        ));
        
         // Question 6: Multiple choice
        addQuestion(new Question(
                "Which habit helps reduce the risk of non-communicable diseases?\n"
                        + "A. Smoking\n"
                        + "B. Exercising regularly\n"
                        + "C. Sleeping very late\n"
                        + "D. Avoiding health screening",
                "B",
                QuestionType.MULTIPLE_CHOICE
        ));

        // Question 7: Multiple choice
        addQuestion(new Question(
                "Which safety habit can help prevent injury?\n"
                        + "A. Not wearing a helmet\n"
                        + "B. Driving when tired\n"
                        + "C. Using seatbelts\n"
                        + "D. Keeping medicine near children",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));

         // Question 8: Multiple choice
        addQuestion(new Question(
                "Universal Health Care means people should receive needed health services without:\n"
                        + "A. Learning about health\n"
                        + "B. Financial hardship\n"
                        + "C. Drinking water\n"
                        + "D. Exercising daily",
                "B",
                QuestionType.MULTIPLE_CHOICE
        ));

         // Question 9: Multiple choice
        addQuestion(new Question(
                "Which daily healthy lifestyle habit is recommended?\n"
                        + "A. Limit sugary drinks\n"
                        + "B. Skip sleep\n"
                        + "C. Avoid vegetables\n"
                        + "D. Never take screen breaks",
                "A",
                QuestionType.MULTIPLE_CHOICE
        ));

        // Question 10: Multiple choice
        addQuestion(new Question(
                "What should people do during a health emergency?\n"
                        + "A. Spread rumours\n"
                        + "B. Ignore announcements\n"
                        + "C. Follow verified health announcements\n"
                        + "D. Hide information",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));

        // Question 11: True or False
        addQuestion(new Question(
                "True or False: Maternal and child health includes safe delivery with trained health workers.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        // Question 12: True or False
        addQuestion(new Question(
                "True or False: Communicable diseases can spread through air, water, insects, blood, or direct contact.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        // Question 13: True or False
        addQuestion(new Question(
                "True or False: Non-communicable diseases spread directly from one person to another.",
                "False",
                QuestionType.TRUE_FALSE
        ));

        // Question 14: True or False
        addQuestion(new Question(
                "True or False: Substance abuse can harm physical health, mental health, relationships, and community safety.",
                "True",
                QuestionType.TRUE_FALSE
        ));

           // Question 15: True or False
        addQuestion(new Question(
                "True or False: Mental health affects how people think, feel, study, work, and connect with others.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        // Question 16: Fill in the blank
        addQuestion(new Question(
                "Fill in the blank: Reproductive health includes puberty education, menstrual health, family planning information, and respect for consent, privacy, and personal __________.",
                "safety",
                QuestionType.FILL_IN_THE_BLANK
        ));

          // Question 17: Fill in the blank
        addQuestion(new Question(
                "Fill in the blank: Universal Health Care includes health promotion, prevention, treatment, rehabilitation, and access to essential __________.",
                "medicines",
                QuestionType.FILL_IN_THE_BLANK
        ));

         // Question 18: Fill in the blank
        addQuestion(new Question(
                "Fill in the blank: During a health emergency, people should not spread __________.",
                "rumours",
                QuestionType.FILL_IN_THE_BLANK
        ));

        // Question 19: Short answer
        addQuestion(new Question(
                "Give one supportive habit for maintaining mental health and well-being.",
                "talk to trusted people",
                QuestionType.SHORT_ANSWER
        ));

          // Question 20: Short answer
        addQuestion(new Question(
                "State one way students can support SDG 3 in daily life.",
                "share accurate health information",
                QuestionType.SHORT_ANSWER
        ));
    }
}
