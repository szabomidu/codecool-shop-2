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

	UserDaoJdbcTest() throws SQLException {
	}

	@BeforeEach
	void setUp() throws SQLException {
		when(dataSource.getConnection()).thenReturn(connection);
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	public void add_setsIdForUser() throws SQLException {
		when(connection.prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);

		User user = new User("John");
		new UserDaoJdbc(dataSource).add(user);
		assertEquals(1, user.getId());
	}

	@Test
	public void addThenFind_ReturnsSameUserInstance() throws SQLException {
		when(connection.prepareStatement(any(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);

		User user = new User("John");
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc(dataSource);
		userDaoJdbc.add(user);

		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.getString(2)).thenReturn("John");

		User userRetrieved = userDaoJdbc.find(1);

		assertEquals(user, userRetrieved);
	}

}
