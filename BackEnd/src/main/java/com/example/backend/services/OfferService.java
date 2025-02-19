package com.example.backend.services;

import com.example.backend.entities.Offer;
import com.example.backend.repositories.OfferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j

public class OfferService implements IOfferService {
    OfferRepository offerrepo;

    @Override
    public Offer addOffer(Offer offer) {
        return offerrepo.save(offer);
    }

    @Override
    public void deleteOffer(int idOffer) {
        offerrepo.deleteById(idOffer);

    }

    @Override
    public Offer updateOffer(Offer offer) {
        return offerrepo.save(offer);;
    }

    @Override
    public List<Offer> allOffers() {
        return offerrepo.findAll();
    }

    @Override
    public Offer findOfferById(int idOffer) {
        return offerrepo.findById(idOffer).get();
    }
}
