import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Learning Module for SDG 3: Good Health and Well-being.
 * Provides at least 10 educational screens with text and generated Java2D images.
 *
 * Creator: Chan Ka Hou 103617 (Learning module + educational content)
 * Tester:
 */

public class LearningModule implements Learning {

    private final List<LearningPage> contentPages = new ArrayList<>();
    private int currentPageIndex = 0;
    private final int selectedModuleId;

    private JDialog dialog;
    private JLabel lblTitle;
    private JLabel lblTopic;
    private JLabel lblImage;
    private JLabel lblPageCounter;
    private JTextArea txtContent;
    private JButton btnPrevious;
    private JButton btnNext;

    private final Color COLOR_BG = new Color(30, 45, 47);
    private final Color COLOR_DARK_HEADER = new Color(23, 34, 36);
    private final Color COLOR_SDG_GREEN = new Color(76, 175, 80);
    private final Color COLOR_CARD_BG = new Color(38, 54, 56);
    private final Color COLOR_BORDER = new Color(70, 85, 87);
    private final Color COLOR_MUTED_TEXT = new Color(170, 184, 186);

    // Overloaded constructor: starts from the intro page.

    public LearningModule() throws LearningContentException {
        this(0);
    }

    /**
     * Overloaded constructor: starts from the dashboard topic selected by Member 1's GUI.
     *
     * @param moduleId dashboard module number from 1 to 6.
     */

    public LearningModule(int moduleId) throws LearningContentException {
        this.selectedModuleId = moduleId;
        loadContent();
        this.currentPageIndex = mapModuleToStartPage(moduleId);
    }

    // Loads 10 educational content screens for SDG 3.

