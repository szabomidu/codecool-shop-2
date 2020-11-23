package com.codecool.shop.dao.database;

import com.codecool.shop.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDaoJdbcTest {

	@Mock private DataSource dataSource;
	@Mock private Connection connection;
	@Mock private PreparedStatement statement;
	@Mock private ResultSet resultSet;

	private User user;

	UserDaoJdbcTest() throws SQLException {
	}

	@BeforeEach
	void setUp() throws SQLException {
		when(dataSource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	public void add_setsIdForUser() throws SQLException {
		user = new User("John");
		new UserDaoJdbc(dataSource).add(user);
		assertEquals(1, user.getId());
	}

}
