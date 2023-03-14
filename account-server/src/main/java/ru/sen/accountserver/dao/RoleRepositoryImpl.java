package ru.sen.accountserver.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.sen.accountserver.entity.Role;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class RoleRepositoryImpl implements RolesRepository {

    //language=SQL
    private static final String SQL_GET_ROLES = "select * from roles where id = ?";

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Optional<Role>> roleRowMapper = (row, ignore) -> {
        Optional<String> name = Optional.ofNullable(row.getString("name"));
        return name.map(s -> Role.builder()
                .name(s)
                .build());
    };

    @Autowired
    public RoleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        List<Optional<Role>> result = jdbcTemplate.query(SQL_GET_ROLES, roleRowMapper, id);
        log.info("sending a request for a role by id: {}", id);
        return result.isEmpty() ? Optional.empty() : result.get(0);
    }
}