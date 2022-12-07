package com.book.booksaleservice.common.dto;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.common.exception.book.BookNotFoundException;
import com.book.booksaleservice.customer.domain.Customer;
import com.book.booksaleservice.order.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {

    private DtoConverter() {
    }

    public static BookDTO.Res convertBookToResDto(Book book, int count) {
        return new BookDTO.Res(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPlot(), book.getPrice(), count);
    }

    public static Customer convertOrderDtoToCustomer(OrderDTO.Req orderDtoReq) {
        return new Customer(orderDtoReq.email(), orderDtoReq.address());
    }

    public static List<Long> bookReqDTOsToIds(List<BookDTO.Req> cart) {
        return cart.stream()
                .map(BookDTO.Req::id)
                .toList();
    }

    public static List<BookDTO.Res> bookToResDtoListWithCount(List<Book> books, List<BookDTO.Req> cart) {
        List<BookDTO.Res> resDtos = new ArrayList<>();
        for (BookDTO.Req req : cart) {
            Book findBook = books.stream()
                    .filter(book -> book.getId().equals(req.id()))
                    .findFirst()
                    .orElseThrow(BookNotFoundException::new);

            resDtos.add(convertBookToResDto(findBook, req.count()));
        }
        return resDtos;
    }

    public static List<BookDTO.Res> bookToResDtoList(List<Book> books) {
        return books.stream()
                .map(book -> DtoConverter.convertBookToResDto(book, 1))
                .toList();
    }
}
