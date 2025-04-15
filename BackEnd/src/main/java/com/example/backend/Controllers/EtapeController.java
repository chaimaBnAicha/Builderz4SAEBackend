package com.example.backend.Controllers;

import com.example.backend.Entities.Etape;
import com.example.backend.Services.EtapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etapes")
public class EtapeController {

    @Autowired
    private EtapeService etapeService;

    @PostMapping("/{tacheId}/ajouter")
    public Etape ajouterEtape(@PathVariable Long tacheId, @RequestBody Etape etape) {
        // Set the tacheId in the etape object
      
        return etapeService.ajouterEtape(tacheId, etape);
    }

    @GetMapping("/{tacheId}/get")
    public List<Etape> getEtapesByTache(@PathVariable Long tacheId) {
        return etapeService.getEtapesByTache(tacheId);
    }

    @GetMapping("/all")
    public List<Etape> getAllEtapes() {
        return etapeService.getAllEtapes();
    }

    @PutMapping("/{etapeId}/modifier")
    public Etape modifierEtape(@PathVariable Long etapeId, @RequestBody Etape etapeDetails) {
        System.out.println("Updating etape with ID: " + etapeId);
        System.out.println("Received data: " + etapeDetails);
        return etapeService.modifierEtape(etapeId, etapeDetails);
    }

    @DeleteMapping("/{etapeId}/supprimer")
    public String supprimerEtape(@PathVariable Long etapeId) {
        etapeService.supprimerEtape(etapeId);
        return "Étape avec ID " + etapeId + " supprimée avec succès.";
    }

    @GetMapping("/{id}")
        public Etape getEtapeById(@PathVariable Long id) {
            return etapeService.getEtapeById(id);
        }


    @GetMapping("/tache/{tacheId}")
    public ResponseEntity<List<Etape>> getEtapesByTacheId(@PathVariable Long tacheId) {
        List<Etape> etapes = etapeService.findByTacheId(tacheId);
        return ResponseEntity.ok(etapes);
    }

}
