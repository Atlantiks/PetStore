package org.example.http;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PetRequests {
    static final HttpClient CLIENT = HttpClient.newHttpClient();
    static final String SITE = "https://petstore.swagger.io/v2";

    void save(org.example.entity.Pet pet) {
        // not ready
    }

    void update(org.example.entity.Pet pet) {
        // not ready
    }

    void findPetById(Integer id) {
        // not ready
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
    private String sendGETRequest(String fullPath) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .GET()
                .header("accept", "application/json")
                .build();
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
