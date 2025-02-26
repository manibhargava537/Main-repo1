package com.smartcrop.repository;

import com.smartcrop.entity.Country;
import com.smartcrop.entity.District;
import com.smartcrop.entity.Mandal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictOrCityRepository extends CrudRepository<District, Long> {

    List<District> findAll();
    List<District> findByState_IdAndActive(Long stateId, boolean active);

    Optional<District> findByState_IdAndName(Long stateId, String name);
}
