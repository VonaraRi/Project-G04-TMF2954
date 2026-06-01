//creator:Rosfanida 106171

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// QuizModule is a GUI class for the quiz
// It extends JFrame because this quiz will open as a GUI window.
// It implements Quiz interface, so it must provide the methods from Quiz.
public class QuizModule extends JFrame implements Quiz {

        // Main method to run the quiz program
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

    // GUI components used in the quiz window.
    private JLabel lblQuestionNumber; // Displays the question number
    private JLabel lblQuestionType;   // Displays the type of question
    private JTextArea txtQuestion;    // Displays the question text
    private JTextField txtAnswer;     // Allows the user to type an answer
    private JLabel lblFeedback;       // Displays Correct or Wrong message
    private JButton btnSubmit;        // Button to submit answer
    private JButton btnNext;          // Button to move to the next question
    private JButton btnCancel;        // Button to cancel the quiz

    // Colour theme used for the quiz GUI.
    private final Color COLOR_BG = new Color(30, 45, 47);            // Main background colour
    private final Color COLOR_LIGHT_HEADER = new Color(23, 34, 36);  // Header background colour
    private final Color COLOR_SDG_GREEN = new Color(76, 175, 80);    // Green SDG colour
    private final Color COLOR_CARD_BG = new Color(38, 54, 56);       // Quiz card background
    private final Color COLOR_TEXT_FIELD = new Color(38, 56, 58);    // Answer text field background
    private final Color COLOR_BORDER = new Color(70, 85, 87);        // Card border colour

    // Constructor for QuizModule.
    // This runs when the QuizModule object is created.
    public QuizModule() {
        setTitle("SDG 3: Good Health & Well-being Quiz"); // Set window title
        setSize(420, 800); // Set window size to look like smartphone layout
        setLocationRelativeTo(null); // Display the window at the center of the screen
        setResizable(false); // Prevent user from resizing the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this quiz window

        questions = new ArrayList<>(); // Create an empty list for questions
        loadSDGQuestions(); // Load all SDG 3 questions into the list

        createQuizGUI(); // Create the quiz GUI layout
        displayQuestion(); // Display the first question
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

    // This method checks whether the user's answer is correct or wrong.
    @Override
    public boolean evaluateAnswer(Question question, String userAnswer) {

        // Remove extra spaces before and after the user's answer.
        String answer = userAnswer.trim();

        // Remove extra spaces before and after the correct answer.
        String correctAnswer = question.getAnswer().trim();

        // TRUE_FALSE is case-sensitive.
        // Example: If the correct answer is "True", user must type exactly "True".
        // "true" or "TRUE" will be counted as wrong.
        if (question.getQuestionType() == QuestionType.TRUE_FALSE) {
            return answer.equals(correctAnswer);
        }

        // MULTIPLE_CHOICE, FILL_IN_THE_BLANK, and SHORT_ANSWER are not case-sensitive.
        // Example: "B" and "b" will both be accepted.
        return answer.equalsIgnoreCase(correctAnswer);
    }

    // This method creates the full GUI layout for the quiz window.
    private void createQuizGUI() {

        // Main container panel using BorderLayout.
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(COLOR_BG);

        // Header panel at the top of the window.
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_LIGHT_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        // Title label shown in the header.
        JLabel lblTitle = new JLabel("Knowledge Check");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);

        // Score label shown on the right side of the header.
        JLabel lblScore = new JLabel(score + " pts");
        lblScore.setFont(new Font("Arial", Font.BOLD, 14));
        lblScore.setForeground(COLOR_SDG_GREEN);

        // Add title and score into the header.
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblScore, BorderLayout.EAST);

        // Add header to the top part of the main container.
        containerPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel stores the quiz card and instructions.
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

