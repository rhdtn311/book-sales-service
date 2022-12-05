package com.book.booksaleservice.order.domain;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long customerId;
    private final LocalDateTime orderDate;
    private DeliveryStatus deliveryStatus;
    private long price;

    public Order(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Order(Long customerId, LocalDateTime orderDate, DeliveryStatus deliveryStatus, long price) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.deliveryStatus = deliveryStatus;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public long getPrice() {
        return price;
    }
}
