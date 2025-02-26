package com.smartcrop.repository;

import com.smartcrop.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    @Query(value = "SELECT f.* FROM farmer f " +
            "INNER JOIN smartcrop_user su ON su.id = f.smart_crop_user_fk " +
            "WHERE su.status != 'approve' ", nativeQuery = true)
    List<Farmer> getAllRegisteredFarmerPendingForApproval();
}