        // quizCard is the main card that contains the question and answer area.
        // It uses custom painting to create rounded corners.
        JPanel quizCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                // Make the card smoother.
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fill the rounded rectangle with card background colour.
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Draw the border of the card.
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
            }
        };

        // Set quiz card properties.
        quizCard.setOpaque(false);
        quizCard.setLayout(new BoxLayout(quizCard, BoxLayout.Y_AXIS));
        quizCard.setBorder(new EmptyBorder(25, 20, 25, 20));
        quizCard.setMaximumSize(new Dimension(370, 560));
        quizCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label to display current question number.
        lblQuestionNumber = new JLabel();
        lblQuestionNumber.setFont(new Font("Arial", Font.BOLD, 20));
        lblQuestionNumber.setForeground(Color.WHITE);
        lblQuestionNumber.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label to display question type.
        lblQuestionType = new JLabel();
        lblQuestionType.setFont(new Font("Arial", Font.BOLD, 14));
        lblQuestionType.setForeground(COLOR_SDG_GREEN);
        lblQuestionType.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Text area to display question text.
        txtQuestion = new JTextArea();
        txtQuestion.setEditable(false); // User cannot edit the question text
        txtQuestion.setLineWrap(true); // Wrap long lines
        txtQuestion.setWrapStyleWord(true); // Wrap by word, not letter
        txtQuestion.setOpaque(false); // Transparent background
        txtQuestion.setForeground(Color.WHITE);
        txtQuestion.setFont(new Font("Arial", Font.BOLD, 16));
        txtQuestion.setBorder(null);
        txtQuestion.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Scroll pane is used in case the question text is long.
        JScrollPane questionScroll = new JScrollPane(txtQuestion);
        questionScroll.setBorder(null);
        questionScroll.setOpaque(false);
        questionScroll.getViewport().setOpaque(false);
        questionScroll.setMaximumSize(new Dimension(330, 230));
        questionScroll.setPreferredSize(new Dimension(330, 230));

        // Label for answer input field.
        JLabel lblAnswer = new JLabel("Your Answer");
        lblAnswer.setFont(new Font("Arial", Font.BOLD, 14));
        lblAnswer.setForeground(new Color(140, 155, 157));
        lblAnswer.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Text field where the user types the answer.
        txtAnswer = new JTextField();
        txtAnswer.setMaximumSize(new Dimension(330, 45));
        txtAnswer.setPreferredSize(new Dimension(330, 45));

        // Set answer text box background colour.
        txtAnswer.setBackground(COLOR_TEXT_FIELD);

        // Set the font colour inside the text box to white.
        txtAnswer.setForeground(Color.WHITE);

        // Set cursor colour to white.
        txtAnswer.setCaretColor(Color.WHITE);

        // Set font style and size for typed answer.
        txtAnswer.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add a light border and inner padding to the answer text box.
        // The line border creates the visible light outline.
        // The empty border creates spacing inside the text box.
        txtAnswer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 2),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        // Make sure the background colour of the text box is visible.
        txtAnswer.setOpaque(true);

        // Feedback label to show whether the answer is correct or wrong.
        lblFeedback = new JLabel(" ");
        lblFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        lblFeedback.setForeground(Color.WHITE);
        lblFeedback.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create Submit and Next buttons.
        btnSubmit = createGreenButton("Submit Answer");
        btnNext = createGreenButton("Next Question");
        btnCancel = createCancelButton("X Cancel Quiz");

        // Next button is disabled at first.
        // It will be enabled after the user submits an answer.
        btnNext.setEnabled(false);

        // When Submit button is clicked, checkAnswer() will run.
        btnSubmit.addActionListener(e -> checkAnswer());

        // When Next button is clicked, nextQuestion() will run.
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

        // Add all components into quiz card.
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

        // Add content panel to the center of the main container.
        containerPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the main container into the JFrame.
        add(containerPanel);
    }

    // This method creates a green button with the same design style.
    private JButton createGreenButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // If the button is enabled, use green colour.
                // If disabled, use grey colour.
                if (isEnabled()) {
                    g2.setColor(COLOR_SDG_GREEN);
                } else {
                    g2.setColor(new Color(80, 95, 96));
                }

                // Draw rounded button background.
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                g2.dispose();
                super.paintComponent(g);
            }
        };



        // Remove default button design so custom painting is visible.
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        // Set button text design.
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        // Set button size.
        button.setMaximumSize(new Dimension(330, 45));
        button.setPreferredSize(new Dimension(330, 45));

        // Change cursor to hand when hovering over the button.
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    // This method creates a cancel button to exit the quiz.
    private JButton createCancelButton(String text) {
        JButton button = new JButton(text);

        // Set button background colour to red.
        button.setBackground(new Color(180, 50, 50));

        // Set button text colour to white.
        button.setForeground(Color.WHITE);

        // Set button font.
        button.setFont(new Font("Arial", Font.BOLD, 16));

        // Remove focus border.
        button.setFocusPainted(false);

        // Set button size.
        button.setMaximumSize(new Dimension(330, 45));
        button.setPreferredSize(new Dimension(330, 45));

        // Change cursor to hand when hovering over the button.
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    // This method displays the current question on the GUI.
    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Display question number and total questions.
        lblQuestionNumber.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());

        // Display the question type.
        lblQuestionType.setText("Question Type: " + currentQuestion.getQuestionType());

        // Display the question text.
        txtQuestion.setText(currentQuestion.getQuestionText());

        // Clear previous answer.
        txtAnswer.setText("");

        // Clear previous feedback message.
        lblFeedback.setText(" ");

        // Enable submit button and disable next button.
        btnSubmit.setEnabled(true);
        btnNext.setEnabled(false);
    }

    // This method checks the user's answer after clicking Submit.
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Get the user's answer from the text field.
        String userAnswer = txtAnswer.getText().trim();

        // If the answer field is empty, ask the user to enter an answer.
        if (userAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your answer.");
            return;
        }

        // Check if the answer is correct.
        if (evaluateAnswer(currentQuestion, userAnswer)) {
            lblFeedback.setText("Correct!");
            lblFeedback.setForeground(COLOR_SDG_GREEN);
            score++;
        } else {
            lblFeedback.setText("Wrong. Correct answer: " + currentQuestion.getAnswer());
            lblFeedback.setForeground(Color.RED);
        }

        // After submitting, prevent user from submitting again.
        btnSubmit.setEnabled(false);

        // Enable next button to move to the next question.
        btnNext.setEnabled(true);
    }

    // This method moves to the next question.
    private void nextQuestion() {
        currentQuestionIndex++;

        // If there are still questions left, display the next question.
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            // If all questions are completed, show final result.
            showFinalResult();
        }
    }

    // This method shows the final quiz result and motivation message.
    private void showFinalResult() {
        String message;

        // Motivation message based on user's score.
        if (score >= 80) {
            message = "Outstanding!";
        } else if (score >= 60) {
            message = "That's good!";
        } else if (score >= 40) {
            message = "Good try!";
        } else if (score >= 20) {
            message = "You can do better!";
        } else {
            message = "Don't give up!";
        }

        // Display final score and motivation message.
        JOptionPane.showMessageDialog(
                this,
                "Quiz Completed!\n"
                        + "Your final score is: " + score + " / " + questions.size()
                        + "\n" + message,
                "Quiz Result",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Close the quiz window after completion.
        dispose();
    }

    // This method loads 20 SDG 3 questions into the quiz.
    private void loadSDGQuestions() {

        addQuestion(new Question(
                "Which topic focuses on protecting mothers, babies, and young children before, during, and after birth?\n"
                        + "A. Mental Health\n"
                        + "B. Maternal and Child Health\n"
                        + "C. Injury Prevention\n"
                        + "D. Universal Health Care",
                "B",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Which action is important for maternal and child health?\n"
                        + "A. Skipping antenatal check-ups\n"
                        + "B. Avoiding immunisation\n"
                        + "C. Attending antenatal check-ups\n"
                        + "D. Ignoring danger signs",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Which of the following is an example of a communicable disease?\n"
                        + "A. Diabetes\n"
                        + "B. Heart disease\n"
                        + "C. Dengue\n"
                        + "D. Cancer",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "How can we reduce mosquito breeding?\n"
                        + "A. Remove stagnant water\n"
                        + "B. Share personal items\n"
                        + "C. Avoid vaccination\n"
                        + "D. Eat more sugar",
                "A",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Which disease is a non-communicable disease?\n"
                        + "A. COVID-19\n"
                        + "B. Measles\n"
                        + "C. Tuberculosis\n"
                        + "D. Diabetes",
                "D",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Which habit helps reduce the risk of non-communicable diseases?\n"
                        + "A. Smoking\n"
                        + "B. Exercising regularly\n"
                        + "C. Sleeping very late\n"
                        + "D. Avoiding health screening",
                "B",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Which safety habit can help prevent injury?\n"
                        + "A. Not wearing a helmet\n"
                        + "B. Driving when tired\n"
                        + "C. Using seatbelts\n"
                        + "D. Keeping medicine near children",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Universal Health Care means people should receive needed health services without:\n"
                        + "A. Learning about health\n"
                        + "B. Financial hardship\n"
                        + "C. Drinking water\n"
                        + "D. Exercising daily",
                "B",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "Which daily healthy lifestyle habit is recommended?\n"
                        + "A. Limit sugary drinks\n"
                        + "B. Skip sleep\n"
                        + "C. Avoid vegetables\n"
                        + "D. Never take screen breaks",
                "A",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "What should people do during a health emergency?\n"
                        + "A. Spread rumours\n"
                        + "B. Ignore announcements\n"
                        + "C. Follow verified health announcements\n"
                        + "D. Hide information",
                "C",
                QuestionType.MULTIPLE_CHOICE
        ));

        addQuestion(new Question(
                "True or False: Maternal and child health includes safe delivery with trained health workers.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        addQuestion(new Question(
                "True or False: Communicable diseases can spread through air, water, insects, blood, or direct contact.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        addQuestion(new Question(
                "True or False: Non-communicable diseases spread directly from one person to another.",
                "False",
                QuestionType.TRUE_FALSE
        ));

        addQuestion(new Question(
                "True or False: Substance abuse can harm physical health, mental health, relationships, and community safety.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        addQuestion(new Question(
                "True or False: Mental health affects how people think, feel, study, work, and connect with others.",
                "True",
                QuestionType.TRUE_FALSE
        ));

        addQuestion(new Question(
                "Fill in the blank: Reproductive health includes puberty education, menstrual health, family planning information, and respect for consent, privacy, and personal __________.",
                "safety",
                QuestionType.FILL_IN_THE_BLANK
        ));

        addQuestion(new Question(
                "Fill in the blank: Universal Health Care includes health promotion, prevention, treatment, rehabilitation, and access to essential __________.",
                "medicines",
                QuestionType.FILL_IN_THE_BLANK
        ));

        addQuestion(new Question(
                "Fill in the blank: During a health emergency, people should not spread __________.",
                "rumours",
                QuestionType.FILL_IN_THE_BLANK
        ));

        addQuestion(new Question(
                "Give one supportive habit for maintaining mental health and well-being.",
                "talk to trusted people",
                QuestionType.SHORT_ANSWER
        ));

        addQuestion(new Question(
                "State one way students can support SDG 3 in daily life.",
                "share accurate health information",
                QuestionType.SHORT_ANSWER
        ));
    }
}
