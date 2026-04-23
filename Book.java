package com.example.libraryapp;

public class Book {
    public int id;
    public String studentName;
    public String bookTitle;
    public String authorName;
    public String issueDate;
    public String returnDate;
    public String status;

    public Book(int id, String studentName, String bookTitle, String authorName,
                String issueDate, String returnDate, String status) {
        this.id = id;
        this.studentName = studentName;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.status = status;
    }
}

