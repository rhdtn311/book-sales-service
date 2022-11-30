package com.book.booksaleservice.order_book.domain;

public class OrderBook {
    private Long id;
    private Long orderId;
    private Long bookId;
    private final long price;
    private final int amount;

    public OrderBook(long price, int amount) {
        this.price = price;
        this.amount = amount;
    }
}
