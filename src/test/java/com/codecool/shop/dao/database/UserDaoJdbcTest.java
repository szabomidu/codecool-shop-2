package com.codecool.shop.dao.database;

import com.codecool.shop.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.sql.DataSource;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoJdbcTest {

//	@InjectMocks
//	private UserDaoJdbc userDaoJdbc;
	@Mock
	private DataSource dataSource;
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement statement;
	@Mock
	private ResultSet resultSet;

	private User user;

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
		when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);

		user = new User("John");
		new UserDaoJdbc(dataSource).add(user);
		assertEquals(1, user.getId());
	}

	@Test
	public void add_whenCannotConnectToDatabase_ThrowsException() throws SQLException {
		when(dataSource.getConnection()).thenThrow(new SQLException());
		assertThrows(RuntimeException.class, () -> new UserDaoJdbc(dataSource).add(user));
	}

	@Test
	public void add_CallsDependencyMethodsForGivenNumberOfTimes() throws SQLException {
		when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);

		user = new User("John");
		new UserDaoJdbc(dataSource).add(user);

		verify(statement, times(1)).setString(1, "John");
		verify(statement, times(1)).executeUpdate();
		verify(statement, times(1)).getGeneratedKeys();
		verify(resultSet, times(1)).next();
		verify(resultSet, times(1)).getInt(1);

	}

	@Test
	public void addThenFind_ReturnsSameUserInstance() throws SQLException {
		when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);

		user = new User("John");
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc(dataSource);
		userDaoJdbc.add(user);

		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.getString(2)).thenReturn("John");

		User userRetrieved = userDaoJdbc.find(1);

		assertEquals(user, userRetrieved);
	}

	@Test
	public void find_CalledWithNonExistingId_ReturnsNull() throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		UserDaoJdbc userDaoJdbc = new UserDaoJdbc(dataSource);
		int nonExistingId = 1000;
		assertNull(userDaoJdbc.find(nonExistingId));

	}

	@Test
	public void find_whenCannotConnectToDatabase_ThrowsException() throws SQLException {
		when(dataSource.getConnection()).thenThrow(new SQLException());
		assertThrows(RuntimeException.class, () -> new UserDaoJdbc(dataSource).find(10));
	}

	@Test
	public void addAll_CallsAddForGivenNumberOfTimes() throws SQLException {
		when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(statement);
		when(statement.getGeneratedKeys()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt(1)).thenReturn(1);

		User[] users = new User[] {new User("A"),
										new User("B"),
										new User("C"),
										new User("D")};
		UserDaoJdbc userDaoJdbc = new UserDaoJdbc(dataSource);
		userDaoJdbc.addAll(users);
		verify(connection, times(users.length)).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));

	}

	@Test
	public void addAll_whenCannotConnectToDatabase_ThrowsException() throws SQLException {
		when(dataSource.getConnection()).thenThrow(new SQLException());

		User[] users = new User[] {new User("A"),
				new User("B"),
				new User("C"),
				new User("D")};

		assertThrows(RuntimeException.class, () -> new UserDaoJdbc(dataSource).addAll(users));
	}

	@Test
	public void remove_CallsDependencyMethodsForGivenNumberOfTimes() throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(statement);

		int id = 6;
		new UserDaoJdbc(dataSource).remove(id);

		verify(statement, times(1)).setInt(1, id);
		verify(statement, times(1)).executeUpdate();

	}

	@Test
	public void remove_whenCannotConnectToDatabase_ThrowsException() throws SQLException {
		when(dataSource.getConnection()).thenThrow(new SQLException());
		assertThrows(RuntimeException.class, () -> new UserDaoJdbc(dataSource).remove(10));
	}

	@Test
	public void getAll_ReturnsListOfUsers() throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		when(statement.executeQuery()).thenReturn(resultSet);

		String[] names = new String[] {"A", "B", "C", "D"};

		when(resultSet.next()).thenAnswer(new Answer<Boolean>() {
			private int iterations = 4;

			public Boolean answer(InvocationOnMock invocation) {
				return iterations-- > 0;
			}
		});

		when(resultSet.getInt(1)).thenAnswer(new Answer<Integer>() {
			private int index = 1;

			public Integer answer(InvocationOnMock invocation) {
				return index++;
			}
		});

		when(resultSet.getString(2)).thenAnswer(new Answer<String>() {
			private int index = 0;

			public String answer(InvocationOnMock invocation) {
				return names[index++];
			}
		});

		List<User> usersRetrieved = new UserDaoJdbc(dataSource).getAll();

		for (int i = 0; i < names.length; i++) {
			User user = new User(names[i]);
			user.setId(i + 1);
			assertEquals(user, usersRetrieved.get(i));
		}
	}

	@Test
	public void getAll_whenCannotConnectToDatabase_ThrowsException() throws SQLException {
		when(dataSource.getConnection()).thenThrow(new SQLException());
		assertThrows(RuntimeException.class, () -> new UserDaoJdbc(dataSource).getAll());
	}

}
