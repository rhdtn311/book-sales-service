package com.book.booksaleservice.book.repository;

import com.book.booksaleservice.book.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final static String FIND_BY_CATEGORY_SQL = "SELECT * FROM BOOK b LEFT JOIN CATEGORY c ON b.category_id = c.id";
    private final static String FIND_BY_ID_SQL = "SELECT * FROM BOOK WHERE id = :id";

    private final static Logger logger = LoggerFactory.getLogger(JdbcBookRepository.class);

    private final NamedParameterJdbcTemplate template;

    private final RowMapper<Book> bookRowMapper = (resultSet, i) -> {
        return Book.builder()
                .id(resultSet.getLong("id"))
                .title(resultSet.getString("title"))
                .price(resultSet.getLong("price"))
                .author(resultSet.getString("author"))
                .plot(resultSet.getString("plot"))
                .publisher(resultSet.getString("publisher"))
                .amount(resultSet.getInt("amount"))
                .categoryId(resultSet.getLong("category_id"))
                .build();
    };

    public JdbcBookRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<Book> findByCategory(String mainCategory, String subCategory) {
        return template.query(FIND_BY_CATEGORY_SQL + getFindByCategorySqlCondition(mainCategory, subCategory),
                Map.of("mainCategory", mainCategory, "subCategory", subCategory), bookRowMapper);
    }

    private String getFindByCategorySqlCondition(String mainCategory, String subCategory) {
        StringBuilder condition = new StringBuilder();

        if (!mainCategory.equals("ALL") || !subCategory.equals("ALL")) {
            condition.append(" WHERE");
            if (!mainCategory.equals("ALL")) {
                condition.append(" c.main_category = :mainCategory");
                if (!subCategory.equals("ALL")) {
                    condition.append(" AND c.sub_category = :subCategory");
                }
            } else {
                condition.append(" c.sub_category = :subCategory");
            }
        }

        return condition.toString();
    }

    @Override
    public Optional<Book> findById(Long id) {
        try {
            return Optional.ofNullable(template.queryForObject(FIND_BY_ID_SQL, Map.of("id", id), bookRowMapper));
        } catch (DataAccessException e) {
            logger.error("[ERROR] Database error : {}", e.getMessage());
        }
        return Optional.empty();
    }

}
