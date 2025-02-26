package com.smartcrop.repository;

import com.smartcrop.entity.SmartCropUserDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartCropUserDocRepository extends JpaRepository<SmartCropUserDoc,Long> {

    List<SmartCropUserDoc> findByEmployee_Id(Long id);

    SmartCropUserDoc findByEmployee_IdAndDocumentType(Long id,String documentType);

    SmartCropUserDoc findByFarmer_IdAndDocumentType(Long id,String documentType);
}
