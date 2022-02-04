package com.enigma.api.inventory.services;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.repositories.UnitRepository;
import com.enigma.api.inventory.services.impl.UnitServiceImpl;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {
    
    @InjectMocks
    private UnitServiceImpl service;

    @Mock
    private UnitRepository repository;

    @Test
    public void shouldSave() {
        Unit input = new Unit();
        input.setCode("xyz");
        input.setDescription("XYZ");
        
        Unit output = new Unit();
        output.setId(1);
        output.setCode("xyz");
        output.setDescription("XYZ");
        
        when(repository.save(any()))
                .thenReturn(output);
        
        Unit result = service.save(input);

        assertEquals(output, result);
    }
}
