package com.book.booksaleservice.common.dto;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.dto.BookDTO;
import com.book.booksaleservice.customer.domain.Customer;
import com.book.booksaleservice.order.dto.OrderDTO;

public class DtoConverter {

    private DtoConverter() {
    }

    public static BookDTO.Res convertBookToResDto(Book book) {
        return new BookDTO.Res(book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPlot(), book.getPrice(), 1);
    }

    public static Customer convertOrderDtoToCustomer(OrderDTO.Req orderDtoReq) {
        return new Customer(orderDtoReq.email(), orderDtoReq.address());
    }
}
