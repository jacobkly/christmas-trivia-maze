package QuestionDatabase;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataImporter {

    private Connection myConn;

    private void setupDatabase(){
        SQLiteDataSource qdb = null;


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


    public static void main(String[] args) {
        DataImporter di = new DataImporter();
        di.setupDatabase();

}

}
