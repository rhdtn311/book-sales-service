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
import java.util.Set;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final static String FIND_BY_CATEGORY_SQL = "SELECT * FROM BOOK b LEFT JOIN CATEGORY c ON b.category_id = c.id";
    private final static String FIND_BY_ID_SQL = "SELECT * FROM BOOK WHERE id = :id";
    private final static String EXIST_BY_ID_SQL = "SELECT COUNT(*) FROM BOOK WHERE id = :id LIMIT 1";
    private final static String FIND_BY_ALL_ID_SQL = "SELECT * FROM BOOK";
    private final static String UPDATE_AMOUNT_SQL = "UPDATE BOOK SET amount = :amount WHERE id = :id";

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

    @Override
    public boolean existBook(Long id) {
        return template.queryForObject(EXIST_BY_ID_SQL, Map.of("id", id), Integer.class) > 0;
    }

    @Override
    public List<Book> findByAllId(Set<Long> ids) {
        return template.query(FIND_BY_ALL_ID_SQL + getFindByAllIdSqlCondition(ids), bookRowMapper);
    }

    @Override
    public void updateAmount(Long id, int amount) {
        try {
            template.update(UPDATE_AMOUNT_SQL, Map.of("amount", amount, "id", id));
        } catch (DataAccessException e) {
            logger.error("[ERROR] Database error : {}", e.getMessage());
        }
    }

    private String getFindByAllIdSqlCondition(Set<Long> ids) {
        StringBuilder condition = new StringBuilder(" WHERE ");
        ids.forEach(id -> condition.append("id = ").append(id).append(" OR "));
        return condition.substring(0, condition.length() - 3);
    }
}
