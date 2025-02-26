package com.smartcrop.controllers;

import com.smartcrop.entity.EmailToken;
import com.smartcrop.entity.EmailVerificationToken;
import com.smartcrop.entity.SmartCropUser;
import com.smartcrop.entity.Village;
import com.smartcrop.model.EmailTokenModel;
import com.smartcrop.repository.ConfirmEmailTokenRepository;
import com.smartcrop.service.EmailService;
import com.smartcrop.service.EmailTokenService;
import com.smartcrop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/confirmEmail")
@Slf4j
public class EmailTokenController {
    @Autowired
    private EmailTokenService emailTokenService;

    @Autowired
    private ConfirmEmailTokenRepository confirmEmailTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{token}")
    public String confirmEmail(@PathVariable String token, @ModelAttribute("emailTokenModel") EmailTokenModel emailTokenModel, Model model) {
        try {
            Optional<EmailTokenModel> optionalEmailTokenModel = emailTokenService.getByToken(token);
            if (optionalEmailTokenModel.isPresent()
                    && optionalEmailTokenModel.get().getToken().equals(token)) {
                return "generate-password";
            } else {
                model.addAttribute("invalidToken", true);
                EmailTokenModel newEmailTokenModel = EmailTokenModel.builder().build();
                model.addAttribute("showPassRegenSubmit", true);
                model.addAttribute("regenTokenModel", newEmailTokenModel);

                return "re-generate-password";
            }
        } catch (Exception e) {
            log.error("Exception occurred in confirmEmail {} ", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{token}")
    public String savePassword(@PathVariable String token, @ModelAttribute("emailTokenModel") EmailTokenModel emailTokenModel, Model model) {
        try {
            if (StringUtils.isNotEmpty(token) && token.equals(emailTokenModel.getToken())) {
                emailTokenService.saveUserPassword(emailTokenModel);
                emailTokenModel.setPassword("");
                model.addAttribute("passwordUpdated", true);
            } else {
                model.addAttribute("invalidToken", true);
                model.addAttribute("showPassRegenSubmit", true);
                return "re-generate-password";
            }
        } catch (Exception e) {
            log.error("Exception occurred while saving password!!", e);
            return "error";
        }
        return "generate-password";
    }

    @GetMapping("/re-generate-password")
    public String regeneratePassword(@ModelAttribute("regenTokenModel") EmailTokenModel regenTokenModel, Model model) {
        return "re-generate-password";
    }

    @PostMapping("/re-generate-password")
    public String sendRegeneratedToken(@ModelAttribute("regenTokenModel") EmailTokenModel emailTokenModel, Model model) {

        String userEmail = emailTokenModel.getEmail();
        SmartCropUser smartCropUser = userService.isValidUser(userEmail);
        if (null != smartCropUser) {

            Map<String, Object> templateModel = emailTokenService.saveAndGetConfirmEmailToken(smartCropUser);
            try {
                emailService.sendVerificationMail(smartCropUser.getEmail(), templateModel);
            } catch (MessagingException e) {
                model.addAttribute("resendEmailStatus", false);
                log.error("Error while re-sending password link ---> {}", e.getMessage());
            }

            model.addAttribute("isValidUser", true);
            model.addAttribute("resendEmailStatus", true);
            model.addAttribute("showPassRegenSubmit", false);
        } else {
            model.addAttribute("isValidUser", false);
            model.addAttribute("showPassRegenSubmit", true);
        }
        return "re-generate-password";
    }

    @PostMapping("/sendMailVerificationMail")
    public @ResponseBody boolean sendVerificationMail(@RequestParam("email") String email, @RequestParam("name") String name) throws MessagingException {
        EmailVerificationToken token = emailTokenService.saveConfirmEmailTokenForRegistration(email);
        Map<String, Object> mailTemplate = new HashMap<>();
        mailTemplate.put("recipientName", name);
        mailTemplate.put("email", email);
        mailTemplate.put("token", token.getToken());
        emailService.sendVerificationMailBeforeOnBoard(email, mailTemplate);
        return true;
    }

    @GetMapping("/verify")
    public @ResponseBody boolean verifyEmailWithToken(@RequestParam("token") String token, @RequestParam("email") String email) {
        EmailVerificationToken tokenEntity = confirmEmailTokenRepository.getByTokenAndEmail(token, email);
        if (tokenEntity != null) {
            return true;
        }
        return false;
    }
}
