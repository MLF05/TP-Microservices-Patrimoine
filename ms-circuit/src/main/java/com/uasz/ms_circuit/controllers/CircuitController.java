package com.uasz.ms_circuit.controllers;

import com.uasz.ms_circuit.entities.Circuit;
import com.uasz.ms_circuit.services.CircuitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")   // ← ajouté
@RestController
@RequestMapping("/api/circuits")
public class CircuitController {

    private CircuitService circuitService;

    public CircuitController(CircuitService circuitService) {
        this.circuitService = circuitService;
    }

    // 1. POST
    @PostMapping
    public Circuit createCircuit(@RequestBody Circuit circuit) {
        return this.circuitService.saveCircuit(circuit);
    }

    // 2. GET all
    @GetMapping
    public List<Circuit> getAllCircuits() {
        return this.circuitService.getAllCircuits();
    }

    // 3. GET by id
    @GetMapping("/{id}")
    public ResponseEntity<Circuit> getCircuitById(@PathVariable Long id) {
        return this.circuitService.getByIdCircuit(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. PUT
    @PutMapping("/{id}")
    public ResponseEntity<Circuit> updateCircuit(
            @PathVariable Long id,
            @RequestBody Circuit circuit) {
        try {
            Circuit updated = this.circuitService.updateCircuit(id, circuit);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCircuit(@PathVariable Long id) {
        this.circuitService.deleteCircuit(id);
        return ResponseEntity.noContent().build();
    }
}