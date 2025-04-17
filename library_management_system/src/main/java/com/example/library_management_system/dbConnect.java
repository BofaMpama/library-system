package com.example.library_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbConnect {
    private static final String MEMBERS_DB_URL = "jdbc:sqlite:yada_lms.db";

    public static Connection connection(){
        try {
            return DriverManager.getConnection(MEMBERS_DB_URL);
        } catch (SQLException e) {
            System.out.println("Couldn't Connect to the Database: " + e.getMessage());
            return null;
        }
    }

    public static void createMembersTable() {
        String membersSQL = "CREATE TABLE IF NOT EXISTS members (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT," +
                "phone TEXT," +
                "membershipDate TEXT," +
                "issuanceNumber INTEGER," +
                "FOREIGN KEY (issuanceNumber) REFERENCES issuedBooks (issuanceNumber)" +
                ")";

        try(Connection conn = connection();
            Statement statement = conn.createStatement()) {
            statement.execute(membersSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createBooksTable() {
        String booksSQL = "CREATE TABLE IF NOT EXISTS books (" +
                "bookID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "Author TEXT," +
                "Genre TEXT," +
                "TotalBooks INTEGER," +
                "AvailableBooks INTEGER" +
                ")";

        try(Connection conn = connection();
            Statement statement = conn.createStatement()) {
            statement.execute(booksSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createIssuedBooksTable(){
        String issuedBooksSQL = "CREATE TABLE IF NOT EXISTS issuedBooks (" +
                "issuanceNumber INTEGER PRIMARY KEY AUTOINCREMENT," +
                "memberID INTEGER," +
                "memberName TEXT," +
                "bookID INTEGER," +
                "bookName TEXT," +
                "issueDate TEXT," +
                "returnDate TEXT," +
                "FOREIGN KEY (memberID) REFERENCES members(id)," +
                "FOREIGN KEY (memberName) REFERENCES members(name)" +
                ")";

        try(Connection conn = connection();
            Statement statement = conn.createStatement()) {
            statement.execute(issuedBooksSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
