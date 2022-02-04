package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.ImageUploadRequest;
import com.enigma.api.inventory.models.ItemElement;
import com.enigma.api.inventory.models.ItemRequest;
import com.enigma.api.inventory.models.ItemResponse;
import com.enigma.api.inventory.models.ItemSearch;
import com.enigma.api.inventory.models.PagedList;
import com.enigma.api.inventory.models.ResponseMessage;
import com.enigma.api.inventory.services.FileService;
import com.enigma.api.inventory.services.ItemService;
import com.enigma.api.inventory.services.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/items")
@RestController
public class ItemController {
    
    @Autowired
    private ItemService service;
    
    @Autowired
    private UnitService unitService;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Operation(summary = "Add Item", description = "Some Description..", tags = { "item" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })
    @PostMapping
    public ResponseMessage<ItemResponse> add(
            @RequestBody @Valid ItemRequest model) {
        Item entity = modelMapper.map(model, Item.class);
        
        Unit unit = unitService.findById(model.getUnitId());
        entity.setUnit(unit);
        
        entity = service.save(entity);
        
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        return ResponseMessage.success(data);
    }
    
    @PutMapping("/{id}")
    public ResponseMessage<ItemResponse> edit(@PathVariable Integer id,
            @RequestBody @Valid ItemRequest model) {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        Unit unit = unitService.findById(model.getUnitId());
        entity.setUnit(unit);
        
        modelMapper.map(model, entity);
        entity = service.save(entity);
        
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        return ResponseMessage.success(data);
    }
    
    @DeleteMapping("/{id}")
    public ResponseMessage<ItemResponse> removeById(@PathVariable Integer id) {
        Item entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        return ResponseMessage.success(data);
    }
    
    @GetMapping("/{id}")
    public ResponseMessage<ItemResponse> findById(@PathVariable Integer id) {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        return ResponseMessage.success(data);
    }
    
    @GetMapping
    public ResponseMessage<PagedList<ItemElement>> findAll(
            @Valid ItemSearch model
    ) {
        Item search = modelMapper.map(model, Item.class);
        
        Page<Item> entityPage = service.findAll(search,
                model.getPage(), model.getSize(), model.getSort());
        List<Item> entities = entityPage.toList();
        
        List<ItemElement> models = entities.stream()
                .map(e -> modelMapper.map(e, ItemElement.class))
                .collect(Collectors.toList());
        
        PagedList<ItemElement> data = new PagedList(models,
                entityPage.getNumber(), entityPage.getSize(),
                entityPage.getTotalElements());
        return ResponseMessage.success(data);
    }
    
    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseMessage upload(@PathVariable Integer id,
            @Valid ImageUploadRequest model) throws IOException {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        
        fileService.upload(entity, model.getFile().getInputStream());
        
        return ResponseMessage.success(true);
    }
    
    @GetMapping("/{id}/image")
    public void download(@PathVariable Integer id,
            HttpServletResponse response) throws IOException {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + entity.getId() + "\"");
        fileService.download(entity, response.getOutputStream());
    }
}
