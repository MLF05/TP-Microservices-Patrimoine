package com.uasz.ms_reservation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomClient;
    private String typeCible;        // CIRCUIT ou EVENEMENT
    private Long referenceCible;     // id du circuit ou de l'événement
    private int nombrePlaces;
    private LocalDate dateReservation;
    private String statut;           // EN_ATTENTE, CONFIRMEE, ANNULEE
}