package com.automation.tests.api;

import com.automation.api.UserApiClient;
import com.automation.base.ApiBaseTest;
import com.automation.models.User;
import com.automation.utils.DataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code user_operations.feature}:
 * create a user, log in and out, delete the user.
 */
@Test(groups = "api")
public class UserApiTests extends ApiBaseTest {

    private UserApiClient users;

    @BeforeClass(alwaysRun = true)
    public void initClient() {
        users = new UserApiClient();
    }

    @Test(description = "User can log in and out")
    public void userCanLoginAndLogout() {
        User user = DataGenerator.randomUser();
        users.createUser(user);

        Response fetched = users.getUser(user.getUsername());
        assertThat(fetched.getStatusCode()).isEqualTo(200);

        Response login = users.login(user.getUsername(), user.getPassword());
        assertThat(login.getStatusCode()).isEqualTo(200);

        users.logout();
    }

    @Test(description = "User can be deleted")
    public void userCanBeDeleted() {
        User user = DataGenerator.randomUser();
        users.createUser(user);
        users.getUser(user.getUsername());

        Response deleted = users.deleteUser(user.getUsername());

        assertThat(deleted.getStatusCode()).isEqualTo(200);
    }
}
