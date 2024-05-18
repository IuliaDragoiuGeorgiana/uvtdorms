package com.uvtdorms.services;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendMail(final @NonNull String to, final @NonNull String subject, final @NonNull String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPassword(final String to, final String subject, final String name, final String password) {
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("name", name);
        context.setVariable("password", password);

        String htmlContent = templateEngine.process("example-email.html", context);

        sendMail(to, subject, htmlContent);
    }

    public void sendRegisterConfirm(final String to, final String name, final String generatedPassword) {
        String subject = "Confirm registration request";
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("name", name);
        context.setVariable("password", generatedPassword);
        String htmlContent = templateEngine.process("confirm-register-request.html", context);

        sendMail(to, subject, htmlContent);
    }

    public void sendNewRegisterRequestConfirm(final String to, final String name) {
        String subject = "Confirm new registration request";
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("name", name);
        String htmlContent = templateEngine.process("confirm-new-register-request.html", context);

        sendMail(to, subject, htmlContent);
    }

    public void sendRegisterRequestAcceptedEmail(final String to, final String name) {
        String subject = "Registration request accepted";
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("name", name);

        String htmlContent = templateEngine.process("register-request-accepted.html", context);

        sendMail(to, subject, htmlContent);
    }

    public void sendRegisterRequestDeclinedEmail(final String to, final String name) {
        String subject = "Registration request declined";
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("name", name);

        String htmlContent = templateEngine.process("register-request-declined.html", context);

        sendMail(to, subject, htmlContent);
    }

    public void sendLaundryAppointmentCanceledBecauseOfWashingMachineFailure(final String email, final String date) {
        String subject = "Laundry appointment canceled";
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("date", date);

        String htmlContent = templateEngine.process("laundry-appointment-canceled.html", context);

        sendMail(email, subject, htmlContent);
    }

    public void sendLaundryAppointmentCanceledBecauseOfDryerFailure(final String email, final String date,
            final String modifiableAppointmentId) {
        String subject = "Laundry appointment canceled";
        Context context = new Context();
        context.setVariable("subject", subject);
        context.setVariable("date", date);
        context.setVariable("id", modifiableAppointmentId);

        String htmlContent = templateEngine.process("laundry-appointment-should-be-modified", context);

        sendMail(email, subject, htmlContent);
    }
}