    @Override
    public void loadContent() throws LearningContentException {
        contentPages.clear();

        contentPages.add(new LearningPage(
                "SDG 3 Overview",
                "Good Health and Well-being",
                "SDG 3 focuses on making healthy lives possible for all people.\n\n" +
                        "Key idea:\n" +
                        "• Health is not only about hospitals. It includes prevention, education, safe behaviour, emergency readiness, and fair access to care.\n\n" +
                        "In this application, you will learn the main health topics, then check your understanding through the quiz module.",
                "overview"));

        contentPages.add(new LearningPage(
                "Learning Screen 1",
                "Maternal & Child Health",
                "Maternal and child health protects mothers, babies, and young children before, during, and after birth.\n\n" +
                        "Important actions:\n" +
                        "• Attend antenatal check-ups.\n" +
                        "• Ensure safe delivery with trained health workers.\n" +
                        "• Give babies proper nutrition and immunisation.\n" +
                        "• Watch for danger signs such as fever, bleeding, dehydration, or breathing difficulty.",
                "maternal"));

        contentPages.add(new LearningPage(
                "Learning Screen 2",
                "Communicable Diseases",
                "Communicable diseases can spread from one person to another through air, water, insects, blood, or direct contact.\n\n" +
                        "Examples include influenza, tuberculosis, dengue, measles, and COVID-19.\n\n" +
                        "Prevention habits:\n" +
                        "• Wash hands regularly.\n" +
                        "• Wear masks when appropriate.\n" +
                        "• Complete vaccinations.\n" +
                        "• Avoid sharing personal items.\n" +
                        "• Remove stagnant water to reduce mosquito breeding.",
                "communicable"));

        contentPages.add(new LearningPage(
                "Learning Screen 3",
                "Non-Communicable Diseases",
                "Non-communicable diseases do not spread directly between people. They usually develop over time.\n\n" +
                        "Examples include diabetes, heart disease, stroke, asthma, and cancer.\n\n" +
                        "Risk reduction:\n" +
                        "• Eat balanced meals.\n" +
                        "• Exercise regularly.\n" +
                        "• Avoid smoking and harmful alcohol use.\n" +
                        "• Go for screening when recommended.\n" +
                        "• Manage stress and sleep properly.",
                "ncd"));

        contentPages.add(new LearningPage(
                "Learning Screen 4",
                "Substance Abuse & Injury Prevention",
                "Substance abuse harms physical health, mental health, relationships, and community safety. Injury prevention reduces avoidable accidents.\n\n" +
                        "Safe choices:\n" +
                        "• Say no to drugs and misuse of medication.\n" +
                        "• Use helmets and seatbelts.\n" +
                        "• Avoid driving when tired or impaired.\n" +
                        "• Keep sharp objects, chemicals, and medicine away from children.\n" +
                        "• Ask for help early when addiction warning signs appear.",
                "injury"));

        contentPages.add(new LearningPage(
                "Learning Screen 5",
                "Reproductive Health",
                "Reproductive health means people can make informed, safe, and respectful decisions about their bodies and families.\n\n" +
                        "It includes:\n" +
                        "• Puberty education.\n" +
                        "• Menstrual health.\n" +
                        "• Family planning information.\n" +
                        "• Prevention and treatment of reproductive infections.\n" +
                        "• Respect for consent, privacy, and personal safety.",
                "reproductive"));

        contentPages.add(new LearningPage(
                "Learning Screen 6",
                "Universal Health Care",
                "Universal Health Care means people should be able to receive needed health services without being pushed into financial hardship.\n\n" +
                        "It includes:\n" +
                        "• Health promotion and prevention.\n" +
                        "• Treatment and rehabilitation.\n" +
                        "• Access to essential medicines.\n" +
                        "• Affordable services for every community.\n\n" +
                        "Fair access matters because health should not depend only on income or location.",
                "uhc"));

        contentPages.add(new LearningPage(
                "Learning Screen 7",
                "Mental Health & Well-being",
                "Mental health affects how people think, feel, study, work, and connect with others.\n\n" +
                        "Supportive habits:\n" +
                        "• Talk to trusted people when overwhelmed.\n" +
                        "• Rest, exercise, and keep a healthy routine.\n" +
                        "• Reduce harmful comparison online.\n" +
                        "• Seek professional help when stress, anxiety, or sadness becomes hard to manage.\n\n" +
                        "Asking for help is a strength, not a weakness.",
                "mental"));

        contentPages.add(new LearningPage(
                "Learning Screen 8",
                "Health Emergencies",
                "Health emergencies include disease outbreaks, natural disasters, injuries, poisoning, heat illness, and sudden medical problems.\n\n" +
                        "Preparedness steps:\n" +
                        "• Know local emergency numbers.\n" +
                        "• Keep a first-aid kit.\n" +
                        "• Follow verified health announcements.\n" +
                        "• Do not spread rumours during a crisis.\n" +
                        "• Help vulnerable people such as children, older adults, and disabled people.",
                "emergency"));

        contentPages.add(new LearningPage(
                "Learning Screen 9",
                "Daily Healthy Lifestyle",
                "Small daily actions can prevent many health problems.\n\n" +
                        "Healthy routine checklist:\n" +
                        "• Drink enough water.\n" +
                        "• Eat fruits, vegetables, and balanced meals.\n" +
                        "• Sleep 7 to 9 hours when possible.\n" +
                        "• Move your body every day.\n" +
                        "• Limit sugary drinks and ultra-processed food.\n" +
                        "• Take breaks from screens and protect your eyes.",
                "lifestyle"));

        contentPages.add(new LearningPage(
                "Learning Screen 10",
                "How You Can Support SDG 3",
                "Everyone can contribute to Good Health and Well-being.\n\n" +
                        "You can:\n" +
                        "• Share accurate health information.\n" +
                        "• Encourage friends to complete vaccinations and screenings.\n" +
                        "• Avoid stigma toward illness or mental health struggles.\n" +
                        "• Practise safe habits and model them for others.\n" +
                        "• Join community health campaigns when available.\n\n" +
                        "Next step: return to the dashboard and start the knowledge check.",
                "action"));

        if (contentPages.size() < 10) {
            throw new LearningContentException("At least 10 learning screens are required.");
        }
    }

    // Displays the learning module in a desktop window sized like a smartphone screen.

    @Override
    public void showContent(JFrame parent) {
        if (dialog == null) {
            dialog = new JDialog(parent, "SDG 3 Learning Module", false);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setSize(420, 760);
            dialog.setResizable(false);
            dialog.setContentPane(createLearningPanel());
            dialog.setLocationRelativeTo(parent);
        }
        updatePageView();
        dialog.setVisible(true);
    }

    // Moves to the next learning screen.

    @Override
    public void nextPage() {
        if (currentPageIndex < contentPages.size() - 1) {
            currentPageIndex++;
            updatePageView();
        }
    }

    // Moves to the previous learning screen.

