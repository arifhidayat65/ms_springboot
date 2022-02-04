package com.enigma.api.inventory.repositories;

import com.enigma.api.inventory.entities.StockSummary;
import java.util.List;

public interface StockSummaryRepository {
    
    public List<StockSummary> findAllSummaries();
}
