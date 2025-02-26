package com.smartcrop.service;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailService {

    void sendVerificationMail(String to, Map<String, Object> templateModel) throws MessagingException;

    void sendSuccessMail(String to, Map<String, Object> templateModel) throws MessagingException;

    void sendApprovalMailToAdmin(String to, Map<String, Object> templateModel) throws MessagingException;

    void sendRejectionEmailToUser(String to, Map<String, Object> templateModel) throws MessagingException;

    void sendAcknowledgementMail(String to, Map<String, Object> templateModel) throws MessagingException;

    void sendVerificationMailBeforeOnBoard(String to, Map<String, Object> templateModel) throws MessagingException;
}
