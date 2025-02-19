package com.example.backend.controllers;

import com.example.backend.entities.Offer;
import com.example.backend.services.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/offer")
public class OfferController {
    IOfferService offerService;

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

    @PostMapping("/add-Offer")
    public Offer addOffer(@RequestBody Offer a) {
        Offer Offer = offerService.addOffer(a);
        return Offer;
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
