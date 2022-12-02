package com.book.booksaleservice.order.repository;

import com.book.booksaleservice.order.domain.DeliveryStatus;
import com.book.booksaleservice.order.domain.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private static final String SAVE_ORDER_SQL = "INSERT INTO ORDERS(order_date, status, customer_id) VALUES (:orderDate, :deliveryStatus, :customerId)";

    private final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    private final NamedParameterJdbcTemplate template;

    public JdbcOrderRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Long save(Order order) {
        template.update(SAVE_ORDER_SQL, getOrderParameterSource(order.getOrderDate(), order.getDeliveryStatus(), order.getCustomerId()), generatedKeyHolder);

        return generatedKeyHolder.getKey().longValue();
    }

    private MapSqlParameterSource getOrderParameterSource(LocalDateTime orderDate, DeliveryStatus deliveryStatus, Long customerId) {
        return new MapSqlParameterSource()
                .addValue("orderDate", orderDate.truncatedTo(ChronoUnit.MINUTES))
                .addValue("deliveryStatus", deliveryStatus.name())
                .addValue("customerId", customerId);
    }
}
