package com.smartcrop.repository;

import com.smartcrop.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmEmailTokenRepository extends JpaRepository<EmailVerificationToken , Long> {

    EmailVerificationToken getByTokenAndEmail(String token, String email);
}
