package com.smartcrop.repository;

import com.smartcrop.entity.CropDetails;
import com.smartcrop.entity.SoilType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoilTypeRepository extends JpaRepository<SoilType, Long> {
    List<SoilType> findAll();

    List<SoilType> findAllByActive(boolean active);

    Optional<SoilType> findByName(String name);
}

