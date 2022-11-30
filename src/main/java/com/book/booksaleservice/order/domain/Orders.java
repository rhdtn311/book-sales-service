package com.book.booksaleservice.order.domain;

import java.time.LocalDateTime;

public class Orders {
    private Long id;
    private Long customerId;
    private final LocalDateTime orderDate;
    private DeliveryStatus deliveryStatus;

    public Orders(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
