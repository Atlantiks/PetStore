package org.example.service;

import lombok.Setter;
import org.example.entity.Order;
import org.example.exception.OperationFailedException;
import org.example.exception.WrongUserInputException;
import org.example.http.ApiResponse;
import org.example.http.StoreRequests;

import java.time.Instant;
import java.util.Scanner;


public class StoreService {
    private static final StoreService STORE_SERVICE = new StoreService();
    private static final StoreRequests STORE_RQS = new StoreRequests();
    @Setter
    private Scanner scanner;

    private StoreService() {
    }

    public static StoreService getInstance() {
        return STORE_SERVICE;
    }

    public void getPetInventoriesAndStatus() {
        var x = STORE_RQS.getStoreInventory();
        x.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public void findPurchasedOrderById() {
        long userInput;
        System.out.println("Please, enter ID of order to return");
        System.out.println("Only integer numbers (1-10) are allowed here");
        try {
            userInput = Long.parseLong(scanner.nextLine());
            if (userInput < 1 || userInput > 10) {
                throw new WrongUserInputException("Incorrect input. Only integer numbers (1-10) are allowed here");
            }
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers (1-10) are allowed here");
        }

        Order foundOrder = STORE_RQS.findOrderById(userInput).orElseThrow(() -> new OperationFailedException("Order not found"));
        System.out.println(foundOrder);
    }

    public void placeNewOrder() {
        long orderId, petId;
        int quantity;

        try {
            System.out.println("Please, enter ID of order to be placed");
            orderId = Long.parseLong(scanner.nextLine());
            System.out.println("Please, enter ID of pet that needs to be purchased");
            petId = Long.parseLong(scanner.nextLine());
            System.out.println("Please, enter quantity to be purchased");
            quantity = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers are allowed here");
        }

        Order order = Order.builder()
                .id(orderId)
                .petId(petId)
                .quantity(quantity)
                .shipDate(Instant.now().toString())
                .status(Order.OrderStatus.placed)
                .complete(true)
                .build();

        Order placedOrder = STORE_RQS.placeOrder(order).orElseThrow(() -> new OperationFailedException("Invalid order"));
        System.out.println(placedOrder);
    }

    public void deleteOrderById() {
        long userInput;
        System.out.println("Please, enter ID of order to return");
        System.out.println("Only integer positive numbers (>0) are allowed here");
        try {
            userInput = Long.parseLong(scanner.nextLine());
            if (userInput <= 0) {
                throw new WrongUserInputException("Incorrect input. " +
                        "For valid response try integer IDs with positive integer value. " +
                        "Negative or non-integer values will generate API errors");
            }
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer positive numbers (>0) are allowed here");
        }

        ApiResponse response = STORE_RQS.deleteOrder(userInput);

        switch (response.getCode()) {
            case 200:
                System.out.println("\033[0;92m" + "Order with id = " + response.getMessage() +
                        " successfully deleted" + "\033[0m");
                break;
            case 400:
                throw new OperationFailedException("Invalid ID supplied");
            case 404:
                throw new OperationFailedException("Order not found");
            default:
                throw new OperationFailedException("Unknown error occurred.");
        }

    }
}
