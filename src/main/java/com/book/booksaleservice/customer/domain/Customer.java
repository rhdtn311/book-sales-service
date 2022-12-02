package com.book.booksaleservice.customer.domain;

public class Customer {
    private Long id;
    private final String email;
    private final String address;

    public Customer(String email, String address) {
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
