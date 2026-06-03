/**
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
        Quiz quizModule = new QuizModule();
        quizModule.startQuiz();
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
    private JLabel lblQuestionNumber; // Displays the current question number
    private JLabel lblQuestionType;   // Displays the question type
    private JTextArea txtQuestion;    // Displays the question text
    private JTextField txtAnswer;     // Text field for user to enter answer
    private JLabel lblFeedback;       // Displays correct or wrong message
    private JLabel lblScore;          // Displays score in the header
    private JButton btnSubmit;        // Button to submit answer
    private JButton btnNext;          // Button to move to next question
    private JButton btnCancel;        // Button to cancel the quiz

    // Colour theme used for the quiz GUI.
    private final Color COLOR_BG = new Color(30, 45, 47);           // Main background colour
    private final Color COLOR_LIGHT_HEADER = new Color(23, 34, 36); // Header background colour
    private final Color COLOR_SDG_GREEN = new Color(76, 175, 80);   // SDG green colour
    private final Color COLOR_CARD_BG = new Color(38, 54, 56);      // Quiz card background colour
    private final Color COLOR_TEXT_FIELD = new Color(38, 56, 58);   // Answer text field background
    private final Color COLOR_BORDER = new Color(70, 85, 87);       // Card border colour

    // Default constructor.
    // This constructor is used when the quiz is run directly without login data.
    public QuizModule() {
        this("", null);
    }

    // Constructor used when QuizModule is opened from mainAppFrame.
    // It receives username and storageManager so the final score can be saved.
    public QuizModule(String username, Storage storageManager) {
        this.username = username;
        this.storageManager = storageManager;

        // Set the title of the quiz window.
        setTitle("SDG 3: Good Health & Well-being Quiz");

        // Set the size of the quiz window.
        setSize(420, 800);

        // Display the quiz window in the center of the screen.
        setLocationRelativeTo(null);

        // Prevent the user from resizing the window.
        setResizable(false);

        // Close only the quiz window when user exits the quiz.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create an empty ArrayList to store questions.
        questions = new ArrayList<>();

        // Load 20 SDG 3 questions into the quiz.
        loadSDGQuestions();

        // Record the quiz start time.
        startTime = System.currentTimeMillis();

        // Create the GUI design for the quiz.
        createQuizGUI();

        // Display the first question.
        displayQuestion();
    }

    // This method comes from the Quiz interface.
    // It is used to show the quiz window.
    @Override
    public void startQuiz() {
        setVisible(true);
    }

    // This method comes from the Quiz interface.
    // It is used to add a question into the questions list.
    @Override
    public void addQuestion(Question question) {
        questions.add(question);
    }

    // This method is used to check whether the user's answer is correct or wrong.
    @Override
    public boolean evaluateAnswer(Question question, String userAnswer) {

        // Remove extra spaces from the user's answer.
        String answer = userAnswer.trim();

        // Remove extra spaces from the correct answer.
        String correctAnswer = question.getAnswer().trim();

        // TRUE_FALSE question is case-sensitive.
        // User must type exactly "True" or "False".
        if (question.getQuestionType() == QuestionType.TRUE_FALSE) {
            return answer.equals(correctAnswer);
        }

        // MULTIPLE_CHOICE, FILL_IN_THE_BLANK, and SHORT_ANSWER are not case-sensitive.
        // Example: "B" and "b" will both be accepted.
        return answer.equalsIgnoreCase(correctAnswer);
    }

    // This method is used to create the full quiz GUI layout.
    private void createQuizGUI() {

        // Main container panel that holds the header and content area.
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(COLOR_BG);

        // Header panel at the top of the quiz window.
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_LIGHT_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        // Title label in the header.
        JLabel lblTitle = new JLabel("Knowledge Check");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);

        // Score label in the header.
        lblScore = new JLabel(score + " pts");
        lblScore.setFont(new Font("Arial", Font.BOLD, 14));
        lblScore.setForeground(COLOR_SDG_GREEN);

        // Add title and score to the header panel.
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblScore, BorderLayout.EAST);

        // Add header panel to the main container.
        containerPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel stores the quiz card and instruction text.
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        // Section label for the quiz.
        JLabel lblSection = new JLabel("SDG 3 HEALTH QUIZ");
        lblSection.setFont(new Font("Arial", Font.BOLD, 15));
        lblSection.setForeground(new Color(140, 155, 157));
        lblSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(lblSection);
        contentPanel.add(Box.createVerticalStrut(15));

        // Quiz card panel to display the question, answer field, and buttons.
        JPanel quizCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                // Make the card edges smoother.
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the quiz card background with rounded corners.
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Draw the quiz card border.
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
            }
        };

        // Set the quiz card layout and size.
        quizCard.setOpaque(false);
        quizCard.setLayout(new BoxLayout(quizCard, BoxLayout.Y_AXIS));
        quizCard.setBorder(new EmptyBorder(25, 20, 25, 20));
        quizCard.setMaximumSize(new Dimension(370, 560));
        quizCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label to display question number.
        lblQuestionNumber = new JLabel();
        lblQuestionNumber.setFont(new Font("Arial", Font.BOLD, 20));
        lblQuestionNumber.setForeground(Color.WHITE);
        lblQuestionNumber.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label to display question type.
        lblQuestionType = new JLabel();
        lblQuestionType.setFont(new Font("Arial", Font.BOLD, 14));
        lblQuestionType.setForeground(COLOR_SDG_GREEN);
        lblQuestionType.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Text area to display the question.
        txtQuestion = new JTextArea();
        txtQuestion.setEditable(false);
        txtQuestion.setLineWrap(true);
        txtQuestion.setWrapStyleWord(true);
        txtQuestion.setOpaque(false);
        txtQuestion.setForeground(Color.WHITE);
        txtQuestion.setFont(new Font("Arial", Font.BOLD, 16));
        txtQuestion.setBorder(null);
        txtQuestion.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Scroll pane is used in case the question is too long.
        JScrollPane questionScroll = new JScrollPane(txtQuestion);
        questionScroll.setBorder(null);
        questionScroll.setOpaque(false);
        questionScroll.getViewport().setOpaque(false);
        questionScroll.setMaximumSize(new Dimension(330, 230));
        questionScroll.setPreferredSize(new Dimension(330, 230));

        // Label for the answer section.
        JLabel lblAnswer = new JLabel("Your Answer");
        lblAnswer.setFont(new Font("Arial", Font.BOLD, 14));
        lblAnswer.setForeground(new Color(140, 155, 157));
        lblAnswer.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Text field where user types the answer.
        txtAnswer = new JTextField();
        txtAnswer.setMaximumSize(new Dimension(330, 45));
        txtAnswer.setPreferredSize(new Dimension(330, 45));
        txtAnswer.setBackground(COLOR_TEXT_FIELD);
        txtAnswer.setForeground(Color.WHITE);
        txtAnswer.setCaretColor(Color.WHITE);
        txtAnswer.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add light border and padding to the answer text field.
        txtAnswer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        // Make sure the text field background is visible.
        txtAnswer.setOpaque(true);

        // Label to show feedback after user submits answer.
        lblFeedback = new JLabel(" ");
        lblFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        lblFeedback.setForeground(Color.WHITE);
        lblFeedback.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons for submit, next, and cancel.
        btnSubmit = createGreenButton("Submit Answer");
        btnNext = createGreenButton("Next Question");
        btnCancel = createCancelButton("X Cancel Quiz");

        // Disable next button until user submits an answer.
        btnNext.setEnabled(false);

        // When Submit button is clicked, checkAnswer() method will run.
        btnSubmit.addActionListener(e -> checkAnswer());

        // When Next button is clicked, nextQuestion() method will run.
        btnNext.addActionListener(e -> nextQuestion());

        // When Cancel button is clicked, user will be asked to confirm cancellation.
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

        // Add all components into the quiz card.
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
        quizCard.add(lblFeedback);
        quizCard.add(Box.createVerticalStrut(15));
        quizCard.add(btnSubmit);
        quizCard.add(Box.createVerticalStrut(10));
        quizCard.add(btnNext);
        quizCard.add(Box.createVerticalStrut(10));
        quizCard.add(btnCancel);

        // Add quiz card into content panel.
        contentPanel.add(quizCard);
        contentPanel.add(Box.createVerticalStrut(20));

        // Instruction label below the quiz card.
        JLabel lblInstruction = new JLabel(
                "<html><body style='color:#8c9b9d; font-family:Arial; font-size:12px;'>"
                        + "For True or False questions, answer exactly: True or False.<br>"
                        + "Other answers are not case-sensitive."
                        + "</body></html>"
        );

        lblInstruction.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(lblInstruction);

        // Add content panel into the main container.
        containerPanel.add(contentPanel, BorderLayout.CENTER);

        // Add main container into the frame.
        add(containerPanel);
    }

    // This method creates a green rounded button.
    private JButton createGreenButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                // Make rounded button smoother.
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Use green colour if button is enabled, grey if disabled.
                if (isEnabled()) {
                    g2.setColor(COLOR_SDG_GREEN);
                } else {
                    g2.setColor(new Color(80, 95, 96));
                }

                // Draw rounded button background.
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.dispose();

                // Let the button draw its text.
                super.paintComponent(g);
            }
        };

        // Remove default button style to show custom design.
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        // Set button text style.
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

        // Set cancel button colour to red.
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

        // Display question number and total number of questions.
        lblQuestionNumber.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());

        // Display question type.
        lblQuestionType.setText("Question Type: " + currentQuestion.getQuestionType());

        // Display question text.
        txtQuestion.setText(currentQuestion.getQuestionText());

        // Clear the answer field.
        txtAnswer.setText("");

        // Clear previous feedback.
        lblFeedback.setText(" ");

        // Enable submit button and disable next button.
        btnSubmit.setEnabled(true);
        btnNext.setEnabled(false);
    }

    // This method checks the user's answer after clicking Submit.
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Get answer from the text field.
        String userAnswer = txtAnswer.getText().trim();

        // If answer is empty, show warning message.
        if (userAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your answer.");
            return;
        }

        // Check whether the answer is correct or wrong.
        if (evaluateAnswer(currentQuestion, userAnswer)) {
            lblFeedback.setText("Correct!");
            lblFeedback.setForeground(COLOR_SDG_GREEN);
            score++;

            // Update score label in the header.
            lblScore.setText(score + " pts");
        } else {
            lblFeedback.setText("Wrong. Correct answer: " + currentQuestion.getAnswer());
            lblFeedback.setForeground(Color.RED);
        }

        // Disable Submit button so user cannot submit twice.
        btnSubmit.setEnabled(false);

        // Enable Next button after answer is submitted.
        btnNext.setEnabled(true);
    }

    // This method moves to the next question.
    private void nextQuestion() {
        currentQuestionIndex++;

        // If there are still questions, display the next question.
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            // If all questions are finished, show final result.
            showFinalResult();
        }
    }

    // =========================================================================
// [MEMBER 4 : GAMIFICATION RESULT]
// This part links QuizModule with Member 4's GamificationModule.
// QuizModule sends the final score to GamificationModule.
// GamificationModule returns badge, stars, and motivational message.
// =========================================================================
private void showFinalResult() {

    // Calculate quiz duration.
    long endTime = System.currentTimeMillis();
    long durationMillis = endTime - startTime;

    long seconds = durationMillis / 1000;
    long minutes = seconds / 60;
    seconds = seconds % 60;

    String durationText = minutes + " min " + seconds + " sec";

    try {
        // =========================================================================
        // [MEMBER 4 CODE]
        // Create object from Member 4's GamificationModule.
        // This object is used to generate reward based on score.
        // =========================================================================
        GamificationModule gamification = new GamificationModule();

        // Generate reward based on final score.
        // This includes badge, stars, and motivational message.
        Reward reward = gamification.generateReward(score);

        // Get visual star display from Member 4's module.
    
        String starDisplay = gamification.getStarDisplay(score);

        // =========================================================================
        // [MEMBER 4 DISPLAY RESULT]
        // Display badge, stars, and motivational message returned by Member 4.
        // =========================================================================
        JOptionPane.showMessageDialog(
                this,
                "Quiz Completed!\n"
                        + "Your final score is: " + score + " / " + questions.size()
                        + "\nTime taken: " + durationText
                        + "\n\nReward Result"
                        + "\nBadge: " + reward.getBadge()
                        + "\nStars: " + starDisplay
                        + "\nMessage: " + reward.getMotivationalMessage(),
                "Quiz Result",
                JOptionPane.INFORMATION_MESSAGE
        );

    } catch (GamificationException e) {

        // This part runs if there is an error in GamificationModule.
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

    // Save final score into storage.
    saveFinalScore();

    // Close quiz window.
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
