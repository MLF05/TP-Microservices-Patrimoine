package com.uasz.ms_evenement.controllers;

import com.uasz.ms_evenement.entities.Evenement;
import com.uasz.ms_evenement.services.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")   // ← ajouté
@RestController
@RequestMapping("/api/evenements")
public class EvenementController {

    private EvenementService evenementService;

    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    // 1. POST
    @PostMapping
    public Evenement createEvenement(@RequestBody Evenement evenement) {
        return this.evenementService.saveEvenement(evenement);
    }

    // 2. GET all
    @GetMapping
    public List<Evenement> getAllEvenements() {
        return this.evenementService.getAllEvenements();
    }

    // 3. GET by id
    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getEvenementById(@PathVariable Long id) {
        return this.evenementService.getByIdEvenement(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. PUT
    @PutMapping("/{id}")
    public ResponseEntity<Evenement> updateEvenement(
            @PathVariable Long id,
            @RequestBody Evenement evenement) {
        try {
            Evenement updated = this.evenementService.updateEvenement(id, evenement);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        this.evenementService.deleteEvenement(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Endpoint supplémentaire pour ms-reservation
    @GetMapping("/{id}/disponibilite")
    public ResponseEntity<Boolean> verifierDisponibilite(
            @PathVariable Long id,
            @RequestParam int nombrePlaces) {
        boolean dispo = this.evenementService.verifierDisponibilite(id, nombrePlaces);
        return ResponseEntity.ok(dispo);
    }
        // 7. Réserver des places (appelé par ms-reservation)
    @PutMapping("/{id}/reserver")
    public ResponseEntity<Void> reserver(
            @PathVariable Long id,
            @RequestParam int nombrePlaces) {
        return this.evenementService.reserverPlaces(id, nombrePlaces)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // 8. Libérer des places (appelé par ms-reservation)
    @PutMapping("/{id}/liberer")
    public ResponseEntity<Void> liberer(
            @PathVariable Long id,
            @RequestParam int nombrePlaces) {
        this.evenementService.libererPlaces(id, nombrePlaces);
        return ResponseEntity.ok().build();
    }
    
}