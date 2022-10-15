package org.example.http;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.example.entity.User;
import org.example.exception.NotFoundException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class UserRequests {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String SITE = "https://petstore.swagger.io/v2";
    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();


    public Optional<User> getUserByName(String userName) {
        var response = sendGETRequest2(SITE + "/user/" + userName);

        switch (response.getCode()) {
            case 200 :
                return Optional.of(GSON.fromJson(response.getMessage(), User.class));
            case 400:
                throw new NotFoundException("Invalid username supplied");
            case 404:
                throw new NotFoundException("User not found");
            default:
                return Optional.empty();
        }
    }

    public ApiResponse login(String userName, String userPassword) {
        return sendGETRequest(SITE + "/user/login?username=" + userName + "&password=" + userPassword);
    }

    public ApiResponse logout() {
        return sendGETRequest(SITE + "/user/logout");
    }

    @SneakyThrows
    private ApiResponse sendGETRequest(String fullPath) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .GET()
                .header("accept", "application/json")
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(httpResponse.body(),ApiResponse.class);
    }

    @SneakyThrows
    private ApiResponse sendGETRequest2(String fullPath) {
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
