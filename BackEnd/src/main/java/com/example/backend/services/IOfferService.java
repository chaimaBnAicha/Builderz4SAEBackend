package com.example.backend.services;

import com.example.backend.entities.Offer;

import java.util.List;

public interface IOfferService {


    Offer addOffer(Offer offer);
    void deleteOffer(int idOffer);
    Offer updateOffer(Offer offer);
    List<Offer> allOffers();
    Offer findOfferById(int idOffer);
    
}
