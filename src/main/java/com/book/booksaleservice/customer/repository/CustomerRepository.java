package com.book.booksaleservice.customer.repository;

import com.book.booksaleservice.customer.domain.Customer;

public interface CustomerRepository {
    Long save(Customer customer);
}
