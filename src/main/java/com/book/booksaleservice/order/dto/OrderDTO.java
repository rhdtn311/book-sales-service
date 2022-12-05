package com.book.booksaleservice.order.dto;

import com.book.booksaleservice.book.dto.BookDTO;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    public record Res(List<BookDTO.Res> books, long price) {
        public static long totalPrice(List<BookDTO.Res> books) {
            return books.stream()
                    .map(BookDTO.Res::price)
                    .reduce(0L, Long::sum);
        }
    }

    public record Req(String email, String address, List<BookDTO.Req> books, long totalPrice) {
    }

    public record OrderHistoryQueryRes(Long orderId, String email, String address, LocalDateTime orderDate,
                                       String status,
                                       long price, List<String> titles) {
    }
}
