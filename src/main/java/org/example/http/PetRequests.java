package org.example.http;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.example.entity.Pet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PetRequests {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String SITE = "https://petstore.swagger.io/v2";
    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    public Optional<Pet> save(Pet pet) {
        String petAsJson = GSON.toJson(pet);
        var apiResponse = sendPOSTRequest(SITE + "/pet", petAsJson, "application/json");

        return apiResponse.getCode() == 200 ? Optional.of(
                GSON.fromJson(apiResponse.getMessage(), Pet.class)) : Optional.empty();
    }

    public ApiResponse update(Pet pet) {
        String petAsJson = GSON.toJson(pet);
        return sendPUTRequest(SITE + "/pet", petAsJson, "application/json");
    }

    public ApiResponse update(Long petId, String petName, String petStatus) {
        String requestBody = String.format("name=%s&status=%s", petName, petStatus);
        return sendPOSTRequest(
                SITE + "/pet/" + petId,
                requestBody,
                "application/x-www-form-urlencoded");
    }

    public Optional<Pet> findPetById(Long id) {
        ApiResponse response = sendGETRequest(SITE + "/pet/" + id);

        return response.getCode() == 200 ? Optional.of(
                GSON.fromJson(response.getMessage(), Pet.class)) : Optional.empty();
    }

    public ApiResponse deletePetById(Long id) {
        return sendDELETERequest(SITE + "/pet/" + id, "1");
    }

    public ApiResponse uploadImageForPetWithId(Long id, String metaData) {
        String boundary = "-------------oiawn4tp89n4e9p5";
        String filePath = "src/main/resources/2022-10-13.png";

        Map<Object, Object> data = new HashMap<>();

        data.put("additionalMetadata", metaData);
        data.put("file", Paths.get(filePath));

        return sendPOSTRequest(SITE + "/pet/" + id + "/uploadImage",
                createPOSTBodyForMultipartFormData(data, boundary),
                "multipart/form-data; boundary=" + boundary);
    }

    public Optional<Pet[]> findPetByStatus(String status) {
        ApiResponse response = sendGETRequest(SITE + "/pet/findByStatus?status=" + status);

        return response.getCode() == 200 ? Optional.of(
                GSON.fromJson(response.getMessage(), Pet[].class)) : Optional.empty();
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

    @SneakyThrows
    private ApiResponse sendPOSTRequest(String fullPath, BodyPublisher body, String contentType) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .POST(body)
                .header("accept", "application/json")
                .header("Content-Type", contentType)
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return ApiResponse.builder()
                .code(httpResponse.statusCode())
                .message(httpResponse.body())
                .build();
    }

    @SneakyThrows
    private ApiResponse sendPUTRequest(String fullPath, String body, String contentType) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .header("accept", "application/json")
                .header("Content-Type", contentType)
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return ApiResponse.builder()
                .code(httpResponse.statusCode())
                .message(httpResponse.body())
                .build();
    }

    @SneakyThrows
    private ApiResponse sendDELETERequest(String fullPath, String apiKey) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .DELETE()
                .header("accept", "application/json")
                .header("api_key", apiKey)
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.statusCode() != 200 ?
                ApiResponse.builder()
                        .code(httpResponse.statusCode())
                        .message(httpResponse.body())
                        .build()
                : GSON.fromJson(httpResponse.body(), ApiResponse.class);
    }

    @SneakyThrows
    private BodyPublisher createPOSTBodyForMultipartFormData(
            Map<Object, Object> data, String boundary) {

        var byteArrays = new ArrayList<byte[]>();

        byte[] separator = ("--" + boundary + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);

        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                System.out.println("path = " + path);
                String mimeType = Files.probeContentType(path);
                System.out.println("mimeType = " + mimeType);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\""
                        + path.getFileName() + "\"\r\nContent-Type: " + mimeType
                        + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(
                        ("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue()
                                + "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays
                .add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
