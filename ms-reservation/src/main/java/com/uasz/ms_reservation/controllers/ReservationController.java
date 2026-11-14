package com.uasz.ms_reservation.controllers;

import com.uasz.ms_reservation.entities.Reservation;
import com.uasz.ms_reservation.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")   // ← ajouté
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 1. POST — créer une réservation (vérifie la dispo)
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation saved = this.reservationService.creerReservation(reservation);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. GET all
    @GetMapping
    public List<Reservation> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    // 3. GET by id
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return this.reservationService.getByIdReservation(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. PUT /confirmer
    @PutMapping("/{id}/confirmer")
    public ResponseEntity<Reservation> confirmer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.reservationService.confirmerReservation(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. PUT /annuler
    @PutMapping("/{id}/annuler")
    public ResponseEntity<Reservation> annuler(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.reservationService.annulerReservation(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        this.reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}