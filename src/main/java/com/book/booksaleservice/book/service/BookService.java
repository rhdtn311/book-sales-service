package com.book.booksaleservice.book.service;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.book.repository.BookRepository;
import com.book.booksaleservice.common.dto.DtoConverter;
import com.book.booksaleservice.common.exception.book.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .map(book -> DtoConverter.convertBookToResDto(book, 1))
                .toList();
    }

    public BookDTO.Res findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        return DtoConverter.convertBookToResDto(book, 1);
    }

    public boolean existBook(Long id) {
        if (!bookRepository.existBook(id)) throw new BookNotFoundException();
        return true;
    }

    public List<BookDTO.Res> findByAllId(List<Long> ids) {
        List<Book> books = bookRepository.findByAllId(ids);

        return entityToDtoList(books);
    }

    public List<BookDTO.Res> findByAllIdAndChangeCount(List<BookDTO.Req> cart) {
        List<Book> books = bookRepository.findByAllId(bookReqDTOsToIds(cart));
        return entityToDtoListWithCount(books, cart);
    }

    private List<Long> bookReqDTOsToIds(List<BookDTO.Req> cart) {
        return cart.stream()
                .map(BookDTO.Req::id)
                .toList();
    }

    private List<BookDTO.Res> entityToDtoListWithCount(List<Book> books, List<BookDTO.Req> cart) {
        List<BookDTO.Res> resDtos = new ArrayList<>();
        for (BookDTO.Req req : cart) {
            Book findBook = books.stream()
                    .filter(book -> book.getId().equals(req.id()))
                    .findFirst()
                    .get();

            resDtos.add(DtoConverter.convertBookToResDto(findBook, req.count()));
        }
        return resDtos;
    }
}

