package com.smartcrop.repository;

import com.smartcrop.entity.CropDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropDetailsRepository extends JpaRepository<CropDetails, Long> {

    List<CropDetails> findAll();

    List<CropDetails> findAllByActive(boolean active);
}
