package com.enigma.api.inventory.models;

import javax.validation.constraints.NotNull;

public class StockRequest {
    
    private Integer itemId;
    
    @NotNull
    private Integer quantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
