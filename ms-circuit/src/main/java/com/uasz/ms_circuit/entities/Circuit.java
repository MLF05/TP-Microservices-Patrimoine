package com.uasz.ms_circuit.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Circuit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private int dureeEstimee;      // en minutes
    private String difficulte;     // FACILE, MOYEN, DIFFICILE
    private double distance;       // en km
    private String etapes;         // ex: "Musée → Monument → Parc"
    private String pointsInteret;  // ex: "Histoire, Nature"
}