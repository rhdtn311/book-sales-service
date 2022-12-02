package com.book.booksaleservice.book.service;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.repository.BookRepository;
import com.book.booksaleservice.common.dto.DtoConverter;
import com.book.booksaleservice.common.exception.book.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
                .map(DtoConverter::convertBookToResDto)
                .toList();
    }

    public BookDTO.Res findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        return DtoConverter.convertBookToResDto(book);
    }

    public boolean existBook(Long id) {
        if (!bookRepository.existBook(id)) throw new BookNotFoundException();
        return true;
    }

    public List<BookDTO.Res> findByAllId(Set<Long> ids) {
        List<Book> books = bookRepository.findByAllId(ids);

        return entityToDtoList(books);
    }
}
