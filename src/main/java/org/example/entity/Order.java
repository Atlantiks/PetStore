package org.example.entity;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private Long petId;
    private Integer quantity;
    private String username;
    private String shipDate;
    private OrderStatus status;
    private Boolean complete;

    enum OrderStatus {
        placed,
        approved,
        delivered
    }
}
