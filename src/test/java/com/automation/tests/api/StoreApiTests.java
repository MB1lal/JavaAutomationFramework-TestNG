package com.automation.tests.api;

import com.automation.api.StoreApiClient;
import com.automation.base.ApiBaseTest;
import com.automation.models.Order;
import com.automation.utils.DataGenerator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Same coverage as the original {@code pets-store.feature}:
 * place an order, fetch it back, delete it.
 */
@Test(groups = "api")
public class StoreApiTests extends ApiBaseTest {

    private StoreApiClient store;

    @BeforeClass(alwaysRun = true)
    public void initClient() {
        store = new StoreApiClient();
    }

    @Test(description = "Order is successfully placed on the pet store")
    public void orderIsSuccessfullyPlaced() {
        Order order = DataGenerator.randomOrder(400);

        store.placeOrder(order);
        Response response = store.getOrder(400);

        assertThat(response.as(Order.class).getId()).isEqualTo(400);
    }

    @Test(description = "Order can be deleted")
    public void orderCanBeDeleted() {
        store.placeOrder(DataGenerator.randomOrder(500));

        store.deleteOrder(500);

        store.assertOrderIsGone(500);
    }
}
