package com.example.usersapi.repositories;

import com.example.usersapi.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setUp() {
		User firstUser = new User(
			"user_name",
			"first name",
			"last name"
		);

		entityManager.persist(firstUser);
		entityManager.flush();
	}

	@Test
	public void findAll_returnsAllUsers() {
		List<User> usersFromDb = userRepository.findAll();

		assertThat(usersFromDb.size(), is(1));
	}

	@Test
	public void findAll_returnsUserName() {
		List<User> usersFromDb = userRepository.findAll();
		String UsersUserName = usersFromDb.get(0).getUserName();

		assertThat(UsersUserName, is("user_name"));
	}

	@Test
	public void findAll_returnsFirstName() {
		List<User> usersFromDb = userRepository.findAll();
		String UsersFirstName = usersFromDb.get(0).getFirstName();

		assertThat(UsersFirstName, is("first name"));
	}

	@Test
	public void findAll_returnsLastName() {
		List<User> usersFromDb = userRepository.findAll();
		String UsersLastName = usersFromDb.get(0).getLastName();

		assertThat(UsersLastName, is("last name"));
	}

}
