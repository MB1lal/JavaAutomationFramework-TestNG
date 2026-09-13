package com.automation.tests.api;

import com.automation.api.PetApiClient;
import com.automation.base.ApiBaseTest;
import com.automation.config.ConfigReader;
import com.automation.models.Pet;
import com.automation.utils.DataGenerator;
import com.automation.utils.JsonUtils;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the same ground as the original {@code pets-test.feature}:
 * add a pet, find it by id and status, update it, delete it.
 */
@Test(groups = "api")
public class PetApiTests extends ApiBaseTest {

    private PetApiClient pets;

    @BeforeClass(alwaysRun = true)
    public void initClient() {
        pets = new PetApiClient();
    }

    private Pet petFromFile(long id, String status) {
        String root = ConfigReader.getInstance().get("pet.file.bodies.root");
        Pet pet = JsonUtils.fromFile(root + "new-pet.json", Pet.class);
        pet.setId(id);
        pet.setStatus(status);
        return pet;
    }

    @Test(description = "Newly added pet can be found by id")
    public void newlyAddedPetIsFoundById() {
        pets.addPet(petFromFile(10001L, "available"));

        Response response = pets.getPetById(10001L);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.as(Pet.class).getId()).isEqualTo(10001L);
    }

    @Test(description = "Newly added pet can be found by status")
    public void newlyAddedPetIsFoundByStatus() {
        Pet pet = DataGenerator.randomPet("sold");
        pets.addPet(pet);

        Response response = pets.getPetsByStatus(List.of("sold"));

        assertThat(response.getStatusCode()).isEqualTo(200);
        Pet[] found = response.as(Pet[].class);
        assertThat(Arrays.stream(found).map(Pet::getId)).contains(pet.getId());
        Pet match = Arrays.stream(found).filter(p -> p.getId() == pet.getId()).findFirst().orElseThrow();
        assertThat(match.getStatus()).isEqualTo("sold");
    }

    @Test(description = "Newly added pet can be deleted")
    public void newlyAddedPetCanBeDeleted() {
        pets.addPet(petFromFile(10002L, "available"));

        pets.deletePet(10002L);

        assertThat(pets.getPetById(10002L).getStatusCode()).isEqualTo(404);
    }

    @Test(description = "Pet name and status can be updated")
    public void petDetailsAreUpdated() {
        pets.addPet(petFromFile(10001L, "available"));

        pets.updatePetWithForm(10001L, "name", "Unicorn");
        pets.updatePetWithForm(10001L, "status", "sold");

        Response response = pets.getPetById(10001L);
        assertThat(response.getStatusCode()).isEqualTo(200);
        Pet updated = response.as(Pet.class);
        assertThat(updated.getId()).isEqualTo(10001L);
        assertThat(updated.getStatus()).isEqualTo("sold");
    }
}
