package com.smartcrop.repository;

import com.smartcrop.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country,Long> {

    List<Country> findAll();
    List<Country> findAllByActive(boolean active);

    Optional<Country> findByName(String name);
}
