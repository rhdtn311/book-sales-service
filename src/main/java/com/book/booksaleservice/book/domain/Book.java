package com.book.booksaleservice.book.domain;

public class Book {
    private Long id;
    private final String title;
    private final long price;
    private final String publisher;
    private final String author;
    private final String plot;
    private final int amount;
    private final Long categoryId;

    public Book(Long id, String title, long price, String publisher, String author, String plot, int amount, Long categoryId) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.publisher = publisher;
        this.author = author;
        this.plot = plot;
        this.amount = amount;
        this.categoryId = categoryId;
    }

    public boolean isOverCount(int count) {
        return count > this.amount;
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {
        private Long id;
        private String title;
        private long price;
        private String publisher;
        private String author;
        private String plot;
        private int amount;
        private Long categoryId;

        BookBuilder() {}

        public BookBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder price(long price) {
            this.price = price;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder plot(String plot) {
            this.plot = plot;
            return this;
        }

        public BookBuilder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public BookBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Book build() {
            return new Book(this.id, this.title, this.price, this.publisher, this.author, this.plot, this.amount, this.categoryId);
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getPlot() {
        return plot;
    }

    public int getAmount() {
        return amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
