package com.example.backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendProjectRequestEmail(String toEmail, String projectName, double budget, String duration, String location) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("Project Request Submission Confirmation");

        // ✅ URL du logo hébergé sur Imgur
        String logoUrl = "https://i.imgur.com/YX34wNO.png"; // Correction pour accéder directement à l'image

        // ✅ HTML de l'email
        String content = "<html><body>"
                + "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                + "<div style='text-align: center; margin-bottom: 20px;'>"
                + "<img src='" + logoUrl + "' alt='App Logo' style='max-width: 150px; margin-bottom: 10px;'>"
                + "<h2 style='color: #004085;'>Project Request Submitted</h2>"
                + "</div>"
                + "<p>Dear Customer,</p>"
                + "<p>We have successfully received your project request. Our project manager is currently reviewing it, and you will receive an update soon.</p>"
                + "<div style='background: #f8f9fa; padding: 15px; border-radius: 5px; margin-top: 10px;'>"
                + "<h3 style='color: #333;'>Project Details:</h3>"
                + "<ul>"
                + "<li><b>Project Name:</b> " + projectName + "</li>"
                + "<li><b>Estimated Budget:</b> " + budget + " €</li>"
                + "<li><b>Estimated Duration:</b> " + duration + "</li>"
                + "<li><b>Location:</b> " + location + "</li>"
                + "</ul>"
                + "</div>"
                + "<p>Thank you for your trust in our services.</p>"
                + "<p style='margin-top: 20px;'>Best regards,</p>"
                + "<p><b>Project Management Team</b></p>"
                + "</div>"
                + "</body></html>";

        helper.setText(content, true); // Activer le HTML
        mailSender.send(message);

        System.out.println("✅ Email sent successfully to: " + toEmail);
    }
}
