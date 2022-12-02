package com.book.booksaleservice.book.dto;

public class BookDTO {
    public record Res(Long id, String title, String author, String publisher, String plot, long price) {
    }
}
