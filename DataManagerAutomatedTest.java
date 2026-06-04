import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Automated command-line tests for Storage, DataManager, and CustomStorageException.
 *
 * Creator: Chan Ka Hou 103617
 * Tester: Rionnalyn 106148
 *
 * Note:
 * These tests focus on CSV storage.
 */
public class DataManagerAutomatedTest {

    private static final Path CSV_FILE = Paths.get("sdg3_scores.csv");
    private static final Path CSV_BACKUP = Paths.get("sdg3_scores_backup_for_test.csv");

    private static int testsRun = 0;
    private static int testsPassed = 0;

    public static void main(String[] args) throws IOException {
        System.out.println("Running DataManager automated tests...\n");

        try {
            backupExistingCsvFile();

            runTest("Default constructor creates CSV file with header", DataManagerAutomatedTest::testDefaultConstructorCreatesCsv);
            runTest("Overloaded constructor using CSV creates CSV file", DataManagerAutomatedTest::testOverloadedConstructorFalse);
            runTest("saveScore stores one score in CSV", DataManagerAutomatedTest::testSaveOneScore);
            runTest("saveScore stores multiple scores in CSV", DataManagerAutomatedTest::testSaveMultipleScores);
            runTest("getAllScores skips the CSV header row", DataManagerAutomatedTest::testGetAllScoresSkipsHeader);
            runTest("DataManager works through Storage interface", DataManagerAutomatedTest::testStorageInterfaceReference);
            runTest("CustomStorageException keeps message and cause", DataManagerAutomatedTest::testCustomStorageExceptionMessageAndCause);

        } finally {
            try {
                restoreExistingCsvFile();
            } catch (IOException e) {
                System.out.println("[WARNING] Could not restore original CSV file: " + e.getMessage());
            }
        }

        System.out.println("\nTests passed: " + testsPassed + " / " + testsRun);

        if (testsPassed == testsRun) {
            System.out.println("RESULT: ALL TESTS PASSED");
        } else {
            System.out.println("RESULT: SOME TESTS FAILED");
            System.exit(1);
        }
    }

    private static void testDefaultConstructorCreatesCsv() throws Exception {
        resetCsvFile();

        new DataManager();

        assertTrue(Files.exists(CSV_FILE), "CSV file should be created by the default constructor.");

        List<String> lines = Files.readAllLines(CSV_FILE);
        assertTrue(lines.size() >= 1, "CSV file should contain a header row.");
        assertEquals("Username,Score,Timestamp", lines.get(0), "CSV header row is incorrect.");
    }

    private static void testOverloadedConstructorFalse() throws Exception {
        resetCsvFile();

        new DataManager(false);

        assertTrue(Files.exists(CSV_FILE), "CSV file should be created when useDatabase is false.");
    }

    private static void testSaveOneScore() throws Exception {
        resetCsvFile();

        DataManager manager = new DataManager(false);
        manager.saveScore("testUser", 85);

        List<String> records = manager.getAllScores();

        assertEquals(1, records.size(), "There should be exactly one saved score record.");
        assertTrue(
                records.get(0).startsWith("testUser,85,"),
                "Saved record should contain username and score."
        );
    }

    private static void testSaveMultipleScores() throws Exception {
        resetCsvFile();

        DataManager manager = new DataManager(false);
        manager.saveScore("alice", 90);
        manager.saveScore("bob", 60);
        manager.saveScore("charlie", 30);

        List<String> records = manager.getAllScores();

        assertEquals(3, records.size(), "There should be three saved score records.");
        assertTrue(records.get(0).startsWith("alice,90,"), "First score record is incorrect.");
        assertTrue(records.get(1).startsWith("bob,60,"), "Second score record is incorrect.");
        assertTrue(records.get(2).startsWith("charlie,30,"), "Third score record is incorrect.");
    }

    private static void testGetAllScoresSkipsHeader() throws Exception {
        resetCsvFile();

        DataManager manager = new DataManager(false);
        manager.saveScore("headerTest", 10);

        List<String> records = manager.getAllScores();

        assertEquals(1, records.size(), "Only one data row should be returned.");
        assertFalse(
                records.get(0).equals("Username,Score,Timestamp"),
                "getAllScores should not return the CSV header."
        );
    }

    private static void testStorageInterfaceReference() throws Exception {
        resetCsvFile();

        Storage storage = new DataManager(false);
        storage.saveScore("interfaceUser", 75);

        List<String> records = storage.getAllScores();

        assertEquals(1, records.size(), "Storage interface should return one saved score.");
        assertTrue(
                records.get(0).startsWith("interfaceUser,75,"),
                "Storage interface should correctly call DataManager implementation."
        );
    }

    private static void testCustomStorageExceptionMessageAndCause() {
        IOException cause = new IOException("Original file problem");
        CustomStorageException exception = new CustomStorageException("Could not save score.", cause);

        assertTrue(
                exception.getMessage().contains("Storage Error: Could not save score."),
                "CustomStorageException should include the storage error prefix and message."
        );

        assertEquals(cause, exception.getCause(), "CustomStorageException should keep the original cause.");
    }

    private static void resetCsvFile() throws IOException {
        Files.deleteIfExists(CSV_FILE);
    }

    private static void backupExistingCsvFile() throws IOException {
        Files.deleteIfExists(CSV_BACKUP);

        if (Files.exists(CSV_FILE)) {
            Files.copy(CSV_FILE, CSV_BACKUP);
        }
    }

    private static void restoreExistingCsvFile() throws IOException {
        Files.deleteIfExists(CSV_FILE);

        if (Files.exists(CSV_BACKUP)) {
            Files.move(CSV_BACKUP, CSV_FILE);
        }
    }

    private static void runTest(String testName, TestAction action) {
        testsRun++;

        try {
            action.run();
            testsPassed++;
            System.out.println("[PASS] " + testName);
        } catch (Throwable error) {
            System.out.println("[FAIL] " + testName);
            System.out.println("       " + error.getMessage());
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) {
            return;
        }

        if (expected != null && expected.equals(actual)) {
            return;
        }

        throw new AssertionError(message + " Expected: " + expected + ", Actual: " + actual);
    }

    private interface TestAction {
        void run() throws Exception;
    }
}
