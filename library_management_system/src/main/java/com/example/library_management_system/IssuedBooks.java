package com.example.library_management_system;

public class IssuedBooks {
    private int issuanceNumber;
    private int memberID;
    private String memberName;
    private int bookID;
    private String bookName;
    private String issueDate;
    private String returnDate;

    public IssuedBooks(int issuanceNumber, int memberID, String memberName, int bookID, String bookName, String issueDate, String returnDate){
        this.issuanceNumber = issuanceNumber;
        this.memberID = memberID;
        this.memberName = memberName;
        this.bookID = bookID;
        this.bookName = bookName;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public int getIssuanceNumber(){return issuanceNumber;}

    public void setIssuanceNumber(int issuanceNumber) {this.issuanceNumber = issuanceNumber;}

    public int getMemberID() {return memberID;}

    public void setMemberID(int memberID) {this.memberID = memberID;}

    public String getMemberName() {return memberName;}

    public void setMemberName(String memberName) {this.memberName = memberName;}

    public int getBookID() {return bookID;}

    public void setBookID(int bookID) {this.bookID = bookID;}

    public String getBookName() {return bookName;}

    public void setBookName(String bookName) {this.bookName = bookName;}

    public String getIssueDate() {return issueDate;}

    public void setIssueDate(String issueDate) {this.issueDate = issueDate;}

    public String getReturnDate() {return returnDate;}

    public void setReturnDate(String returnDate) {this.returnDate = returnDate;}
}

