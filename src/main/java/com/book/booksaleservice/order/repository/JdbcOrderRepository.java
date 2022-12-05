package com.book.booksaleservice.order.repository;

import com.book.booksaleservice.order.domain.DeliveryStatus;
import com.book.booksaleservice.order.domain.Order;
import com.book.booksaleservice.order.dto.OrderDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private static final String SAVE_ORDER_SQL = "INSERT INTO ORDERS(order_date, status, customer_id, price) VALUES (:orderDate, :deliveryStatus, :customerId, :price)";
    private static final String FIND_ORDERS_SQL = "SELECT o.id, c.email, c.address, o.order_date, o.status, b.title, o.price FROM ORDERS o JOIN CUSTOMER c ON o.customer_id = c.id JOIN ORDER_BOOK ob ON o.id = ob.order_id JOIN BOOK b ON b.id = ob.book_id ORDER BY o.id asc;";

    private final GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
    private final NamedParameterJdbcTemplate template;

    public JdbcOrderRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Long save(Order order) {
        template.update(SAVE_ORDER_SQL, getOrderParameterSource(order.getOrderDate(), order.getDeliveryStatus(), order.getCustomerId(), order.getPrice()), generatedKeyHolder);

        return generatedKeyHolder.getKey().longValue();
    }

    @Override
    public List<OrderDTO.OrderHistoryQueryRes> findOrderHistory() {
        ResultSetExtractor<List<OrderDTO.OrderHistoryQueryRes>> extractor = new ResultSetExtractor<>() {
            @Override
            public List<OrderDTO.OrderHistoryQueryRes> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                LinkedList<OrderDTO.OrderHistoryQueryRes> orderHistoryQueryResList = new LinkedList<>();
                while (resultSet.next()) {
                    if (isSameOrderId(resultSet, orderHistoryQueryResList)) {
                        orderHistoryQueryResList.getLast().titles().add(resultSet.getString("title"));
                    } else {
                        orderHistoryQueryResList.addLast(
                                new OrderDTO.OrderHistoryQueryRes(
                                        resultSet.getLong("id"),
                                        resultSet.getString("email"),
                                        resultSet.getString("address"),
                                        resultSet.getTimestamp("order_date").toLocalDateTime(),
                                        resultSet.getString("status"),
                                        resultSet.getLong("price"),
                                        new LinkedList<>(List.of(resultSet.getString("title")))
                                )
                        );
                    }
                }
                return orderHistoryQueryResList;
            }
        };

        return template.query(FIND_ORDERS_SQL, extractor);
    }

    private void makeOrderHistoryQueryResList(ResultSet resultSet, LinkedList<OrderDTO.OrderHistoryQueryRes> orderHistoryQueryResList) throws SQLException {
        if (isSameOrderId(resultSet, orderHistoryQueryResList)) {
            orderHistoryQueryResList.getLast().titles().add(resultSet.getString("title"));
        } else {
            orderHistoryQueryResList.addLast(
                    new OrderDTO.OrderHistoryQueryRes(
                            resultSet.getLong("id"),
                            resultSet.getString("email"),
                            resultSet.getString("address"),
                            resultSet.getTimestamp("order_date").toLocalDateTime(),
                            resultSet.getString("status"),
                            resultSet.getLong("price"),
                            new LinkedList<>(List.of(resultSet.getString("title")))
                    )
            );
        }
    }

    private boolean isSameOrderId(ResultSet resultSet, LinkedList<OrderDTO.OrderHistoryQueryRes> orderHistoryQueryResList) throws SQLException {
        return orderHistoryQueryResList.size() != 0 && orderHistoryQueryResList.peekLast().orderId() == resultSet.getLong("id");
    }

    private MapSqlParameterSource getOrderParameterSource(LocalDateTime orderDate, DeliveryStatus deliveryStatus, Long customerId, Long price) {
        return new MapSqlParameterSource()
                .addValue("orderDate", orderDate.truncatedTo(ChronoUnit.MINUTES))
                .addValue("deliveryStatus", deliveryStatus.name())
                .addValue("customerId", customerId)
                .addValue("price", price);
    }
}
