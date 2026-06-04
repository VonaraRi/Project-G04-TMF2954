/**
 * Automated command-line tests for Learning Module.
 *
 * Creator: Chan Ka Hou 103617
 * Tester: Rionnalyn 106148
 */

public class LearningModuleAutomatedTest {

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) {
        System.out.println("Running Learning Module automated tests...\n");

        runTest("LearningModule can be created from intro page", LearningModuleAutomatedTest::testDefaultConstructor);
        runTest("LearningModule can be created from all dashboard module buttons", LearningModuleAutomatedTest::testDashboardModuleConstructors);
        runTest("LearningModule works through the Learning interface", LearningModuleAutomatedTest::testInterfaceReference);
        runTest("LearningModule navigation methods do not crash at page limits", LearningModuleAutomatedTest::testNavigationDoesNotCrash);
        runTest("LearningModule can reload content without crashing", LearningModuleAutomatedTest::testReloadContent);
        runTest("LearningContentException gives a clear error message", LearningModuleAutomatedTest::testLearningContentExceptionMessage);

        System.out.println("\nTests passed: " + testsPassed + " / " + testsRun);

        if (testsPassed == testsRun) {
            System.out.println("RESULT: ALL TESTS PASSED");
        } else {
            System.out.println("RESULT: SOME TESTS FAILED");
            System.exit(1);
        }
    }

    private static void testDefaultConstructor() throws LearningContentException {
        LearningModule module = new LearningModule();
        assertNotNull(module, "LearningModule object should not be null.");
    }

    private static void testDashboardModuleConstructors() throws LearningContentException {
        for (int moduleId = 1; moduleId <= 6; moduleId++) {
            LearningModule module = new LearningModule(moduleId);
            assertNotNull(module, "LearningModule should be created for module ID " + moduleId + ".");
        }

        LearningModule defaultModule = new LearningModule(99);
        assertNotNull(defaultModule, "Invalid module ID should safely open the overview page.");
    }

    private static void testInterfaceReference() throws LearningContentException {
        Learning module = new LearningModule(2);
        module.nextPage();
        module.previousPage();
        assertNotNull(module, "LearningModule should work when stored as a Learning interface reference.");
    }

    private static void testNavigationDoesNotCrash() throws LearningContentException {
        LearningModule module = new LearningModule();

        // Move beyond the expected final page. The method should stop safely at the end.
        for (int i = 0; i < 20; i++) {
            module.nextPage();
        }

        // Move beyond the expected first page. The method should stop safely at the beginning.
        for (int i = 0; i < 20; i++) {
            module.previousPage();
        }
    }

    private static void testReloadContent() throws LearningContentException {
        LearningModule module = new LearningModule(3);
        module.nextPage();
        module.loadContent();
        module.nextPage();
        module.previousPage();
    }

    private static void testLearningContentExceptionMessage() {
        LearningContentException exception = new LearningContentException("Test message");
        assertTrue(
                exception.getMessage().contains("Learning Content Error"),
                "Custom exception message should clearly identify a learning content error."
        );
    }

    private static void runTest(String testName, TestAction testAction) {
        testsRun++;
        try {
            testAction.run();
            testsPassed++;
            System.out.println("[PASS] " + testName);
        } catch (Throwable error) {
            System.out.println("[FAIL] " + testName);
            System.out.println("       " + error.getMessage());
        }
    }

    private static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private interface TestAction {
        void run() throws Exception;
    }
}