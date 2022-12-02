package com.book.booksaleservice.order_book.repository;

import com.book.booksaleservice.order_book.domain.OrderBook;

public interface OrderBookRepository {
    void save(OrderBook orderBook);
}
