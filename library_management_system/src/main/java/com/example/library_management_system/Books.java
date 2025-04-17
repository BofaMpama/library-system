package com.example.library_management_system;

public class Books {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int totalCopies;
    private int availableCopies;

    public Books(int id, String title, String author, String genre, int totalCopies, int availableCopies){
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}

    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    public int getTotalCopies() {return totalCopies;}
    public void setTotalCopies(int totalCopies) {this.totalCopies = totalCopies;}

    public int getAvailableCopies() {return availableCopies;}
    public void setAvailableCopies(int availableCopies) {this.availableCopies = availableCopies;}
}
