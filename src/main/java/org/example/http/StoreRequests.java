package org.example.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.example.entity.Order;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;

public class StoreRequests {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String SITE = "https://petstore.swagger.io/v2";
    public static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    public Map<String,String> getStoreInventory() {
        ApiResponse response = sendGETRequest(SITE + "/store/inventory");
        Type type = new TypeToken<Map<String, String>>(){}.getType();

        return GSON.fromJson(response.getMessage(), type);
    }

    public Optional<Order> findOrderById(Long id) {
        ApiResponse response =  sendGETRequest(SITE + "/store/order/" + id);

        return response.getCode() == 200 ? Optional.of(
                GSON.fromJson(response.getMessage(), Order.class)) : Optional.empty();
    }

    public Optional<Order> placeOrder(Order order) {
        String requestBody = GSON.toJson(order);

        ApiResponse response =  sendPOSTRequest(
                SITE + "/store/order/",
                requestBody,
                "application/json");

        return response.getCode() == 200 ? Optional.of(
                GSON.fromJson(response.getMessage(), Order.class)) : Optional.empty();
    }

    public ApiResponse deleteOrder(Long id) {
        return sendDELETERequest(SITE + "/store/order/" + id);
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
    private ApiResponse sendDELETERequest(String fullPath) {
        var request = HttpRequest.newBuilder(URI.create(fullPath))
                .DELETE()
                .header("accept", "application/json")
                .build();
        var httpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return httpResponse.statusCode() != 200 ?
                ApiResponse.builder()
                        .code(httpResponse.statusCode())
                        .message(httpResponse.body())
                        .build()
                : GSON.fromJson(httpResponse.body(), ApiResponse.class);
    }
}
