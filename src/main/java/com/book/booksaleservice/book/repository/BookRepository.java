package com.book.booksaleservice.book.repository;

import com.book.booksaleservice.book.domain.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository {
    List<Book> findByCategory(String mainCategory, String subCategory);

    Optional<Book> findById(Long id);

    boolean existBook(Long id);

    List<Book> findByAllId(Set<Long> ids);
}
