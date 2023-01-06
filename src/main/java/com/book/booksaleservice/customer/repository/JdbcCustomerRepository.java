package com.book.booksaleservice.customer.repository;

import com.book.booksaleservice.customer.domain.Customer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private static final String CUSTOMER_SAVE_SQL = "INSERT INTO CUSTOMER(email, address) VALUES (:email, :address)";

    public Long save(Customer customer) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        try {
            template.update(CUSTOMER_SAVE_SQL, getCustomerParameterSource(customer.getEmail(), customer.getAddress()), generatedKeyHolder);
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
        }

        return (long) generatedKeyHolder.getKey().intValue();
    }

    private MapSqlParameterSource getCustomerParameterSource(String email, String address) {
        return new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("address", address);
    }
}