    @Override
    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            updatePageView();
        }
    }

    private JPanel createLearningPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COLOR_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_DARK_HEADER);
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        lblTitle = new JLabel("Learning Module");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        header.add(lblTitle, BorderLayout.WEST);

        JButton btnClose = new JButton("Back");
        btnClose.setFocusPainted(false);
        btnClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBackground(COLOR_SDG_GREEN);
        btnClose.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btnClose.addActionListener(e -> dialog.dispose());
        header.add(btnClose, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(COLOR_BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 22, 20, 22));

        lblPageCounter = new JLabel("Page 1 of 10");
        lblPageCounter.setFont(new Font("Arial", Font.BOLD, 13));
        lblPageCounter.setForeground(COLOR_SDG_GREEN);
        lblPageCounter.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblPageCounter);
        content.add(Box.createVerticalStrut(10));

        JPanel card = new JPanel();
        card.setBackground(COLOR_CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDER),
                new EmptyBorder(16, 16, 16, 16)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(360, 560));

        lblTopic = new JLabel("Topic");
        lblTopic.setForeground(Color.WHITE);
        lblTopic.setFont(new Font("Arial", Font.BOLD, 17));
        lblTopic.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblTopic);
        card.add(Box.createVerticalStrut(14));

        lblImage = new JLabel();
        lblImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImage.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        card.add(lblImage);
        card.add(Box.createVerticalStrut(16));

        txtContent = new JTextArea();
        txtContent.setEditable(false);
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        txtContent.setOpaque(false);
        txtContent.setForeground(Color.WHITE);
        txtContent.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContent.setBorder(null);
        txtContent.setFocusable(false);
        card.add(txtContent);

        content.add(card);
        content.add(Box.createVerticalStrut(16));

        JPanel controls = new JPanel(new GridLayout(1, 2, 12, 0));
        controls.setOpaque(false);
        controls.setMaximumSize(new Dimension(360, 45));
        controls.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnPrevious = createNavigationButton("Previous");
        btnPrevious.addActionListener(e -> previousPage());
        controls.add(btnPrevious);

        btnNext = createNavigationButton("Next");
        btnNext.addActionListener(e -> {
            if (currentPageIndex == contentPages.size() - 1) {
                dialog.dispose();
            } else {
                nextPage();
            }
        });
        controls.add(btnNext);

        content.add(controls);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        root.add(scrollPane, BorderLayout.CENTER);

        return root;
    }

    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(COLOR_SDG_GREEN);
        button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        return button;
    }

    private void updatePageView() {
        if (contentPages.isEmpty() || lblTitle == null) {
            return;
        }

        LearningPage page = contentPages.get(currentPageIndex);
        lblTitle.setText(page.getScreenTitle());
        lblTopic.setText("<html>" + page.getTopicTitle() + "</html>");
        txtContent.setText(page.getBodyText());
        txtContent.setCaretPosition(0);
        lblImage.setIcon(new ImageIcon(createVisualImage(page.getVisualCode(), 310, 150)));
        lblPageCounter.setText("Page " + (currentPageIndex + 1) + " of " + contentPages.size());

        btnPrevious.setEnabled(currentPageIndex > 0);
        btnNext.setText(currentPageIndex == contentPages.size() - 1 ? "Finish" : "Next");
    }

    private int mapModuleToStartPage(int moduleId) {
        switch (moduleId) {
            case 1:
                return 1; // Maternal & Child Health
            case 2:
                return 2; // Communicable & Non-Communicable Diseases
            case 3:
                return 4; // Substance Abuse & Injury Prevention
            case 4:
                return 5; // Reproductive Health
            case 5:
                return 6; // Universal Health Care
            case 6:
                return 8; // Health Emergencies
            default:
                return 0; // Overview
        }
    }

    private BufferedImage createVisualImage(String visualCode, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(20, 33, 35));
        g2.fillRoundRect(0, 0, width - 1, height - 1, 18, 18);
        g2.setColor(new Color(56, 74, 77));
        g2.drawRoundRect(0, 0, width - 1, height - 1, 18, 18);

        g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        if ("maternal".equals(visualCode)) {
            drawCircle(g2, 95, 65, 36, COLOR_SDG_GREEN);
            drawCircle(g2, 165, 75, 24, new Color(125, 198, 130));
            g2.setColor(Color.WHITE);
            g2.drawArc(75, 70, 90, 55, 200, 140);
            g2.drawString("Care", 198, 82);
        } else if ("communicable".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            for (int i = 0; i < 5; i++) {
                int x = 55 + i * 48;
                g2.drawOval(x, 50, 28, 28);
                g2.drawLine(x + 14, 44, x + 14, 35);
                g2.drawLine(x + 14, 84, x + 14, 96);
                g2.drawLine(x - 6, 64, x - 18, 64);
                g2.drawLine(x + 34, 64, x + 46, 64);
            }
            g2.setColor(Color.WHITE);
            g2.drawString("Prevent Spread", 84, 125);
        } else if ("ncd".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.drawLine(45, 85, 85, 85);
            g2.drawLine(85, 85, 105, 45);
            g2.drawLine(105, 45, 135, 105);
            g2.drawLine(135, 105, 160, 65);
            g2.drawLine(160, 65, 250, 65);
            g2.setColor(Color.WHITE);
            g2.drawString("Screen Early", 90, 130);
        } else if ("injury".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.fillRoundRect(70, 45, 75, 60, 15, 15);
            g2.setColor(new Color(180, 60, 60));
            g2.drawLine(185, 45, 245, 105);
            g2.drawLine(245, 45, 185, 105);
            g2.setColor(Color.WHITE);
            g2.drawString("Safety First", 92, 130);
        } else if ("reproductive".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.drawOval(70, 45, 65, 65);
            g2.drawLine(102, 110, 102, 130);
            g2.drawLine(88, 120, 116, 120);
            g2.setColor(new Color(125, 198, 130));
            g2.drawOval(178, 45, 65, 65);
            g2.drawLine(211, 110, 211, 130);
            g2.setColor(Color.WHITE);
            g2.drawString("Respect + Care", 91, 30);
        } else if ("uhc".equals(visualCode)) {
            Polygon shield = new Polygon();
            shield.addPoint(155, 30);
            shield.addPoint(230, 55);
            shield.addPoint(215, 120);
            shield.addPoint(155, 140);
            shield.addPoint(95, 120);
            shield.addPoint(80, 55);
            g2.setColor(COLOR_SDG_GREEN);
            g2.fillPolygon(shield);
            g2.setColor(Color.WHITE);
            g2.fillRect(142, 67, 26, 58);
            g2.fillRect(126, 83, 58, 26);
        } else if ("mental".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.drawOval(75, 15, 90, 85);
            g2.drawArc(95, 45, 45, 35, 200, 140);
            g2.setColor(Color.WHITE);
            g2.drawString("Talk. Rest. Seek Help.", 62, 135);
        } else if ("emergency".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.fillRoundRect(90, 45, 130, 80, 14, 14);
            g2.setColor(Color.WHITE);
            g2.fillRect(145, 62, 20, 46);
            g2.fillRect(132, 75, 46, 20);
            g2.setColor(COLOR_MUTED_TEXT);
            g2.drawString("Emergency Ready", 77, 30);
        } else if ("lifestyle".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.drawOval(70, 5, 110, 110);
            g2.drawLine(125, 5, 125, 115);
            g2.drawLine(70, 60, 180, 60);
            g2.setColor(Color.WHITE);
            g2.drawString("Move + Eat + Sleep", 68, 135);
        } else if ("action".equals(visualCode)) {
            g2.setColor(COLOR_SDG_GREEN);
            g2.drawLine(70, 105, 115, 105);
            g2.drawLine(115, 105, 135, 75);
            g2.drawLine(135, 75, 165, 105);
            g2.drawLine(165, 105, 230, 105);
            g2.setColor(Color.WHITE);
            g2.drawString("Support SDG 3", 88, 55);
        } else {
            g2.setColor(COLOR_SDG_GREEN);
            g2.drawOval(75, 5, 110, 110);
            g2.drawLine(130, 8, 130, 113);
            g2.drawLine(80, 61, 180, 61);
            g2.setColor(Color.WHITE);
            g2.drawString("Health for All", 86, 140);
        }

        g2.dispose();
        return image;
    }

    private void drawCircle(Graphics2D g2, int x, int y, int radius, Color color) {
        g2.setColor(color);
        g2.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    // Private helper class for storing one learning page.

    private static class LearningPage {
        private final String screenTitle;
        private final String topicTitle;
        private final String bodyText;
        private final String visualCode;

        LearningPage(String screenTitle, String topicTitle, String bodyText, String visualCode) {
            this.screenTitle = screenTitle;
            this.topicTitle = topicTitle;
            this.bodyText = bodyText;
            this.visualCode = visualCode;
        }

        String getScreenTitle() {
            return screenTitle;
        }

        String getTopicTitle() {
            return topicTitle;
        }

        String getBodyText() {
            return bodyText;
        }

        String getVisualCode() {
            return visualCode;
        }
    }
}
