package com.enigma.api.inventory.models;

public class StockResponse {
    
    private Integer id;
    private ItemResponse item;
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemResponse getItem() {
        return item;
    }

    public void setItem(ItemResponse item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
