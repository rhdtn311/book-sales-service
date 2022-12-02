package com.book.booksaleservice.order.service;

import com.book.booksaleservice.common.dto.DtoConverter;
import com.book.booksaleservice.customer.domain.Customer;
import com.book.booksaleservice.customer.repository.CustomerRepository;
import com.book.booksaleservice.order.domain.DeliveryStatus;
import com.book.booksaleservice.order.domain.Order;
import com.book.booksaleservice.order.dto.OrderDTO;
import com.book.booksaleservice.order.repository.OrderRepository;
import com.book.booksaleservice.order_book.domain.OrderBook;
import com.book.booksaleservice.order_book.repository.OrderBookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderBookRepository orderBookRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderBookRepository orderBookRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderBookRepository = orderBookRepository;
    }

    public Long save(OrderDTO.Req orderDtoReq) {
        Customer customer = DtoConverter.convertOrderDtoToCustomer(orderDtoReq);
        Long customerId = customerRepository.save(customer);

        Order order = new Order(customerId, LocalDateTime.now(), DeliveryStatus.READY);
        Long orderId = orderRepository.save(order);

        orderDtoReq.books().forEach(book -> {
            orderBookRepository.save(new OrderBook(orderId, book.id(), book.amount()));
        });

        return order.getId();
    }
}
