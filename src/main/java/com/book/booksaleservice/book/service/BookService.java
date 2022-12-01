package com.book.booksaleservice.book.service;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.repository.BookRepository;
import com.book.booksaleservice.common.dto.DtoConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO.Res> findByCategory(String mainCategory, String subCategory) {

        List<Book> books = bookRepository.findByCategory(mainCategory, subCategory);

        return entityToDtoList(books);
    }

    private List<BookDTO.Res> entityToDtoList(List<Book> books) {
        return books.stream()
                .map(DtoConverter::convertBookToResDTO)
                .toList();
    }

}
