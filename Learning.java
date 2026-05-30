import javax.swing.JFrame;

/**
 * Java Interface for Member 2's Learning Module.
 * This is not a Graphical User Interface; it defines the behaviours that the learning module must provide.
 *
 * Creator: Member 2
 * Tester:
 */
public interface Learning {
    /**
     * Loads all SDG 3 educational pages into the learning module.
     */
    void loadContent() throws LearningContentException;

    /**
     * Displays the learning content screen from the selected topic.
     */
    void showContent(JFrame parent);

    /**
     * Moves to the next learning screen.
     */
    void nextPage();

    /**
     * Moves to the previous learning screen.
     */
    void previousPage();
}
