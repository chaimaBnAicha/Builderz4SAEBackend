package com.example.backend.controllers;

import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Récupérer les informations du client avec l'ID statique (1)
    @GetMapping("/1")
    public ResponseEntity<User> getUserById() {
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : 1"));
        return ResponseEntity.ok(user);
    }
}