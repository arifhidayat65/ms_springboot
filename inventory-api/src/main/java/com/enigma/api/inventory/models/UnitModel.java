package com.enigma.api.inventory.models;

import javax.validation.constraints.Size;

public class UnitModel {
    
    private Integer id;
    
    @Size(min = 1, max = 10)
    private String code;
    
    @Size(min = 1, max = 100)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
