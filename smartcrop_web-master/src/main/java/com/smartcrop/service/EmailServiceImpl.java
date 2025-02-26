package com.smartcrop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    @Override
    @Async
    public void sendVerificationMail(String to, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail-templates/email-verification-mail.html", thymeleafContext);

        sendHtmlMessage(to, "Set your password", htmlBody);
    }

    @Override
    @Async
    public void sendVerificationMailBeforeOnBoard(String to, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail-templates/verify_email_before_register.html", thymeleafContext);

        sendHtmlMessage(to, "Verify Your Email", htmlBody);
    }
    @Override
    @Async
    public void sendAcknowledgementMail(String to, Map<String, Object> templateModel) throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail-templates/registration-success-waiting-for-approval.html", thymeleafContext);

        sendHtmlMessage(to, "Registration Acknowledgement", htmlBody);
    }

    @Override
    @Async
    public void sendSuccessMail(String to, Map<String, Object> templateModel) throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail-templates/application_approved.html", thymeleafContext);

        sendHtmlMessage(to, "Account approved - Welcome to our platform!", htmlBody);
    }

    @Override
    @Async
    public void sendApprovalMailToAdmin(String to, Map<String, Object> templateModel) throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail-templates/approve-user-registration.html", thymeleafContext);

        sendHtmlMessage(to, "Approve User Registration", htmlBody);

    }

    @Override
    public void sendRejectionEmailToUser(String to, Map<String, Object> templateModel) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail-templates/application_rejected.html", thymeleafContext);

        sendHtmlMessage(to, "Application Rejected / Referred Back", htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody)  {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(fromEmailAddress);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            emailSender.send(message);
            log.info("Email has sent successfully to {}", to);
        } catch (MessagingException messagingException) {
            log.error("messagingException occurred while  sending {} . exception={}",subject,messagingException.getMessage());
        }

    }
}
