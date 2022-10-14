package org.example.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.example.entity.Order;
import org.example.entity.Pet;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

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
