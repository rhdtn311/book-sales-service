package com.book.booksaleservice.order_book.domain;

public class OrderBook {
    private Long id;
    private final Long orderId;
    private final Long bookId;
    private int amount;

    public OrderBook(Long orderId, Long bookId, int amount) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public int getAmount() {
        return amount;
    }
}
