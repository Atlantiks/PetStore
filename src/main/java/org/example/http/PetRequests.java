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

    public Optional<Pet> save(Pet pet) {
        String petAsJson = GSON.toJson(pet);
        var apiResponse = sendPOSTRequest(SITE + "/pet", petAsJson, "application/json");

        return apiResponse.getCode() == 200 ? Optional.of(
                GSON.fromJson(apiResponse.getMessage(),Pet.class)) : Optional.empty();
    }

    public ApiResponse update(Long petId, String petName, String petStatus) {
        String requestBody = String.format("name=%s&status=%s",petName,petStatus);
        return sendPOSTRequest(
                SITE + "/pet/" + petId,
                requestBody,
                "application/x-www-form-urlencoded");
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

    public Optional<Pet[]> findPetByStatus(String status) {
        ApiResponse response =  sendGETRequest(SITE + "/pet/findByStatus?status=" + status);

        return response.getCode() == 200 ? Optional.of(
                GSON.fromJson(response.getMessage(),Pet[].class)) : Optional.empty();
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

    @SneakyThrows
    private ApiResponse sendPOSTRequest(String fullPath, String body, String contentType) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("accept", "application/json")
                .header("Content-Type", contentType)
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return ApiResponse.builder()
                .code(httpResponse.statusCode())
                .message(httpResponse.body())
                .build();
    }
}
