package com.example.springbootmicroservicewrapperwithtests.features;

import com.example.springbootmicroservicewrapperwithtests.models.User;
import com.example.springbootmicroservicewrapperwithtests.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsersUIFeatureTest {

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setUp() {
		userRepository.deleteAll();
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void shouldAllowFullCrudFunctionalityForAUser() throws Exception {

		User firstUser = new User(
			"someone",
			"Some",
			"One"
		);
		firstUser = userRepository.save(firstUser);
		Long firstUserId = firstUser.getId();

		User secondUser = new User(
			"someone_else",
			"Someone",
			"Else"
		);
		secondUser = userRepository.save(secondUser);
		Long secondUserId = secondUser.getId();

		System.setProperty("selenide.browser", "Chrome");
		System.setProperty("selenide.headless", "true");

		// Visit the UI in headless browser
		open("http://localhost:3000");

		// check for two users
		$$("[data-user-display]").shouldHave(size(2));

		// check data per user
		$("#user-" + firstUserId + "-user-name").shouldHave(text("someone"));
		$("#user-" + firstUserId + "-first-name").shouldHave(text("Some"));
		$("#user-" + firstUserId + "-last-name").shouldHave(text("One"));

		$("#user-" + secondUserId + "-user-name").shouldHave(text("someone_else"));
		$("#user-" + secondUserId + "-first-name").shouldHave(text("Someone"));
		$("#user-" + secondUserId + "-last-name").shouldHave(text("Else"));

		//visit page
		$("#new-user-link").click();

		// form appear
		$("#new-user-form").should(appear);

		// Add user
		$("#new-user-user-name").sendKeys("third_user");
		$("#new-user-first-name").sendKeys("Third");
		$("#new-user-last-name").sendKeys("User");
		$("#new-user-submit").click();

		// go back
		$("#users-wrapper").should(appear);

		// incremented user size
		$$("[data-user-display]").shouldHave(size(3));

		refresh();

		
		$$("[data-user-display]").shouldHave(size(3));

		// data for user three
		Long thirdUserId = secondUserId + 1;
		$("#user-" + thirdUserId + "-user-name").shouldHave(text("third_user"));
		$("#user-" + thirdUserId + "-first-name").shouldHave(text("Third"));
		$("#user-" + thirdUserId + "-last-name").shouldHave(text("User"));

		// Deleting 
		$("#user-" + firstUserId).should(exist);
		$$("[data-user-display]").shouldHave(size(3));

		$("#delete-user-" + firstUserId).click();
		$("#user-" + firstUserId).shouldNot(exist);

		$$("[data-user-display]").shouldHave(size(2));

	}

}