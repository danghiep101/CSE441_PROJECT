package com.example.cse441_project.data.model.discount;

public class Discount {
    private String id;
    private String name;
    private int value;
    private int quantity;
    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Discount() {
    }

    public Discount(String id, String name, int value, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.quantity = quantity;
        this.description = description;

    }
}
