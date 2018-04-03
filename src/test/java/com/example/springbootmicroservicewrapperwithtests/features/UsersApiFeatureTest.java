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
import java.util.stream.Stream;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsersApiFeatureTest {

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
    public void CrudForALLUsers() throws Exception {

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

        Stream.of(firstUser, secondUser)
                .forEach(user -> {
                    userRepository.save(user);
                });

        when()
                .get("http://localhost:8081/users")
                .then()
                .statusCode(is(200))
                .and()
                .body(containsString("someone"))
                .and()
                .body(containsString("someOtherOne"));

        when()
                .get("http://localhost:8081/xxxxxx/")
                .then()
                .statusCode(is(404));

        when()
                .get("http://localhost:8081/users/x")
                .then()
                .statusCode(is(400));


        when()
                .get("http://localhost:8081/users/" + firstUser.getId())
                .then()
                .statusCode(is(200))
                .body(containsString("someone"))
                .body(containsString("Some"))
                .body(containsString("One"));

        when()
                .delete("http://localhost:8081/users/" + firstUser.getId())
                .then()
                .statusCode(is(200));

        when()
                .get("http://localhost:8081/users/" + secondUser.getId())
                .then()
                .statusCode(is(200))
                .body(containsString("someOtherOne"))
                .body(containsString("Someone"))
                .body(containsString("Other"));

        when()
                .delete("http://localhost:8081/users/" + secondUser.getId())
                .then()
                .statusCode(is(200));
    }





}