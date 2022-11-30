package com.book.booksaleservice.customer.domain;

public class Address {
    private final String city;
    private final String gun;
    private final String street;
    private final String detail;

    public Address(String city, String gun, String street, String detail) {
        this.city = city;
        this.gun = gun;
        this.street = street;
        this.detail = detail;
    }
}
