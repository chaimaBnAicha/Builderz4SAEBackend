package com.example.backend.Services;

import com.example.backend.Entities.Tache;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${app.base-url}")
    private String baseUrl;

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendTaskConfirmationEmail(String to, Tache tache) {
        String subject = "Confirmation de tâche : " + tache.getNom();
        String content = buildEmailContent(tache);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur d'envoi d'email", e);
        }
    }

    private String buildEmailContent(Tache tache) {
        return "<h1>Nouvelle Tâche Assignée</h1>" +
                "<p><strong>Nom :</strong> " + tache.getNom() + "</p>" +
                "<p><strong>Description :</strong> " + tache.getDescription() + "</p>" +
                "<p>Veuillez confirmer :</p>" +
                "<a href=\"" + baseUrl + "/api/tasks/" + tache.getId() + "/accept\" " +
                "style=\"background:green; color:white; padding:10px; margin:5px;\">Oui</a>" +
                "<a href=\"" + baseUrl + "/api/tasks/" + tache.getId() + "/decline\" " +
                "style=\"background:red; color:white; padding:10px; margin:5px;\">Non</a>";
    }

    public void sendTaskAssignmentEmail(String to, Tache tache, String acceptUrl, String declineUrl) {
        logger.info("Début de l'envoi d'email - Configuration SMTP : {}:{}", mailSender.getHost(), mailSender.getPort());
        logger.info("Envoi à : {}", to);
        String subject = "Nouvelle tâche assignée : " + tache.getNom();
        
        String htmlContent = String.format("""
            <html>
            <body>
                <h2>Nouvelle tâche assignée</h2>
                <p>Vous avez été assigné à la tâche suivante :</p>
                <p><strong>Nom :</strong> %s</p>
                <p><strong>Description :</strong> %s</p>
                <p><strong>Date de début :</strong> %s</p>
                <p><strong>Date de fin :</strong> %s</p>
                <div style="margin-top: 20px;">
                    <p>Voulez-vous accepter cette tâche ?</p>
                    <a href="%s" style="background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; margin-right: 10px;">
                        Accepter
                    </a>
                    <a href="%s" style="background-color: #f44336; color: white; padding: 10px 20px; text-decoration: none;">
                        Refuser
                    </a>
                </div>
            </body>
            </html>
            """,
            tache.getNom(),
            tache.getDescription(),
            tache.getDateDebut(),
            tache.getDateFin(),
            acceptUrl,
            declineUrl
        );

        try {
            logger.info("Création du message email");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            logger.info("Tentative d'envoi de l'email...");
            mailSender.send(message);
            logger.info("Email envoyé avec succès");
        } catch (MessagingException e) {
            logger.error("Erreur détaillée lors de l'envoi de l'email", e);
            throw new RuntimeException("Erreur lors de l'envoi de l'email: " + e.getMessage(), e);
        }
    }

    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            
            mailSender.send(message);
            logger.info("Email envoyé avec succès à {}", to);
        } catch (MessagingException e) {
            logger.error("Erreur lors de l'envoi de l'email à {}", to, e);
            throw new RuntimeException("Erreur lors de l'envoi de l'email: " + e.getMessage(), e);
        }
    }

    public void sendTaskAcceptanceConfirmationEmail(String to, Tache tache) {
        logger.info("Envoi de l'email de confirmation d'acceptation à {}", to);
        String subject = "Confirmation d'acceptation de la tâche";
        String completeUrl = baseUrl + "/#/reponse/" + tache.getId() + "/done";
        
        String htmlContent = String.format("""
            <html>
            <body>
                <h2>Tâche acceptée avec succès</h2>
                <p>Vous avez accepté la tâche suivante :</p>
                <p><strong>Nom :</strong> %s</p>
                <p><strong>Description :</strong> %s</p>
                <p>Une fois la tâche terminée, cliquez sur le lien ci-dessous :</p>
                <p><a href="%s">Marquer comme terminée</a></p>
            </body>
            </html>
            """,
            tache.getNom(),
            tache.getDescription(),
            completeUrl
        );

        sendEmail(to, subject, htmlContent);
    }

    public void sendTaskCompletionEmail(String to, Tache tache) {
        logger.info("Envoi de l'email de confirmation de fin de tâche à {}", to);
        String subject = "Tâche terminée : " + tache.getNom();
        
        String htmlContent = String.format("""
            <html>
            <body>
                <h2>Tâche marquée comme terminée</h2>
                <p>La tâche suivante a été marquée comme terminée :</p>
                <p><strong>Nom :</strong> %s</p>
                <p><strong>Description :</strong> %s</p>
                <p><strong>Date de fin effective :</strong> %s</p>
                <p style="color: green;"><strong>Statut :</strong> TERMINEE</p>
            </body>
            </html>
            """,
            tache.getNom(),
            tache.getDescription(),
            tache.getDateFin()
        );

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            logger.info("Email de confirmation de fin de tâche envoyé avec succès");
        } catch (MessagingException e) {
            logger.error("Erreur lors de l'envoi de l'email de confirmation de fin de tâche", e);
            throw new RuntimeException("Erreur lors de l'envoi de l'email de confirmation: " + e.getMessage(), e);
        }
    }
}