package com.book.booksaleservice.book.repository;

import com.book.booksaleservice.book.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findByCategory(String mainCategory, String subCategory);
}
