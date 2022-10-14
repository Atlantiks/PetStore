package org.example.service;

import lombok.Setter;
import org.example.entity.Order;
import org.example.entity.Pet;
import org.example.exception.NotFoundException;
import org.example.exception.WrongUserInputException;
import org.example.http.StoreRequests;
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
        try {
            userInput = Long.parseLong(scanner.nextLine());
            if (userInput < 1 || userInput > 10) {
                throw new WrongUserInputException("Incorrect input. Only integer numbers (1-10) are allowed here");
            }
        } catch (NumberFormatException e) {
            throw new WrongUserInputException("Incorrect input. Only integer numbers (1-10) are allowed here");
        }

        Order foundOrder = STORE_RQS.findOrderById(userInput).orElseThrow(() -> new NotFoundException("Order not found"));
        System.out.println(foundOrder);
    }
}
