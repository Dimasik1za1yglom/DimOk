package ru.sen.accountserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.sen.accountserver.entity.AuthorizationData;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorizationDataRepositoryImpl implements AuthorizationDataRepository {

    //language=SQL
    private static final String SQL_ADD_DATA = "insert into authorization_data(email, password, user_id)" +
            " values (?, ?, ?)";
    //language=SQL
    private static final String SQL_GET_DATA = "select * from authorization_data where email = ?";
    //language=SQL
    private static final String SQL_DELETE_DATA = "delete from authorization_data where user_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorizationDataRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final RowMapper<Optional<AuthorizationData>> dataRowMapper = (row, ignore) -> {
        Optional<String> email = Optional.ofNullable(row.getString("email"));
        Optional<String> password = Optional.ofNullable(row.getString("password"));
        Optional<Long> userId = Optional.of(row.getLong("user_id"));
        if (email.isPresent() && password.isPresent()) {
            return Optional.of(AuthorizationData.builder()
                    .email(email.get())
                    .password(password.get())
                    .userId(userId.get())
                    .build());
        } else {
            return Optional.empty();
        }
    };

    @Override
    public boolean addAuthorizationData(AuthorizationData data) {
        int rowsAffected = jdbcTemplate.update(SQL_ADD_DATA, data.getEmail(), data.getPassword(), data.getUserId());
        return rowsAffected > 0;
    }

    @Override
    public Optional<AuthorizationData> getDataByLogin(String email) {
        List<Optional<AuthorizationData>> result = jdbcTemplate.query(SQL_GET_DATA, dataRowMapper, email);
        return result.isEmpty() ? Optional.empty() : result.get(0);
    }

    @Override
    public boolean deleteDataByUserId(Long userId) {
        int rowsAffected = jdbcTemplate.update(SQL_DELETE_DATA, userId);
        return rowsAffected > 0;
    }
}
