package com.automation.utils;

import com.automation.models.Category;
import com.automation.models.Order;
import com.automation.models.Pet;
import com.automation.models.Tag;
import com.automation.models.User;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.List;

/**
 * Builds random but valid payloads so tests don't depend on hardcoded data.
 */
public final class DataGenerator {

    private static final Faker FAKER = new Faker();

    private DataGenerator() {
    }

    public static Pet randomPet(long id, String status) {
        return Pet.builder()
                .id(id)
                .category(Category.builder().id(0L).name(FAKER.animal().name()).build())
                .name(FAKER.dog().name().replaceAll("\\s+", ""))
                .photoUrls(List.of("https://example.com/photo.jpg"))
                .tags(List.of(Tag.builder().id(0L).name(FAKER.lorem().word()).build()))
                .status(status)
                .build();
    }

    public static Pet randomPet(String status) {
        return randomPet(FAKER.random().nextLong(100_000L, 9_999_999L), status);
    }

    public static Order randomOrder(int orderId) {
        return Order.builder()
                .id(orderId)
                .petId(FAKER.random().nextInt(1, 10_000))
                .quantity(FAKER.random().nextInt(1, 5))
                .shipDate(Instant.now().toString())
                .status("placed")
                .complete(true)
                .build();
    }

    public static User randomUser() {
        return User.builder()
                .id(FAKER.random().nextInt(1, Integer.MAX_VALUE))
                .username(FAKER.credentials().username())
                .firstName(FAKER.name().firstName())
                .lastName(FAKER.name().lastName())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.credentials().password(10, 20))
                .phone(FAKER.phoneNumber().cellPhone())
                .userStatus(FAKER.random().nextInt(0, 2))
                .build();
    }
}
