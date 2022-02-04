package com.enigma.api.inventory.models;

public class ItemSearch extends PageSearch {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
