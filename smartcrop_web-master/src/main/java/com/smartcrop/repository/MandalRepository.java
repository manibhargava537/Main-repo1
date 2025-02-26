package com.smartcrop.repository;

import com.smartcrop.entity.Country;
import com.smartcrop.entity.Mandal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MandalRepository extends CrudRepository<Mandal, Long> {

    List<Mandal> findAll();
    List<Mandal> findByDistrict_IdAndActive(Long districtId, boolean active);

    Optional<Mandal> findByDistrict_IdAndName(Long districtId, String name);
}
