package com.smartcrop.repository;

import com.smartcrop.entity.Country;
import com.smartcrop.entity.Mandal;
import com.smartcrop.entity.Village;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VillageRepository extends CrudRepository<Village, Long> {

    List<Village> findAll();
    List<Village> findByMandal_IdAndActive(Long mandalId, boolean active);

    Optional<Village> findByMandal_IdAndName(Long mandalId, String name);
}
