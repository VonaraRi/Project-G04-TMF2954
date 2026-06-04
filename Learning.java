import javax.swing.JFrame;

/**
 * Java Interface for Learning Module.
 * This is not a GUI; it only defines the behaviours that the learning module must provide.
 *
 * Creator: Chan Ka Hou 103617
 * Tester: Rionnalyn 106148
 */

public interface Learning {
    // Loads all SDG 3 educational pages into the learning module.
    void loadContent() throws LearningContentException;

    // Displays the learning content screen from the selected topic.
    void showContent(JFrame parent);

    // Moves to the next learning screen.
    void nextPage();

    //Moves to the previous learning screen.
    void previousPage();
}
