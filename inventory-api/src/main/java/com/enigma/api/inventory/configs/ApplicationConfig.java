package com.enigma.api.inventory.configs;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.models.UnitModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        modelMapper.typeMap(UnitModel.class, Unit.class).addMappings(mapper -> {
            mapper.skip(Unit::setId);
        });
        return modelMapper;
    }
}
