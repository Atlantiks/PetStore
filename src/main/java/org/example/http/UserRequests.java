package org.example.http;

import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserRequests {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String SITE = "https://petstore.swagger.io/v2";
    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();


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
}
