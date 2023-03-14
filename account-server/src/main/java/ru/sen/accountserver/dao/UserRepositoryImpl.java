package ru.sen.accountserver.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sen.accountserver.entity.User;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    //language=SQL
    private static final String SQL_ADD_USER = "insert into users(first_name, last_name, " +
            "birthday, bio, country, city, phone, role_id) values (?, ?, ?, ?, ?, ?, ?, 1)";
    //language=SQL
    private static final String SQL_GET_USER = "select * from users where id = ?";
    //language=SQL
    private static final String SQL_CHANGE_USER = "update users set first_name = ?, last_name = ?," +
            "birthday = ?, bio = ?, country = ?, city = ?, phone = ? where id = ?";
    //language=SQL
    private static final String SQL_DELETE_USER = "delete from users where id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * mandatory filled in fields are firstName, lastName, birthday, roleId
     * fields can be empty bio, country, city, phone
     */
    private static final RowMapper<Optional<User>> userRowMapper = (row, ignore) -> {
        Optional<Long> id = Optional.of(row.getLong("id"));
        Optional<String> firstName = Optional.ofNullable(row.getString("first_name"));
        Optional<String> lastName = Optional.ofNullable(row.getString("last_name"));
        Optional<Date> birthday = Optional.ofNullable(row.getDate("birthday"));
        String bio = Optional.of(row.getString("bio")).orElse("");
        String country = Optional.of(row.getString("country")).orElse("");
        String city = Optional.ofNullable(row.getString("city")).orElse("");
        String phone = Optional.ofNullable(row.getString("phone")).orElse("");
        Optional<Long> roleId = Optional.of(row.getLong("role_id"));
        if (firstName.isPresent() && lastName.isPresent() && birthday.isPresent()) {
            return Optional.of(User.builder()
                    .id(id.get())
                    .firstName(firstName.get())
                    .lastName(lastName.get())
                    .birthday(birthday.get())
                    .bio(bio)
                    .country(country)
                    .city(city)
                    .phone(phone)
                    .roleId(roleId.get())
                    .build());
        } else {
            return Optional.empty();
        }
    };

    @Override
    public Long addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_ADD_USER);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, user.getBirthday());
            ps.setString(4, user.getBio());
            ps.setString(5, user.getCountry());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getPhone());
            return ps;
        }, keyHolder);
        Number id = keyHolder.getKey();
        log.info("sending a request to save user data and get his id: {}", id);
        return id != null ? id.longValue() : 0;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        List<Optional<User>> result = jdbcTemplate.query(SQL_GET_USER, userRowMapper, id);
        log.info("sending a request to get a user by his id: {}", id);
        return result.isEmpty() ? Optional.empty() : result.get(0);
    }

    @Override
    public boolean updateUser(User changUser) {
        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_CHANGE_USER);
            ps.setString(1, changUser.getFirstName());
            ps.setString(2, changUser.getLastName());
            ps.setDate(3, changUser.getBirthday());
            ps.setString(4, changUser.getBio());
            ps.setString(5, changUser.getCountry());
            ps.setString(6, changUser.getCity());
            ps.setString(7, changUser.getPhone());
            ps.setLong(8, changUser.getId());
            return ps;
        });
        log.info("sending a request to change the user by his id. User with changed data: {} \n" +
                "number of modified rows: {}", changUser, rowsAffected);
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteUserById(Long id) {
        int rowsAffected = jdbcTemplate.update(SQL_DELETE_USER, id);
        log.error("request to delete a user by his id. number of modified rows: {}", rowsAffected);
        return rowsAffected > 0;
    }
}
