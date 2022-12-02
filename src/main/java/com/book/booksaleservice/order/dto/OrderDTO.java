package com.book.booksaleservice.order.dto;

import com.book.booksaleservice.book.dto.BookDTO;

import java.util.List;

public class OrderDTO {
    public record Res(List<BookDTO.Res> books, long price) {
        public static long totalPrice(List<BookDTO.Res> books) {
            return books.stream()
                    .map(BookDTO.Res::price)
                    .reduce(0L, Long::sum);
        }
    }
}
