package com.example.backend.controllers;

import com.example.backend.entities.Request;
import com.example.backend.entities.Statut;
import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ProjectRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProjectRequestController {

    @Autowired
    private ProjectRequestService projectRequestService;

    @Autowired
    private UserRepository userRepository;

    // Créer une demande de projet avec l'ID statique du client (1)
    @PostMapping("/RequestsPost")
    public ResponseEntity<Request> createProjectRequest(@Valid @RequestBody Request projectRequest) {
        // Récupérer l'utilisateur avec l'ID statique (1)
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : 1"));

        // Associer l'utilisateur à la demande
        projectRequest.setUser(user);

        // Sauvegarder la demande
        Request savedRequest = projectRequestService.createProjectRequest(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    // Récupérer toutes les demandes
    @GetMapping("/AllRequest")
    public List<Request> getAllRequest() {
        return projectRequestService.getAllRequests();
    }

    // Récupérer une demande par son ID
    @GetMapping("/request/{id_projet}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id_projet) {
        Request request = projectRequestService.getRequestById(id_projet);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request);
    }
    // 🔹 Filtrer les demandes par statut
    @GetMapping("/requests/status/{status}")
    public List<Request> getRequestsByStatus(@PathVariable Statut status) {
        return projectRequestService.getRequestsByStatus(status);
    }
    // 🔹 Rechercher une demande par nom du projet
    @GetMapping("/requests/search")
    public List<Request> searchRequests(@RequestParam String query) {
        return projectRequestService.searchRequests(query);
    }

    // Mettre à jour une demande
    @PutMapping("/request/{id_project}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id_project, @Valid @RequestBody Request request) {
        Request existingRequest = projectRequestService.getRequestById(id_project);
        if (existingRequest == null) {
            return ResponseEntity.notFound().build();
        }

        // Mettre à jour les champs
        existingRequest.setProjectName(request.getProjectName());
        existingRequest.setDescription(request.getDescription());
        existingRequest.setEstimated_budget(request.getEstimated_budget());
        existingRequest.setEstimated_duration(request.getEstimated_duration());
        existingRequest.setGeographic_location(request.getGeographic_location());

        // Sauvegarder les modifications
        Request updatedRequest = projectRequestService.updateRequest(existingRequest);
        return ResponseEntity.ok(updatedRequest);
    }

    // 🔹 Approuver une demande
    @PutMapping("/request/{id}/approve")
    public ResponseEntity<Request> approveRequest(@PathVariable Long id) {
        Request updatedRequest = projectRequestService.approveRequest(id);
        return (updatedRequest != null) ? ResponseEntity.ok(updatedRequest) : ResponseEntity.notFound().build();
    }
    // 🔹 Rejeter une demande
    @PutMapping("/request/{id}/reject")
    public ResponseEntity<Request> rejectRequest(@PathVariable Long id) {
        Request updatedRequest = projectRequestService.rejectRequest(id);
        return (updatedRequest != null) ? ResponseEntity.ok(updatedRequest) : ResponseEntity.notFound().build();
    }


    // Supprimer une demande
    @DeleteMapping("/request/{id_project}")
    public ResponseEntity<?> deleteRequest(@PathVariable Long id_project) {
        Request existingRequest = projectRequestService.getRequestById(id_project);
        if (existingRequest == null) {
            return ResponseEntity.notFound().build();
        }
        projectRequestService.deleteRequest(id_project);
        return ResponseEntity.ok().build();
    }
}