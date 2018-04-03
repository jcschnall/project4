package com.example.springbootmicroservicewrapperwithtests.features;

import com.example.springbootmicroservicewrapperwithtests.models.User;
import com.example.springbootmicroservicewrapperwithtests.repositories.UserRepository;
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
	private UserRepository repo;

	@Before
	public void setUp() {
		repo.deleteAll();
	}

	@After
	public void tearDown() {
		repo.deleteAll();
	}

	@Test
	public void CrudTestForALL() throws Exception {

		User firstUser = new User(
			"someone",
			"Some",
			"One"
		);


		firstUser = repo.save(firstUser);
		Long firstUserId = firstUser.getId();



		System.setProperty("selenide.browser", "Chrome");
		System.setProperty("selenide.headless", "true");




		//test open directly to LogIn
		open("http://localhost:3000/logIn");
		$("#new-user-form").should(appear);


		//load list page
		open("http://localhost:3000/list");
		$$("[data-user-display]").shouldHave(size(1));  //seeded data deleted


		$("#user-" + firstUserId + "-user-name").shouldHave(text("someone"));
		$("#user-" + firstUserId + "-first-name").shouldHave(text("Some"));
		$("#user-" + firstUserId + "-last-name").shouldHave(text("One"));



		//test navigate
		$("#new-user-link").click();
		$("#new-user-form").should(appear);


		//test add user
		$("#new-user-user-name").sendKeys("second_user");
		$("#new-user-first-name").sendKeys("Second");
		$("#new-user-last-name").sendKeys("User");
		$("#new-user-submit").click();


		//test size
		$("#users-wrapper").should(appear);
		$$("[data-user-display]").shouldHave(size(2));


		//test refresh
		refresh();
		$$("[data-user-display]").shouldHave(size(2));

		//test accurate creation
		Long secondUserId = firstUserId + 1;
		$("#user-" + secondUserId + "-user-name").shouldHave(text("second_user"));
		$("#user-" + secondUserId + "-first-name").shouldHave(text("Second"));
		$("#user-" + secondUserId + "-last-name").shouldHave(text("User"));


		//test delete
		$("#user-" + firstUserId).should(exist);
		$("#delete-user-" + firstUserId).click();
		$("#user-" + firstUserId).shouldNot(exist);
		$$("[data-user-display]").shouldHave(size(1));


		//test open directly to LogIn
		open("http://localhost:3000/LogIn");
		$("#new-user-form").should(appear);


	}

}