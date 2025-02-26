package com.smartcrop.repository;

import com.smartcrop.entity.Country;
import com.smartcrop.entity.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
    List<State> findAll();
    List<State> findByCountry_IdAndActive(Long countryId, boolean active);

    Optional<State> findByCountry_IdAndName(Long countryId, String name);
}
