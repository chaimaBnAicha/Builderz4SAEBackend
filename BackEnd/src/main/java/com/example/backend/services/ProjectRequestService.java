package com.example.backend.services;

import com.example.backend.entities.Request;
import com.example.backend.entities.Statut;
import com.example.backend.repositories.ProjectRequestRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectRequestService {

    @Autowired
    private ProjectRequestRepository projectRequestRepository;

    @Autowired
    private EmailService emailService;

    public List<Request> getAllRequests() {
        return projectRequestRepository.findAll();
    }

    public Request getRequestById(Long id_projet) {
        return projectRequestRepository.findById(id_projet).orElse(null);
    }
    public List<Request> getRequestsByStatus(Statut status) {
        return projectRequestRepository.findByStatus(status);
    }

    public Request createProjectRequest(Request projectRequest) {
        Request savedRequest = projectRequestRepository.save(projectRequest);

        // Récupérer l'email du client
        String clientEmail = savedRequest.getUser().getEmail();

        // Envoyer l'email de confirmation
        try {
            emailService.sendProjectRequestEmail(
                    clientEmail,
                    savedRequest.getProjectName(),
                    savedRequest.getEstimated_budget(),
                    savedRequest.getEstimated_duration(),
                    savedRequest.getGeographic_location()
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return savedRequest;
    }

    public Request updateRequest(Request request) {
        return projectRequestRepository.save(request);
    }

    public void deleteRequest(Long id_project) {
        projectRequestRepository.deleteById(id_project);
    }
    public List<Request> searchRequests(String query) {
        return projectRequestRepository.findByProjectNameContainingIgnoreCase(query);
    }
    public Request approveRequest(Long id) {
        Optional<Request> request = projectRequestRepository.findById(id);
        if (request.isPresent()) {
            request.get().setStatus(Statut.Approved);
            return projectRequestRepository.save(request.get());
        }
        return null;
    }
    public Request rejectRequest(Long id) {
        Optional<Request> request = projectRequestRepository.findById(id);
        if (request.isPresent()) {
            request.get().setStatus(Statut.Rejected);
            return projectRequestRepository.save(request.get());
        }
        return null;
    }
}
