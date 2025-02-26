package com.smartcrop.repository;

import com.smartcrop.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findBySmartCropUser_Email(String email);

    @Query(value = "SELECT e.* FROM employee e " +
            "INNER JOIN smartcrop_user su ON su.id = e.smart_crop_user_fk\n" +
            "WHERE su.status != 'approve' ", nativeQuery = true)
    List<Employee> getAllEmployeesPendingForApproval();
}