package com.book.booksaleservice.book.service;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.repository.BookRepository;
import com.book.booksaleservice.common.dto.DtoConverter;
import com.book.booksaleservice.common.exception.book.BookNotFoundException;
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

        return DtoConverter.bookToResDtoList(books);
    }

    public BookDTO.Res findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        return DtoConverter.convertBookToResDto(book, 1);
    }

    public boolean existBook(Long id) {
        return bookRepository.existBook(id);
    }

    public List<BookDTO.Res> findByAllId(List<Long> ids) {
        List<Book> books = bookRepository.findByAllId(ids);

        return DtoConverter.bookToResDtoList(books);
    }

    public List<BookDTO.Res> findByAllIdAndChangeCount(List<BookDTO.Req> cart) {
        List<Book> books = bookRepository.findByAllId(DtoConverter.bookReqDTOsToIds(cart));
        return DtoConverter.bookToResDtoListWithCount(books, cart);
    }
}

