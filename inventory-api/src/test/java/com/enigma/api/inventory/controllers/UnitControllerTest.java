package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.models.UnitModel;
import com.enigma.api.inventory.services.UnitService;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnitController.class)
public class UnitControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UnitService service;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void addShouldSuccess() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);
        entity.setCode("xyz");
        entity.setDescription("XYZ");
        when(service.save(any())).thenReturn(entity);
        
        UnitModel model = new UnitModel();
        model.setId(entity.getId());
        model.setCode(entity.getCode());
        model.setDescription(entity.getDescription());
        when(modelMapper.map(any(Unit.class), any(Class.class))).thenReturn(model);
        
        RequestBuilder request = post("/units")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"xyz\", \"description\": \"XYZ\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.code", is(entity.getCode())));
    }
    
    @Test
    public void findAllShouldReturnEmptyList() throws Exception {
        Page page = Page.empty();
        when(service.findAll(any(), anyInt(), anyInt(), any())).thenReturn(page);
        
        RequestBuilder request = get("/units");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.list", empty()));
    }
}
