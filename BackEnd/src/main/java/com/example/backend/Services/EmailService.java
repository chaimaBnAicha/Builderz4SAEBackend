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

    private static final String EMAIL_TEMPLATE = """
            <html>
            <head>
                <style>
                    body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 0; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: #2196F3; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }
                    .content { background: #fff; padding: 20px; border-radius: 0 0 5px 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
                    .task-info { background: #f5f5f5; padding: 15px; border-radius: 5px; margin: 15px 0; }
                    .button-container { margin: 20px 0; text-align: center; }
                    .button { display: inline-block; padding: 10px 20px; margin: 0 10px; border-radius: 5px; text-decoration: none; font-weight: bold; }
                    .accept-button { background: #4CAF50; color: white; }
                    .decline-button { background: #f44336; color: white; }
                    .footer { text-align: center; margin-top: 20px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">%s</div>
            </body>
            </html>
            """;

    private String buildEmailContent(Tache tache) {
        return String.format(EMAIL_TEMPLATE, """
                <div class="header">
                    <h1>📋 Nouvelle Tâche Assignée</h1>
                </div>
                <div class="content">
                    <div class="task-info">
                        <h2>📌 Détails de la tâche</h2>
                        <p><strong>Nom:</strong> %s</p>
                        <p><strong>Description:</strong> %s</p>
                    </div>
                    <div class="button-container">
                        <p>Veuillez confirmer votre participation :</p>
                        <a href="%s/api/tasks/%d/accept" class="button accept-button">✓ Accepter</a>
                        <a href="%s/api/tasks/%d/decline" class="button decline-button">✗ Refuser</a>
                    </div>
                    <div class="footer">
                        <p>Cet email a été envoyé automatiquement. Merci de ne pas y répondre.</p>
                    </div>
                </div>
                """,
                tache.getNom(),
                tache.getDescription(),
                baseUrl, tache.getId(),
                baseUrl, tache.getId()
        );
    }

    public void sendTaskAssignmentEmail(String to, Tache tache, String acceptUrl, String declineUrl) {
        logger.info("Début de l'envoi d'email - Configuration SMTP : {}:{}", mailSender.getHost(), mailSender.getPort());
        logger.info("Envoi à : {}", to);
        String subject = "📋 Nouvelle tâche assignée : " + tache.getNom();
        
        String htmlContent = String.format(EMAIL_TEMPLATE, String.format("""
                <div class="header">
                    <h1>📋 Nouvelle Tâche Assignée</h1>
                </div>
                <div class="content">
                    <div class="task-info">
                        <h2>📌 Détails de la tâche</h2>
                        <p><strong>Nom:</strong> %s</p>
                        <p><strong>Description:</strong> %s</p>
                        <p><strong>📅 Date de début:</strong> %s</p>
                        <p><strong>📅 Date de fin:</strong> %s</p>
                    </div>
                    <div class="button-container">
                        <p>Souhaitez-vous accepter cette tâche ?</p>
                        <a href="%s" class="button accept-button">✓ Accepter</a>
                        <a href="%s" class="button decline-button">✗ Refuser</a>
                    </div>
                    <div class="footer">
                        <p>Cet email a été envoyé automatiquement. Merci de ne pas y répondre.</p>
                    </div>
                </div>
                """,
                tache.getNom(),
                tache.getDescription(),
                tache.getDateDebut(),
                tache.getDateFin(),
                acceptUrl,
                declineUrl
        ));

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
        String subject = "✅ Confirmation d'acceptation de la tâche";
        String completeUrl = baseUrl + "/#/reponse/" + tache.getId() + "/done";

        String htmlContent = String.format(EMAIL_TEMPLATE, String.format("""
                <div class="header">
                    <h1>✅ Tâche Acceptée</h1>
                </div>
                <div class="content">
                    <div class="task-info">
                        <h2>📌 Détails de la tâche</h2>
                        <p><strong>Nom:</strong> %s</p>
                        <p><strong>Description:</strong> %s</p>
                    </div>
                    <div class="task-info" style="background-color: #e8f5e9; border-left: 4px solid #4CAF50;">
                        <p style="margin: 0;">👍 Vous avez accepté cette tâche avec succès!</p>
                    </div>
                    <div class="button-container">
                        <p>Une fois la tâche terminée, cliquez ci-dessous :</p>
                        <a href="%s" class="button accept-button">✓ Marquer comme terminée</a>
                    </div>
                    <div class="footer">
                        <p>Cet email a été envoyé automatiquement. Merci de ne pas y répondre.</p>
                    </div>
                </div>
                """,
                tache.getNom(),
                tache.getDescription(),
                completeUrl
        ));

        sendEmail(to, subject, htmlContent);
    }
    public void sendTaskCompletionEmail(String to, Tache tache) {
        logger.info("Envoi de l'email de confirmation de fin de tâche à {}", to);
        String subject = "✅ Tâche terminée : " + tache.getNom();

        String htmlContent = String.format(EMAIL_TEMPLATE, String.format("""
                <div class="header">
                    <h1>✅ Tâche Terminée</h1>
                </div>
                <div class="content">
                    <div class="task-info">
                        <h2>📌 Détails de la tâche</h2>
                        <p><strong>Nom:</strong> %s</p>
                        <p><strong>Description:</strong> %s</p>
                        <p><strong>📅 Date de fin effective:</strong> %s</p>
                        <p><strong style="color: #4CAF50;">Statut: TERMINÉE ✓</strong></p>
                    </div>
                    <div class="task-info" style="background-color: #e8f5e9; border-left: 4px solid #4CAF50;">
                        <p style="margin: 0;">🎉 Félicitations ! La tâche a été complétée avec succès.</p>
                    </div>
                    <div class="footer">
                        <p>Cet email a été envoyé automatiquement. Merci de ne pas y répondre.</p>
                    </div>
                </div>
                """,
                tache.getNom(),
                tache.getDescription(),
                tache.getDateFin()
        ));

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