package org.example.entity;

import lombok.Data;

@Data
public class Pet {
    private Long id;
    private String name;
    private Category category;
    private PetStatus status;

    public enum PetStatus {
        available,
        pending,
        sold
    }
}
