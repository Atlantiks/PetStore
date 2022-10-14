package org.example.http;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.example.entity.Pet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class PetRequests {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String SITE = "https://petstore.swagger.io/v2";
    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    void save(org.example.entity.Pet pet) {
        // not ready
    }

    void update(org.example.entity.Pet pet) {
        // not ready
    }

    public Optional<Pet> findPetById(Integer id) {
        ApiResponse response =  sendGETRequest(SITE + "/pet/" + id);

        return response.getCode() == 200 ? Optional.of(
                GSON.fromJson(response.getMessage(),Pet.class)) : Optional.empty();
    }

    void updatePetById(Integer id) {
        // not ready
    }

    void deletePetById(Integer id) {
        // not ready
    }

    void uploadImageForPetWithId(Integer id) {
        // not ready
    }

    void findPetByStatus(org.example.entity.Pet.PetStatus status) {

    }

    @SneakyThrows
    private ApiResponse sendGETRequest(String fullPath) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .GET()
                .header("accept", "application/json")
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return ApiResponse.builder()
                .code(httpResponse.statusCode())
                .message(httpResponse.body())
                .build();
    }
}
