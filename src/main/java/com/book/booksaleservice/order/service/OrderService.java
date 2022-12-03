package com.book.booksaleservice.order.service;

import com.book.booksaleservice.book.domain.Book;
import com.book.booksaleservice.book.repository.BookRepository;
import com.book.booksaleservice.common.dto.DtoConverter;
import com.book.booksaleservice.common.exception.book.BookNotFoundException;
import com.book.booksaleservice.common.exception.book.NotEnoughBookAmountException;
import com.book.booksaleservice.customer.domain.Customer;
import com.book.booksaleservice.customer.repository.CustomerRepository;
import com.book.booksaleservice.order.domain.DeliveryStatus;
import com.book.booksaleservice.order.domain.Order;
import com.book.booksaleservice.order.dto.OrderDTO;
import com.book.booksaleservice.order.repository.OrderRepository;
import com.book.booksaleservice.order_book.domain.OrderBook;
import com.book.booksaleservice.order_book.repository.OrderBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderBookRepository orderBookRepository;
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderBookRepository orderBookRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderBookRepository = orderBookRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Long save(OrderDTO.Req orderDtoReq) {
        Customer customer = DtoConverter.convertOrderDtoToCustomer(orderDtoReq);
        Long customerId = customerRepository.save(customer);

        Order order = new Order(customerId, LocalDateTime.now(), DeliveryStatus.READY);
        Long orderId = orderRepository.save(order);

        orderDtoReq.books().forEach(book -> {
            Book findBook = bookRepository.findById(book.id()).orElseThrow(BookNotFoundException::new);

            if (checkBookAmount(findBook, book.count())) {
                bookRepository.updateAmount(findBook.getId(), findBook.getAmount() - book.count());
                orderBookRepository.save(new OrderBook(orderId, book.id(), book.count()));
            }
        });

        return orderId;
    }

    private boolean checkBookAmount(Book book, int count) {
        if (book.isOverCount(count)) {
            throw new NotEnoughBookAmountException();
        }
        return true;
    }
}