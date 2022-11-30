package com.book.booksaleservice.book.domain;

public class Book {
    private Long id;
    private final String title;
    private final long price;
    private final String publisher;
    private final String author;
    private final int amount;
    private Long categoryId;

    public Book(String title, long price, String publisher, String author, int amount) {
        this.title = title;
        this.price = price;
        this.publisher = publisher;
        this.author = author;
        this.amount = amount;
    }
}
