package com.smartcrop.service;

import com.smartcrop.entity.EmailToken;
import com.smartcrop.entity.EmailVerificationToken;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.EmailTokenModel;
import com.smartcrop.repository.ConfirmEmailTokenRepository;
import com.smartcrop.repository.EmailTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EmailTokenServiceImpl implements EmailTokenService {

    @Value("${spring.application.siteURL}")
    private String siteURL;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private ConfirmEmailTokenRepository confirmEmailTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public EmailToken saveConfirmEmailToken(EmailToken confirmEmailToken) {
        return emailTokenRepository.save(confirmEmailToken);
    }

    @Override
    public Optional<EmailTokenModel> getByToken(String token) {
        EmailToken emailToken = emailTokenRepository.getByToken(token);
        EmailTokenModel emailTokenModel = null;
        if (emailToken != null) {
            emailTokenModel = EmailTokenModel.builder()
                    .verified(emailToken.isVerified())
                    .token(emailToken.getToken())
                    .build();
        }
        return Optional.ofNullable(emailTokenModel);
    }

    @Override
    public EmailVerificationToken saveConfirmEmailTokenForRegistration(String email) {
        String randomUUID = UUID.randomUUID().toString();
        EmailVerificationToken newToken = EmailVerificationToken.builder()
                .token(randomUUID)
                .email(email)
                .createdOn(new Date())
                .build();

        return confirmEmailTokenRepository.save(newToken);
    }

    public Map<String, Object> saveAndGetConfirmEmailToken(SmartCropUser smartCropUser) {
        String randomUUID = UUID.randomUUID().toString();
        EmailToken newToken = EmailToken.builder()
                .token(randomUUID)
                .smartCropUser(smartCropUser)
                .createdOn(new Date())
                .build();

        EmailToken confirmEmailToken = saveConfirmEmailToken(newToken);

        Map<String, Object> templateModel = generateTokenEmailTemplate(smartCropUser, confirmEmailToken);
        return templateModel;
    }

    private Map<String, Object> generateTokenEmailTemplate(SmartCropUser smartCropUser, EmailToken confirmEmailToken) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", smartCropUser.getFirstName() + " " + smartCropUser.getLastName());
        templateModel.put("email", smartCropUser.getEmail());
        templateModel.put("userId", smartCropUser.getUserId());
        templateModel.put("confirmationLink", siteURL + "/confirmEmail/" + confirmEmailToken.getToken());
        return templateModel;
    }

    @Override
    public boolean saveUserPassword(EmailTokenModel emailTokenModel) {
        EmailToken emailToken = emailTokenRepository.getByToken(emailTokenModel.getToken());
        SmartCropUser smartCropUser = emailToken.getSmartCropUser();
        smartCropUser.setPassword(bCryptPasswordEncoder.encode(emailTokenModel.getPassword()));
        smartCropUser.setDefaultPassword(emailTokenModel.getPassword());
        //userRepo.save(smartCropUser);
        log.info("password updated successfully!");
        emailTokenRepository.delete(emailToken);
        log.info("emailToken record deleted successfully!");
        return true;
    }

    @Override
    public String generateUserProfileLink(long userId, String userType) {
        return siteURL + "/dashboard/" + userType + "/" + userId;
    }

    public EmailTokenModel resendEmailToken(String emailAddress) {
        try {
            EmailToken emailToken = emailTokenRepository.getBySmartCropUser_Email(emailAddress);
            if (null == emailToken) {

            }
//            emailService.sendVerificationMail(emailAddress, "Test Subject", generateTokenEmailTemplate(emailToken.getUser(), emailToken));
        } catch (Exception e) {
            log.error(" Exception inside resendEmailToken, exception = {}", e.getMessage());
        }
        return null;
    }
}
