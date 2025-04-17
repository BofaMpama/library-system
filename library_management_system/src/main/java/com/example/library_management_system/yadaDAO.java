package com.example.library_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class yadaDAO {
    public static void insertMembers(String name, String email, String phone, String membershipDate) {
        String sql = "INSERT INTO members (name, email, phone, membershipDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnect.connection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, membershipDate);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();

        }
    }

    public static ObservableList<Members> getMembers(){
        ObservableList<Members> membersList = FXCollections.observableArrayList();
        String sql =  "SELECT id, Name, Email, Phone, MembershipDate, IssuanceNumber FROM members";

        try(Connection connection = dbConnect.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()){
                 membersList.add(new Members(
                         rs.getInt("id"),
                         rs.getString("name"),
                         rs.getString("email"),
                         rs.getString("phone"),
                         rs.getString("membershipDate"),
                         rs.getInt("issuanceNumber")));
             }


        } catch (SQLException e){
            e.printStackTrace();
        }
        return membersList;
    }


    public static void insertBooks(String Title, String Author, String Genre, int TotalBooks, int AvailableBooks) throws SQLException {
        String sql = "INSERT INTO books (Title, Author, Genre, TotalBooks, AvailableBooks) VALUES(?, ?, ?, ?, ?)";
//        String sql2 = "UPDATE books SET TotalBooks = T"

        try(Connection connection = dbConnect.connection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, Title);
            preparedStatement.setString(2, Author);
            preparedStatement.setString(3, Genre);
            preparedStatement.setString(4, String.valueOf(TotalBooks));
            preparedStatement.setString(5, String.valueOf(AvailableBooks));
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ObservableList<Books> getBooks(){
        ObservableList<Books> booksList = FXCollections.observableArrayList();
        String sql =  "SELECT * FROM books";

        try(Connection connection = dbConnect.connection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()){
                booksList.add(new Books(
                        rs.getInt("bookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Genre"),
                        rs.getInt("TotalBooks"),
                        rs.getInt("AvailableBooks")));
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
        return booksList;
    }

    public static void insertIssuedBooks(int memberID, String memberName, int bookID, String bookName, String issueDate, String returnDate) throws SQLException {
        String sql = "INSERT INTO issuedBooks (memberID, memberName, bookID, bookName, issueDate, returnDate) VALUES(?, ?, ?, ?, ?, ?)";

        try(Connection connection = dbConnect.connection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);){
            preparedStatement1.setString(1, String.valueOf(memberID));
            preparedStatement1.setString(2, memberName);
            preparedStatement1.setString(3, String.valueOf(bookID));
            preparedStatement1.setString(4, bookName);
            preparedStatement1.setString(5, issueDate);
            preparedStatement1.setString(6, returnDate);
            preparedStatement1.executeUpdate();
        } catch (SQLException e){e.printStackTrace();}
    }

    public static ObservableList<IssuedBooks> getIssuedBooks() throws SQLException {
        ObservableList<IssuedBooks> issuedBookList = FXCollections.observableArrayList();
        String sql1 = "SELECT * FROM issuedBooks";
//        String sql2 = "SELECT memberName FROM members WHERE memberID =?";
//        String sql3 = "SELECT bookName FROM books WHERE bookID =?";

        try(Connection connection = dbConnect.connection();
            Statement statement = connection.createStatement();
            ResultSet rs1 = statement.executeQuery(sql1)){

            while (rs1.next()){
                issuedBookList.add(new IssuedBooks(
                        rs1.getInt("issuanceNumber"),
                        rs1.getInt("memberID"),
                        rs1.getString("memberName"),
                        rs1.getInt("bookID"),
                        rs1.getString("bookName"),
                        rs1.getString("issueDate"),
                        rs1.getString("returnDate")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return issuedBookList;
    }

}

