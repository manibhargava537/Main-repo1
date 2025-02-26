package com.smartcrop.repository;

import com.smartcrop.entity.SmartCropUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmartCropUserRepository extends JpaRepository<SmartCropUser,Long> {

    Optional<SmartCropUser> findByEmail(String email);
    Optional<SmartCropUser> findByUserName(String userName);

    Optional<SmartCropUser> findByUserId(String userId);

    SmartCropUser findTopByRole(String role);

    @Modifying
    @Query("update SmartCropUser u set u.status = :status , u.remark = ''  where u.id = :id")
    void updateUserStatus(@Param("status") String status, @Param("id") Long id);

    @Modifying
    @Query("update SmartCropUser u set u.status = :status , u.remark = :remark where u.id = :id")
    void updateUserStatusWithRemark(@Param("status") String status, @Param("remark") String remark, @Param("id") Long id);

    Optional<SmartCropUser> findByEmailAndStatusIgnoreCase(String email,String status);

    /*@Query(value = "CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;", nativeQuery = true)
    Long createSequenceTable();

    @Query(value = "SELECT user_id_seq.nextval FROM dual", nativeQuery = true)
    Long getNextSeriesId();*/
}
