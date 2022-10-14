package org.example.entity;

import lombok.Data;

@Data
public class Pet {
    private Long id;
    private String name;
    private Category category;
    private PetStatus status;
    private String[] photoUrls;
    private Tag[] tags;

    public enum PetStatus {
        available,
        pending,
        sold
    }
}
