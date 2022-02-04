package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.PagedList;
import com.enigma.api.inventory.models.ResponseMessage;
import com.enigma.api.inventory.models.StockElement;
import com.enigma.api.inventory.models.StockRequest;
import com.enigma.api.inventory.models.StockResponse;
import com.enigma.api.inventory.models.StockSearch;
import com.enigma.api.inventory.services.ItemService;
import com.enigma.api.inventory.services.StockService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/stocks")
@RestController
public class StockController {
    
    @Autowired
    private StockService service;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseMessage<StockResponse> add(
            @RequestBody @Valid StockRequest model) {
        Stock entity = modelMapper.map(model, Stock.class);
        
        Item item = itemService.findById(model.getItemId());
        entity.setItem(item);
        
        entity = service.save(entity);
        
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        return ResponseMessage.success(data);
    }
    
    @PutMapping("/{id}")
    public ResponseMessage<StockResponse> edit(@PathVariable Integer id,
            @RequestBody @Valid StockRequest model) {
        Stock entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        Item item = itemService.findById(model.getItemId());
        entity.setItem(item);
        
        modelMapper.map(model, entity);
        entity = service.save(entity);
        
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        return ResponseMessage.success(data);
    }
    
    @DeleteMapping("/{id}")
    public ResponseMessage<StockResponse> removeById(@PathVariable Integer id) {
        Stock entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        return ResponseMessage.success(data);
    }
    
    @GetMapping("/{id}")
    public ResponseMessage<StockResponse> findById(@PathVariable Integer id) {
        Stock entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        return ResponseMessage.success(data);
    }
    
    @GetMapping
    public ResponseMessage<PagedList<StockElement>> findAll(
            @Valid StockSearch model
    ) {
        Stock search = modelMapper.map(model, Stock.class);
        
        Page<Stock> entityPage = service.findAll(search,
                model.getPage(), model.getSize(), model.getSort());
        List<Stock> entities = entityPage.toList();
        
        List<StockElement> models = entities.stream()
                .map(e -> modelMapper.map(e, StockElement.class))
                .collect(Collectors.toList());
        
        PagedList<StockElement> data = new PagedList(models,
                entityPage.getNumber(), entityPage.getSize(),
                entityPage.getTotalElements());
        return ResponseMessage.success(data);
    }
    
    @GetMapping("/summaries")
    public ResponseMessage<List<StockSummary>> findAllSummaries() {
        List<StockSummary> entityPage = service.findAllSummaries();
        return ResponseMessage.success(entityPage);
    }
}
