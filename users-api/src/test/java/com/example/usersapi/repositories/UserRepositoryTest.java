package com.example.usersapi.repositories;

import com.example.usersapi.models.User;
import com.example.usersapi.repositories.UserRepository;
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
			"email",
			"first_name",
			"last_name"
		);

		entityManager.persist(firstUser);
		entityManager.flush();
	}



	//below tests will only work if Id is correct, meaning database cleared out and rebooted

	/*
	@Test
	public void findOne_returnsUserName(){
		User foundUser = userRepository.findOne(2);
		String UsersUserName = foundUser.getUserName();

		assertThat(UsersUserName, is("email"));
	}


	@Test
	public void findOne_returnsFirstName(){
		User foundUser = userRepository.findOne(2);
		String UsersUserName = foundUser.getFirstName();

		assertThat(UsersUserName, is("first_name"));
	}


	@Test
	public void findOne_returnsLastName()	{
		User foundUser = userRepository.findOne(2);
		String UsersUserName = foundUser.getLastName();

		assertThat(UsersUserName, is("last_name"));
		}

	}
	*/




	@Test
	public void findAll_returnsAllUsers() {
		List<User> usersFromDb = userRepository.findAll();

		assertThat(usersFromDb.size(), is(2));    //inputed user plus seed in database migrations
	}

	@Test
	public void findAll_returnsUserName() {
		List<User> usersFromDb = userRepository.findAll();
		String UsersUserName = usersFromDb.get(1).getUserName();

		assertThat(UsersUserName, is("email"));
	}

	@Test
	public void findAll_returnsFirstName() {
		List<User> usersFromDb = userRepository.findAll();
		String UsersFirstName = usersFromDb.get(1).getFirstName();

		assertThat(UsersFirstName, is("first_name"));
	}

	@Test
	public void findAll_returnsLastName() {
		List<User> usersFromDb = userRepository.findAll();
		String UsersLastName = usersFromDb.get(1).getLastName();

		assertThat(UsersLastName, is("last_name"));
	}





}
