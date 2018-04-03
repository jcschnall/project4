package com.example.usersapi.controllers;

import com.example.usersapi.models.User;
import com.example.usersapi.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository mockUserRepository;

	@Autowired
	private ObjectMapper jsonObjectMapper;

	private User newUser;
	private User updatedSecondUser;





	@Before
	public void setUp() {

		//create mock users

		User firstUser = new User(
			"someone",
			"Some",
			"One"
		);

		User secondUser = new User(
			"someOtherOne",
			"Someone",
			"Other"
		);


		//mock users list
		List<User> mockUsers =
			Stream.of(firstUser, secondUser).collect(Collectors.toList());

		//mock calls to repo
		given(mockUserRepository.findAll()).willReturn(mockUsers);
		given(mockUserRepository.findOne(1L)).willReturn(firstUser);
		given(mockUserRepository.findOne(4L)).willReturn(null);


		//mock to test update
		updatedSecondUser = new User(
				"updated_username",
				"Updated",
				"Info"
		);
		given(mockUserRepository.save(updatedSecondUser)).willReturn(updatedSecondUser);


		//mock to create new
		newUser = new User(
			"new_user_for_create",
			"New",
			"User"
		);
		given(mockUserRepository.save(newUser)).willReturn(newUser);




		// Mock out Delete of empty
		doAnswer(methodCall -> {
			throw new EmptyResultDataAccessException("ERROR MESSAGE FROM MOCK!!!", 1234);
		}).when(mockUserRepository).delete(4L);

	}





	//END TO END TESTS OF API

	// TEST findALL  user info and status
	@Test
	public void findAllUsers_success_StatusOK() throws Exception {

		this.mockMvc
			.perform(get("/users"))
			.andExpect(status().isOk());
	}


	@Test
	public void findAllUsers_success_returnUserName() throws Exception {

		this.mockMvc
			.perform(get("/users"))
			.andExpect(jsonPath("$[0].userName", is("someone")));
	}

	@Test
	public void findAllUsers_success_returnFirstName() throws Exception {

		this.mockMvc
			.perform(get("/users"))
			.andExpect(jsonPath("$[0].firstName", is("Some")));
	}

	@Test
	public void findAllUsers_success_returnLastName() throws Exception {

		this.mockMvc
			.perform(get("/users"))
			.andExpect(jsonPath("$[0].lastName", is("One")));
	}




	// Test findOne user info and status

	@Test
	public void findUserById_success_StatusOK() throws Exception {

		this.mockMvc
			.perform(get("/users/1"))
			.andExpect(status().isOk());
	}

	@Test
	public void findUserById_failure_userNotFound404() throws Exception {

		this.mockMvc
				.perform(get("/users/4"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void findUserById_success_UserName() throws Exception {

		this.mockMvc
			.perform(get("/users/1"))
			.andExpect(jsonPath("$.userName", is("someone")));
	}

	@Test
	public void findUserById_success_FirstName() throws Exception {

		this.mockMvc
			.perform(get("/users/1"))
			.andExpect(jsonPath("$.firstName", is("Some")));
	}

	@Test
	public void findUserById_success_LastName() throws Exception {

		this.mockMvc
			.perform(get("/users/1"))
			.andExpect(jsonPath("$.lastName", is("One")));
	}









	//Test delete user

	@Test
	public void deleteUserById_success_StatusOk() throws Exception {

		this.mockMvc
			.perform(delete("/users/1"))
			.andExpect(status().isOk());
	}

	@Test
	public void deleteUserById_failure_Returns404() throws Exception {

		this.mockMvc
				.perform(delete("/users/4"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteUserById_success_deletesViaRepository() throws Exception {

		this.mockMvc.perform(delete("/users/1"));

		verify(mockUserRepository, times(1)).delete(1L);
	}






	// Test create newUser with json object mapper

	@Test
	public void createUser_success_StatusOk() throws Exception {

		this.mockMvc
			.perform(
				post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(newUser))
			)
			.andExpect(status().isOk());
	}

	@Test
	public void createUser_success_returnsUserName() throws Exception {

		this.mockMvc
			.perform(
				post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(newUser))
			)
			.andExpect(jsonPath("$.userName", is("new_user_for_create")));
	}

	@Test
	public void createUser_success_returnsFirstName() throws Exception {

		this.mockMvc
			.perform(
				post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(newUser))
			)
			.andExpect(jsonPath("$.firstName", is("New")));
	}

	@Test
	public void createUser_success_returnsLastName() throws Exception {

		this.mockMvc
			.perform(
				post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(newUser))
			)
			.andExpect(jsonPath("$.lastName", is("User")));
	}





	//Test update user

	@Test
	public void updateUserById_success_StatusOk() throws Exception {

		this.mockMvc
			.perform(
				patch("/users/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(updatedSecondUser))
			)
			.andExpect(status().isOk());
	}

	@Test
	public void updateUserById_success_UpdatedUserName() throws Exception {

		this.mockMvc
			.perform(
				patch("/users/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(updatedSecondUser))
			)
			.andExpect(jsonPath("$.userName", is("updated_username")));
	}

	@Test
	public void updateUserById_success_UpdatedFirstName() throws Exception {

		this.mockMvc
			.perform(
				patch("/users/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(updatedSecondUser))
			)
			.andExpect(jsonPath("$.firstName", is("Updated")));
	}

	@Test
	public void updateUserById_success_returnsUpdatedLastName() throws Exception {

		this.mockMvc
			.perform(
				patch("/users/1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(updatedSecondUser))
			)
			.andExpect(jsonPath("$.lastName", is("Info")));
	}


	@Test
	public void updateUserById_failure_userNotFound() throws Exception {

		this.mockMvc
			.perform(
				patch("/users/4")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonObjectMapper.writeValueAsString(updatedSecondUser))
			)
			.andExpect(status().reason(containsString("not found!")));
	}


}
