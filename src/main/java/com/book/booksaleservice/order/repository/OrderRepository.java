package com.book.booksaleservice.order.repository;

import com.book.booksaleservice.order.domain.Order;
import com.book.booksaleservice.order.dto.OrderDTO;

import java.util.List;

public interface OrderRepository {
    Long save(Order order);

    List<OrderDTO.OrderHistoryQueryRes> findOrderHistory();
}
