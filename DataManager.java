import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation handling both CSV and Java DB storage mechanisms.
 * and overriding interface contracts.
 * * Creator: Rionnalyn 106148 (GUI + Data Storage + Integration)
 * Tester: 
 */
public class DataManager implements Storage {
    
    private final String csvFilePath = "sdg3_scores.csv";
    private final String dbUrl = "jdbc:derby:sdg3_db;create=true";
    private boolean useDatabase;

    // --- METHOD OVERLOADING (Constructors) ---
    // Constructor 1: Default constructor flags to use CSV storage
    public DataManager() {
        this.useDatabase = false;
        initializeCsv();
    }

    // Constructor 2: Overloaded constructor allowing choice of storage medium
    public DataManager(boolean useDatabase) {
        this.useDatabase = useDatabase;
        if (useDatabase) {
            initializeDatabase();
        } else {
            initializeCsv();
        }
    }

    private void initializeCsv() {
        File file = new File(csvFilePath);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println("Username,Score,Timestamp"); // Header row
            } catch (IOException e) {
                System.err.println("Failed to initialize CSV file: " + e.getMessage());
            }
        }
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            
            DatabaseMetaData dbMeta = conn.getMetaData();
            try (ResultSet rs = dbMeta.getTables(null, null, "SCORES", null)) {
                if (!rs.next()) {
                    stmt.executeUpdate("CREATE TABLE SCORES (" +
                            "id INT GENERATED ALWAYS AS IDENTITY, " +
                            "username VARCHAR(50), " +
                            "score INT, " +
                            "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
                    System.out.println("Java DB Scores table created successfully.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed. Falling back to CSV. " + e.getMessage());
            this.useDatabase = false; // Fallback safety
            initializeCsv();
        }
    }

    // --- INTERFACE IMPLEMENTATION & METHOD OVERRIDING ---
    @Override
    public void saveScore(String username, int score) throws CustomStorageException {
        if (useDatabase) {
            saveToDatabase(username, score);
        } else {
            saveToCsv(username, score);
        }
    }

    @Override
    public List<String> getAllScores() throws CustomStorageException {
        if (useDatabase) {
            return readFromDatabase();
        } else {
            return readFromCsv();
        }
    }

    // --- PRIVATE STORAGE ACTIONS ---
    private void saveToCsv(String username, int score) throws CustomStorageException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath, true))) {
            long now = System.currentTimeMillis();
            writer.println(username + "," + score + "," + new Timestamp(now));
        } catch (IOException e) {
            throw new CustomStorageException("Failed to write record to CSV file.", e);
        }
    }

    private List<String> readFromCsv() throws CustomStorageException {
        List<String> scoreList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                scoreList.add(line); // Pass raw CSV row to let Member 4 handle leaderboard sorting/parsing
            }
        } catch (IOException e) {
            throw new CustomStorageException("Failed to read records from CSV file.", e);
        }
        return scoreList;
    }

    private void saveToDatabase(String username, int score) throws CustomStorageException {
        String sql = "INSERT INTO SCORES (username, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CustomStorageException("Failed to insert score record inside Java DB.", e);
        }
    }

    private List<String> readFromDatabase() throws CustomStorageException {
        List<String> scoreList = new ArrayList<>();
        String sql = "SELECT username, score, timestamp FROM SCORES ORDER BY score DESC";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                scoreList.add(rs.getString("username") + "," + rs.getInt("score") + "," + rs.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {
            throw new CustomStorageException("Failed to extract score data from Java DB.", e);
        }
        return scoreList;
    }
}
