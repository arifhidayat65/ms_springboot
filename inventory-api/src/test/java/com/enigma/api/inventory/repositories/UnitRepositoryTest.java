package com.enigma.api.inventory.repositories;

import com.enigma.api.inventory.entities.Unit;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UnitRepositoryTest {
    
    @Autowired
    private UnitRepository repository;
    
    @Test
    void shouldSave() {
        Unit unit = new Unit();
        unit.setCode("xyz");
        unit.setDescription("XYZ");
        repository.save(unit);
        
        Unit savedUnit = repository.findById(unit.getId()).get();
        assertEquals(unit, savedUnit);
    }
}
