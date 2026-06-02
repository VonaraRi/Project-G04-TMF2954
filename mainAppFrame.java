import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * Desktop GUI matching smartphone form factor and UI aesthetics for SDG 3.
 * Fulfills Member 1 responsibilities regarding UI layout management and app entry point.
 * * Creator: Rionnalyn (GUI + Data Storage + Integration)
 * Tester: 
 */
public class mainAppFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private Storage storageManager;
    
    private String currentUser = "";
    private int currentUserScore = 0;
    
    // UI References for Dynamic Screen Updates
    private JLabel lblUserProfileChip;
    private JPanel leaderboardCardContainer;

    // Design Color Palettes
    private final Color COLOR_BG = new Color(30, 45, 47);          
    private final Color COLOR_DARK_HEADER = new Color(23, 34, 36);  
    private final Color COLOR_SDG_GREEN = new Color(76, 175, 80);  
    private final Color COLOR_CARD_BG = new Color(38, 54, 56);     
    private final Color COLOR_TEXT_FIELD = new Color(38, 56, 58); 
    private final Color COLOR_BORDER = new Color(70, 85, 87);      

    public mainAppFrame() {
        // Enforce smartphone layout constraints run inside desktop frames
        setTitle("SDG 3: Good Health & Well-being");
        setSize(420, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Instantiate Member 1 Data Engine (Choose true for Java DB, false for CSV)
        this.storageManager = new DataManager(false);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        mainContainer.add(createLoginPanel(), "LoginScreen");
        mainContainer.add(createDashboardPanel(), "DashboardScreen");

        add(mainContainer);
        setVisible(true);
    }

    /**
     * Login screen rendering with user verification pipelines.
     */
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(80, 35, 60, 35));

        // Circular badge icon
        JPanel badgePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_SDG_GREEN);
                g2.fillOval(0, 0, 100, 100);
                g2.dispose();
            }
        };
        badgePanel.setOpaque(false);
        badgePanel.setPreferredSize(new Dimension(100, 100));
        badgePanel.setMaximumSize(new Dimension(100, 100));
        badgePanel.setLayout(new GridBagLayout());
        
        JLabel numberLabel = new JLabel("3");
        numberLabel.setFont(new Font("Arial", Font.BOLD, 46));
        numberLabel.setForeground(Color.WHITE);
        badgePanel.add(numberLabel);

        JLabel titleLabel = new JLabel("Good Health & Well-being", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username Input Field
        JTextField usernameField = new JTextField("Enter Username...") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_TEXT_FIELD);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        usernameField.setOpaque(false);
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        usernameField.setForeground(Color.GRAY);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setMaximumSize(new Dimension(330, 45));
        usernameField.setCaretColor(Color.WHITE);

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Enter Username...")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.WHITE);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().trim().isEmpty()) {
                    usernameField.setText("Enter Username...");
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        // Sign In Execution Button
        JButton btnSignIn = new JButton("Sign In") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_SDG_GREEN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSignIn.setContentAreaFilled(false);
        btnSignIn.setBorderPainted(false);
        btnSignIn.setFocusPainted(false);
        btnSignIn.setOpaque(false);
        btnSignIn.setForeground(Color.WHITE);
        btnSignIn.setFont(new Font("Arial", Font.BOLD, 16));
        btnSignIn.setMaximumSize(new Dimension(330, 45));
        btnSignIn.setPreferredSize(new Dimension(330, 45));
        btnSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSignIn.addActionListener(e -> {
            String inputName = usernameField.getText().trim();
            if (inputName.isEmpty() || inputName.equals("Enter Username...")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid username.", "Notice", JOptionPane.WARNING_MESSAGE);
                return;
            }

            processUserLogin(inputName);

            // Dynamically update profile sub-chip values
            lblUserProfileChip.setText("<html><body style='color:#ffffff; font-weight:bold; font-family:Arial;'>" 
                    + currentUser + " <span style='color:#4CAF50;'> " + currentUserScore + " pts</span></body></html>");
            
            // Re-render sorting arrangements inside the leaderboard UI card
            refreshLeaderboardDisplay();

            cardLayout.show(mainContainer, "DashboardScreen");
        });

        badgePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSignIn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(badgePanel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(panel.add(btnSignIn));

        return panel;
    }

    /**
     * Checks if user exists via Storage interface, matching existing scores or saving baseline.
     */
    private void processUserLogin(String username) {
        boolean userFound = false;
        int savedScore = 0;

        try {
            List<String> records = storageManager.getAllScores();
            for (String line : records) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equalsIgnoreCase(username)) {
                    userFound = true;
                    savedScore = Integer.parseInt(parts[1].trim());
                    username = parts[0].trim(); // Preserve exact case
                    break;
                }
            }

            if (!userFound) {
                storageManager.saveScore(username, 0); // New user baseline initialization
                savedScore = 0;
            }
        } catch (CustomStorageException e) {
            System.err.println("Login state retrieval failure: " + e.getMessage());
        }

        this.currentUser = username;
        this.currentUserScore = savedScore;
    }

    /**
     * Build primary layout workspace hub framework.
     */
    private JPanel createDashboardPanel() {
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(COLOR_BG);

        // Header bar setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_DARK_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblDashboardTitle = new JLabel("Dashboard");
        lblDashboardTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblDashboardTitle.setForeground(Color.WHITE);
        headerPanel.add(lblDashboardTitle, BorderLayout.WEST);

        lblUserProfileChip = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 25, 26)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblUserProfileChip.setOpaque(false);
        lblUserProfileChip.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        headerPanel.add(lblUserProfileChip, BorderLayout.EAST);
        containerPanel.add(headerPanel, BorderLayout.NORTH);

        // Scroll Content Panel View
        JPanel scrollContent = new JPanel();
        scrollContent.setBackground(COLOR_BG);
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- SECTION A: THE LEARNING PORTAL BUTTONS ---
        JLabel lblSectionLearning = new JLabel("THE LEARNING PORTAL");
        lblSectionLearning.setFont(new Font("Arial", Font.BOLD, 14));
        lblSectionLearning.setForeground(new Color(140, 155, 157));
        lblSectionLearning.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollContent.add(lblSectionLearning);
        scrollContent.add(Box.createVerticalStrut(12));

        JPanel gridModules = new JPanel(new GridLayout(3, 2, 12, 12));
        gridModules.setOpaque(false);
        gridModules.setMaximumSize(new Dimension(380, 360));

        JButton btnModule1 = createModuleCard("1", "Maternal & Child\nHealth");
        JButton btnModule2 = createModuleCard("2", "Communicable &\nNon-Communicable Diseases");
        JButton btnModule3 = createModuleCard("3", "Substance Abuse &\nInjury Prevention");
        JButton btnModule4 = createModuleCard("4", "Reproductive Health");
        JButton btnModule5 = createModuleCard("5", "Universal Health\nCare");
        JButton btnModule6 = createModuleCard("6", "Health Emergencies");

        btnModule1.addActionListener(e -> launchLearningModule(1));
        btnModule2.addActionListener(e -> launchLearningModule(2));
        btnModule3.addActionListener(e -> launchLearningModule(3));
        btnModule4.addActionListener(e -> launchLearningModule(4));
        btnModule5.addActionListener(e -> launchLearningModule(5));
        btnModule6.addActionListener(e -> launchLearningModule(6));

        gridModules.add(btnModule1); gridModules.add(btnModule2);
        gridModules.add(btnModule3); gridModules.add(btnModule4);
        gridModules.add(btnModule5); gridModules.add(btnModule6);
        
        gridModules.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollContent.add(gridModules);
        scrollContent.add(Box.createVerticalStrut(25));

        // --- SECTION B: KNOWLEDGE CHECK ---
        JLabel lblSectionQuiz = new JLabel("KNOWLEDGE CHECK");
        lblSectionQuiz.setFont(new Font("Arial", Font.BOLD, 14));
        lblSectionQuiz.setForeground(new Color(140, 155, 157));
        lblSectionQuiz.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollContent.add(lblSectionQuiz);
        scrollContent.add(Box.createVerticalStrut(12));

        JPanel quizBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
            }
        };
        quizBox.setOpaque(false);
        quizBox.setLayout(new BoxLayout(quizBox, BoxLayout.Y_AXIS));
        quizBox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        quizBox.setMaximumSize(new Dimension(370, 175));
        quizBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        /* =========================================================================
         * [MEMBER 3 & 4 PLUG-IN AREA: QUIZ TIMER & PROGRESS UI LAYER]
         * The countdown timers, tracking metrics, and custom visual progress bars
         * fall under Member 3 (Quiz Module) and Member 4 (Gamification Elements).
         * ========================================================================= */
        JPanel timerRow = new JPanel(new BorderLayout());
        timerRow.setOpaque(false);
        JLabel lblTimer = new JLabel("Time Tracker: Ready");
        lblTimer.setFont(new Font("Arial", Font.BOLD, 15));
        lblTimer.setForeground(Color.WHITE);
        timerRow.add(lblTimer, BorderLayout.WEST);
        quizBox.add(timerRow);
        quizBox.add(Box.createVerticalStrut(15));

        JButton btnStartQuiz = new JButton("Start Quiz") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_SDG_GREEN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnStartQuiz.setContentAreaFilled(false);
        btnStartQuiz.setBorderPainted(false);
        btnStartQuiz.setFocusPainted(false);
        btnStartQuiz.setOpaque(false);
        btnStartQuiz.setForeground(Color.WHITE);
        btnStartQuiz.setFont(new Font("Arial", Font.BOLD, 16));
        btnStartQuiz.setMaximumSize(new Dimension(330, 45));
        btnStartQuiz.setPreferredSize(new Dimension(330, 45));
        btnStartQuiz.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnStartQuiz.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnStartQuiz.addActionListener(e -> {
            /* =========================================================================
             * [MEMBER 3 ACTION HOOK]
             * Connect this listener action directly to display Member 3's Quiz UI panel.
             * ========================================================================= */
            Quiz quizModule = new QuizModule();
            quizModule.startQuiz();
        });
        
        quizBox.add(btnStartQuiz);
        quizBox.add(Box.createVerticalStrut(15));

        JLabel lblStatus = new JLabel("Status: Unlocked / Ready", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStatus.setForeground(COLOR_SDG_GREEN);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        quizBox.add(lblStatus);

        scrollContent.add(quizBox);
        scrollContent.add(Box.createVerticalStrut(25));

        // --- SECTION C: GLOBAL LEADERBOARD DISPLAY ---
        JLabel lblSectionLeaderboard = new JLabel("GLOBAL LEADERBOARD");
        lblSectionLeaderboard.setFont(new Font("Arial", Font.BOLD, 14));
        lblSectionLeaderboard.setForeground(new Color(140, 155, 157));
        lblSectionLeaderboard.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollContent.add(lblSectionLeaderboard);
        scrollContent.add(Box.createVerticalStrut(12));

        leaderboardCardContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                g2.dispose();
            }
        };
        leaderboardCardContainer.setOpaque(false);
        leaderboardCardContainer.setLayout(new BoxLayout(leaderboardCardContainer, BoxLayout.Y_AXIS));
        leaderboardCardContainer.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        leaderboardCardContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        leaderboardCardContainer.setMaximumSize(new Dimension(370, 240));

        scrollContent.add(leaderboardCardContainer);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        containerPanel.add(scrollPane, BorderLayout.CENTER);

        return containerPanel;
    }

    /**
     * Prepares structural container layout for the leaderboard card panel.
     */
    private void refreshLeaderboardDisplay() {
        leaderboardCardContainer.removeAll();

        /* =========================================================================
         * [MEMBER 4 TASK MATRIX: LEADERBOARD SORTING, MEDALS, AND GAMIFICATION DISPLAY]
         * Sabrina Natasha is responsible for sorting data rows and appending badges/rewards.
         * This safe hook placeholder calls your Storage implementation and renders them safely.
         * ========================================================================= */
        try {
            List<String> recordsList = storageManager.getAllScores();

            Leaderboard leaderboard = new Leaderboard();
            leaderboard.sortScores(recordsList);

            int displayCount = 1;
            for (String entryLine : recordsList) {
                if (displayCount > 4) break; 
                
                String[] segments = entryLine.split(",");
                String nameStr = segments[0];
                String scoreStr = segments.length > 1 ? segments[1] : "0";
                String medal = leaderboard.getMedal(displayCount);

                JPanel rowPanel = new JPanel(new BorderLayout());
                rowPanel.setOpaque(false);
                rowPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

                JLabel lblLeft = new JLabel(
                        "<html><body style='font-family:Arial; font-size:14px; color:#ffffff;'>"
                        + "<span style='color:#8c9b9d; font-weight:bold;'>"
                        + displayCount + " " + medal
                        + "</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                        + "<b>" + nameStr + "</b>"
                        + "</body></html>"
                );

                rowPanel.add(lblLeft, BorderLayout.WEST);

                JLabel lblRight = new JLabel(
                        "<html><body style='font-family:Arial; font-size:14px; font-weight:bold; color:#4CAF50;'>"
                        + scoreStr
                        + " <span style='font-size:11px; color:#ffffff;'>pts</span>"
                        + "</body></html>"
                );

                rowPanel.add(lblRight, BorderLayout.EAST);

                leaderboardCardContainer.add(rowPanel);

                if (displayCount < 4 && displayCount < recordsList.size()) {
                    JSeparator sep = new JSeparator();
                    sep.setForeground(new Color(50, 68, 70));
                    sep.setBackground(new Color(50, 68, 70));
                    leaderboardCardContainer.add(sep);
                }
                displayCount++;
            }
        } catch (CustomStorageException e) {
            System.err.println("Leaderboard system access fault: " + e.getMessage());
        }

        leaderboardCardContainer.revalidate();
        leaderboardCardContainer.repaint();
    }

    /**
     * Generates a stylized clickable JButton for each learning module topic card.
     */
    private JButton createModuleCard(String indexNum, String textTitle) {
        JButton cardButton = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(COLOR_CARD_BG.brighter());
                } else {
                    g2.setColor(COLOR_CARD_BG);
                }
                g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        cardButton.setContentAreaFilled(false);
        cardButton.setBorderPainted(false);
        cardButton.setFocusPainted(false);
        cardButton.setOpaque(false);
        cardButton.setLayout(new BorderLayout());
        cardButton.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        cardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIndex = new JLabel(indexNum, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 78, 82)); 
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblIndex.setOpaque(false);
        lblIndex.setPreferredSize(new Dimension(24, 24));
        lblIndex.setFont(new Font("Arial", Font.BOLD, 12));
        lblIndex.setForeground(Color.WHITE);

        JPanel indexAligner = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        indexAligner.setOpaque(false);
        indexAligner.add(lblIndex);
        cardButton.add(indexAligner, BorderLayout.NORTH);

        JLabel lblTitle = new JLabel("<html><body style='color:#ffffff; font-family:Arial; font-size:11px; font-weight:bold;'>" 
                + textTitle.replace("\n", "<br>") + "</body></html>");
        lblTitle.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        cardButton.add(lblTitle, BorderLayout.CENTER);

        return cardButton;
    }

    private void launchLearningModule(int moduleId) {
        /* =========================================================================
         * [BEGINNING OF CHAN KA HOU 103617's ACTION HOOK]
         * Connect these click triggers directly to Chan Ka Hou's Educational Screen frames.
         * ========================================================================= */
       try {
           Learning learningModule = new LearningModule(moduleId);
           learningModule.showContent(this);
       } catch (LearningContentException e) {
           JOptionPane.showMessageDialog(
                   this,
                   e.getMessage(),
                   "Learning Module Error",
                   JOptionPane.ERROR_MESSAGE
           );
       }
   }
        /* =========================================================================
         * [END OF CHAN KA HOU 103617's ACTION HOOK]
         * ========================================================================= */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(mainAppFrame::new);
    }
}
