package com.book.booksaleservice.common.dto;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;

public class DtoConverter {

    private DtoConverter() {}
    
    public static BookDTO.Res convertBookToResDTO(Book book) {
        return new BookDTO.Res(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPlot(), book.getPrice());
    }
}
