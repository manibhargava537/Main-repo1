package com.smartcrop.repository;

import com.smartcrop.entity.CurrentCrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentCropRepository extends JpaRepository<CurrentCrop,Long> {
}
