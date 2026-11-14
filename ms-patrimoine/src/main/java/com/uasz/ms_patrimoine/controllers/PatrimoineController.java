package com.uasz.ms_patrimoine.controllers;

import com.uasz.ms_patrimoine.entities.Patrimoine;
import com.uasz.ms_patrimoine.services.PatrimoineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;   // ← ajouté

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")   // ← ajouté
@RestController
@RequestMapping("/api/patrimoines")
public class PatrimoineController {

    private PatrimoineService patrimoineService;

    // Injection via le constructeur
    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    // 1. Ajouter un patrimoine (POST)
    @PostMapping
    public Patrimoine createPatrimoine(@RequestBody Patrimoine patrimoine) {
        return this.patrimoineService.savePatrimoine(patrimoine);
    }

    // 2. Récupérer tous les patrimoines (GET)
    @GetMapping
    public List<Patrimoine> getAllPatrimoines() {
        return this.patrimoineService.getAllPatrimoines();
    }

    // 3. Récupérer un patrimoine par son ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoineById(@PathVariable Long id) {
        return this.patrimoineService.getByIdPatrimoine(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Mettre à jour un patrimoine (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> updatePatrimoine(
            @PathVariable Long id,
            @RequestBody Patrimoine patrimoine) {
        try {
            Patrimoine updated = this.patrimoineService.updatePatrimoine(id, patrimoine);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Supprimer un patrimoine (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatrimoine(@PathVariable Long id) {
        this.patrimoineService.deletePatrimoine(id);
        return ResponseEntity.noContent().build();
    }
}