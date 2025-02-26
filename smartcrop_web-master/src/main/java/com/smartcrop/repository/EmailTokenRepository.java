package com.smartcrop.repository;

import com.smartcrop.entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {

    EmailToken getByToken(String token);

    EmailToken getBySmartCropUser_Email(String email);
}
