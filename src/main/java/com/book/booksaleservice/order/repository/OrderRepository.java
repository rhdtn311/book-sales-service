package com.book.booksaleservice.order.repository;

import com.book.booksaleservice.order.domain.Order;

public interface OrderRepository {
    Long save(Order order);
}
