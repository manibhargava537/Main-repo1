package com.smartcrop.service;

import com.smartcrop.entity.EmailToken;
import com.smartcrop.entity.EmailVerificationToken;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.model.EmailTokenModel;

import java.util.Map;
import java.util.Optional;

public interface EmailTokenService {
    
     EmailToken saveConfirmEmailToken(EmailToken token);
    
     Optional<EmailTokenModel> getByToken(String token);

     Map<String, Object> saveAndGetConfirmEmailToken( SmartCropUser userEntity);

     boolean saveUserPassword(EmailTokenModel emailTokenModel);

     String generateUserProfileLink(long userId, String userType);

     EmailVerificationToken saveConfirmEmailTokenForRegistration(String email);
}
