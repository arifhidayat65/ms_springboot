package com.enigma.api.inventory.services.impl;

import com.enigma.api.inventory.services.CommonService;
import java.util.List;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class CommonServiceImpl<T, ID> implements CommonService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public CommonServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }
    
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T removeById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            repository.deleteById(id);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll(Sort.by("id"));
    }

    @Override
    public Page<T> findAll(T search, int page, int size, Direction direction) {
        Sort sort = Direction.DESC.equals(direction) ?
                Sort.by(direction, "id") : Sort.by("id");
        
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING);
        return repository.findAll(
                Example.of(search, matcher),
                PageRequest.of(page, size, sort));
    }
}
