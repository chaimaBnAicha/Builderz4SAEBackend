package com.example.backend.controllers;

import com.example.backend.entities.Offer;
import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.EmailService;
import com.example.backend.services.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.backend.entities.User;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/offer")
@CrossOrigin(origins = "http://localhost:4200")
public class OfferController {
    IOfferService offerService;
EmailService emailService;
UserRepository userRepository;
    @GetMapping("/retrieve-all-Offers")
    public List<Offer> getOffers() {
        List<Offer> listOffers = offerService.allOffers();
        return listOffers;
    }

    @GetMapping("/retrieve-Offer/{Offer-id}")
    public Offer retrieveOffer(@PathVariable("Offer-id") int Id) {
        Offer Offer = offerService.findOfferById(Id);
        return Offer;
    }
/*
    @PostMapping("/add-Offer")
    public Offer addOffer(@RequestBody Offer a) {
        Offer Offer = offerService.addOffer(a);
        try {
        emailService.sendOfferNotification(saveOffer.getuser.getEmail());

        }
        return Offer;
    }*/


    @PostMapping("/add-Offer")
    public Offer addOffer(@RequestBody Offer a) {
        int userId = (a.getUser() != null && a.getUser().getId() != 0) ? a.getUser().getId() : 1;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

        a.setUser(user);

        Offer savedOffer = offerService.addOffer(a);

        try {
            String userEmail = savedOffer.getUser().getEmail();
            emailService.sendOfferNotification(userEmail, savedOffer);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }

        return savedOffer;
    }


    @DeleteMapping("/remove-Offer/{Offer-id}")
    public void removeOffer(@PathVariable("Offer-id") int Id) {
        offerService.deleteOffer(Id);
    }
    @PutMapping("/modify-Offer")
    public Offer modifyOffer(@RequestBody Offer a) {
        Offer Offer = offerService.updateOffer(a);
        return Offer;
    }
    
    
    
}
