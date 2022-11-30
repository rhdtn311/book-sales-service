package com.book.booksaleservice.customer.domain;

public class Customer {
    private Long id;
    private final String email;
    private final Address address;

    public Customer(String email, Address address) {
        this.email = email;
        this.address = address;
    }
}
