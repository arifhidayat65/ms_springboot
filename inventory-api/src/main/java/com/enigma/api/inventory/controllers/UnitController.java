package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.PagedList;
import com.enigma.api.inventory.models.ResponseMessage;
import com.enigma.api.inventory.models.UnitModel;
import com.enigma.api.inventory.models.UnitSearch;
import com.enigma.api.inventory.services.UnitService;
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

@RequestMapping("/units")
@RestController
public class UnitController {
    
    @Autowired
    private UnitService service;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping
    public ResponseMessage<UnitModel> add(@RequestBody @Valid UnitModel model) {
        Unit entity = modelMapper.map(model, Unit.class);
        entity = service.save(entity);
        
        UnitModel data = modelMapper.map(entity, UnitModel.class);
        return ResponseMessage.success(data);
    }
    
    @PutMapping("/{id}")
    public ResponseMessage<UnitModel> edit(
            @PathVariable Integer id, @RequestBody @Valid UnitModel model) {
        Unit entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        modelMapper.map(model, entity);
        entity = service.save(entity);
        
        UnitModel data = modelMapper.map(entity, UnitModel.class);
        return ResponseMessage.success(data);
    }
    
    @DeleteMapping("/{id}")
    public ResponseMessage<UnitModel> removeById(@PathVariable Integer id) {
        Unit entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        UnitModel data = modelMapper.map(entity, UnitModel.class);
        return ResponseMessage.success(data);
    }
    
    @GetMapping("/{id}")
    public ResponseMessage<UnitModel> findById(@PathVariable Integer id) {
        Unit entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        UnitModel data = modelMapper.map(entity, UnitModel.class);
        return ResponseMessage.success(data);
    }
    
    @GetMapping
    public ResponseMessage<PagedList<UnitModel>> findAll(
            @Valid UnitSearch model
    ) {
        Unit search = modelMapper.map(model, Unit.class);
        
        Page<Unit> entityPage = service.findAll(search,
                model.getPage(), model.getSize(), model.getSort());
        List<Unit> entities = entityPage.toList();
        
        List<UnitModel> models = entities.stream()
                .map(e -> modelMapper.map(e, UnitModel.class))
                .collect(Collectors.toList());
        
        PagedList<UnitModel> data = new PagedList(models,
                entityPage.getNumber(), entityPage.getSize(),
                entityPage.getTotalElements());
        return ResponseMessage.success(data);
    }
}
