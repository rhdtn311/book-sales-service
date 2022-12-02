package com.book.booksaleservice.order_book.repository;

import com.book.booksaleservice.order_book.domain.OrderBook;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class JdbcOrderBookRepository implements OrderBookRepository {

    private static final String SAVE_ORDER_BOOK_SQL = "INSERT INTO ORDER_BOOK(amount, order_id, book_id) VALUES(:amount, :order_id, :book_id)";

    private final NamedParameterJdbcTemplate template;

    public JdbcOrderBookRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(OrderBook orderBook) {
        template.update(SAVE_ORDER_BOOK_SQL, Map.of("amount", orderBook.getAmount(), "order_id", orderBook.getOrderId(), "book_id", orderBook.getBookId()));
    }
}
