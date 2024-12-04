package QuestionDatabase;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A utility class for importing and setting up a database for storing question data.
 * The database includes tables for multiple-choice, boolean, and text-input questions.
 *
 * @author Mathew Miller
 * @author Jacob Klymenko (Javadoc)
 * @version 1.0
 */
public final class DataImporter {

    /** The database connection for managing queries. */
    private Connection myConn;

    /**
     * Sets up the database by creating tables if they do not already exist.
     * Tables include:
     * - multiple_choice_question
     * - boolean_question
     * - text_input_question
     */
    private void setupDatabase(){
        SQLiteDataSource qdb;

        try {
            qdb = new SQLiteDataSource();
            qdb.setUrl("jdbc:sqlite:questions.sqlite");
            myConn = qdb.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        String query = """
                CREATE TABLE IF NOT EXISTS multiple_choice_question
                (
                    question       TEXT NOT NULL,
                    answer         TEXT NOT NULL,
                    wrong_answer_1 TEXT NOT NULL,
                    wrong_answer_2 TEXT NOT NULL,
                    wrong_answer_3 TEXT NOT NULL
                );
                """;
        try (Statement stmt = myConn.createStatement();) {
            int rv = stmt.executeUpdate(query);
            System.out.println("executeUpdate() returned " + rv);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        query = """
                CREATE TABLE IF NOT EXISTS boolean_question
                (
                    question     TEXT NOT NULL,
                    answer       BOOLEAN NOT NULL
                );
                """;

        try (Statement stmt = myConn.createStatement();) {
            int rv = stmt.executeUpdate(query);
            System.out.println("executeUpdate() returned " + rv);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        query = """
                CREATE TABLE IF NOT EXISTS text_input_question
                (
                    question TEXT NOT NULL,
                    answer   TEXT NOT NULL
                );
                """;

        try (Statement stmt = myConn.createStatement();) {
            int rv = stmt.executeUpdate(query);
            System.out.println("executeUpdate() returned " + rv);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Main method to execute the database setup.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        DataImporter di = new DataImporter();
        di.setupDatabase();
    }
}
