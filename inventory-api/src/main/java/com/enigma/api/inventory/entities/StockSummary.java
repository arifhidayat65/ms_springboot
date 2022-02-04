package com.enigma.api.inventory.entities;

public class StockSummary {
    
    private Item item;
    private Long quantity;

    public StockSummary(Item item, Long quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
