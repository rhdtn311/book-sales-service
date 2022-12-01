package com.book.booksaleservice.book.dto;

public class BookDTO {
    public record Res(String title, String author, String publisher, long price) {
    }
}
