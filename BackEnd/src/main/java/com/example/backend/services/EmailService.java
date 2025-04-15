package com.example.backend.services;

import com.example.backend.entities.Insurance;
import com.example.backend.entities.Offer;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

   /* public void sendOfferNotification(String userEmail, Offer offer) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail); // ✅ Utilisation correcte de la variable passée en paramètre
        message.setSubject("New Offer Available!");
        message.setText(
                "Hello,\n\n" +
                        "A new offer has been added:\n" +
                        "Title: " + offer.getTitle() + "\n" +
                        "Description: " + offer.getDescription() + "\n" +
                        "Valid from: " + offer.getStart_Date() + " to " + offer.getEnd_Date() + "\n\n" +
                        "Log in to view more details.\n\n" +
                        "Best regards,\nYour Application Team"
        );

        mailSender.send(message);
    }*/
   public void sendOfferNotification(String userEmail, Offer offer) throws MessagingException {
       MimeMessage message = mailSender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

       helper.setTo(userEmail);
       helper.setSubject("🎉 New Offer Available!");

       String emailContent = buildEmailContent(offer);

       helper.setText(emailContent, true); // Enable HTML content
       mailSender.send(message);
   }

    private String buildEmailContent(Offer offer) {
        // Convert java.util.Date to LocalDate
        LocalDate startDate = offer.getStart_Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = offer.getEnd_Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Format the LocalDate to dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        return "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px; background-color: #f9f9f9;'>"
                + "    <div style='text-align: center; margin-bottom: 20px;'>"
                + "        <img src='https://i.imgur.com/YX34wNO.png' alt='Builderz Logo' style='max-width: 150px;'>"
                + "    </div>"
                + "    <h2 style='color: #16A085; text-align: center; font-size: 28px;'>🚀 New Offer Available!</h2>"
                + "    <p style='font-size: 18px; color: #555; text-align: center;'>A new offer has been added. Check out the details below:</p>"
                + "    <div style='background: #fff; padding: 15px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);'>"
                + "        <p style='font-size: 18px;'><strong>📌 Title:</strong> " + offer.getTitle() + "</p>"
                + "        <p style='font-size: 18px;'><strong>📝 Description:</strong> " + offer.getDescription() + "</p>"
                + "        <p style='font-size: 18px;'><strong>📅 Valid From:</strong> " + formattedStartDate + " to " + formattedEndDate + "</p>"
                + "    </div>"
                + "    <div style='text-align: center; margin-top: 20px;'>"
                + "        <a href='http://localhost:4200/#/app-view-offers" + offer.getId_offer() + "'"
                + "           style='display: inline-block; padding: 12px 20px; background-color: #16A085; color: #fff; font-size: 18px; font-weight: bold; text-decoration: none; border-radius: 5px; box-shadow: 0 4px 6px rgba(0, 123, 255, 0.3);'>"
                + "            🔗 View Offer"
                + "        </a>"
                + "    </div>"
                + "    <p style='text-align: center; color: #777; font-size: 16px; margin-top: 20px;'>Thank you for using our platform!</p>"
                + "    <hr style='border: 0; height: 1px; background: #ddd; margin: 20px 0;'>"
                + "    <p style='text-align: center; color: #555; font-size: 16px;'>Best regards,<br><strong>Builderz Team</strong></p>"
                + "</div>";
    }






    public void sendInsuranceNotification(String userEmail, Insurance insurance) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(userEmail);
        helper.setSubject("🎉 New Insurance Available!");

        String emailContent = buildEmailContent(insurance);

        helper.setText(emailContent, true); // Enable HTML content
        mailSender.send(message);
    }

    private String buildEmailContent(Insurance insurance) {
        // Convert java.util.Date to LocalDate
        LocalDate startDate = insurance.getStart_Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = insurance.getEnd_Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Format the LocalDate to dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedStartDate = startDate.format(formatter);
        String formattedEndDate = endDate.format(formatter);

        return "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px; background-color: #f9f9f9;'>"
                + "    <div style='text-align: center; margin-bottom: 20px;'>"
                + "        <img src='https://i.imgur.com/YX34wNO.png' alt='Builderz Logo' style='max-width: 150px;'>"
                + "    </div>"
                + "    <h2 style='color: #16A085; text-align: center; font-size: 28px;'>🚀 New Offer Available!</h2>"
                + "    <p style='font-size: 18px; color: #555; text-align: center;'>A new offer has been added. Check out the details below:</p>"
                + "    <div style='background: #fff; padding: 15px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);'>"

                + "        <p style='font-size: 18px;'><strong>📝 Description:</strong> " + insurance.getDescription() + "</p>"
                + "        <p style='font-size: 18px;'><strong>📅 Valid From:</strong> " + formattedStartDate + " to " + formattedEndDate + "</p>"
                + "    </div>"
                + "    <div style='text-align: center; margin-top: 20px;'>"
                + "        <a href='http://localhost:4200/#/app-view-offers" + insurance.getId_Insurance() + "'"
                + "           style='display: inline-block; padding: 12px 20px; background-color: #16A085; color: #fff; font-size: 18px; font-weight: bold; text-decoration: none; border-radius: 5px; box-shadow: 0 4px 6px rgba(0, 123, 255, 0.3);'>"
                + "            🔗 View Offer"
                + "        </a>"
                + "    </div>"
                + "    <p style='text-align: center; color: #777; font-size: 16px; margin-top: 20px;'>Thank you for using our platform!</p>"
                + "    <hr style='border: 0; height: 1px; background: #ddd; margin: 20px 0;'>"
                + "    <p style='text-align: center; color: #555; font-size: 16px;'>Best regards,<br><strong>Builderz Team</strong></p>"
                + "</div>";
    }




















}
