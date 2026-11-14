package com.uasz.ms_evenement.services;

import com.uasz.ms_evenement.entities.Evenement;
import java.util.List;
import java.util.Optional;

public interface IEvenementService {

    Evenement saveEvenement(Evenement evenement);

    List<Evenement> getAllEvenements();

    Optional<Evenement> getByIdEvenement(Long id);

    Evenement updateEvenement(Long id, Evenement newEvenement);

    void deleteEvenement(Long id);

    // Méthode spécifique pour ms-reservation :
    // retourne true si le nombre de places demandées est disponible
    boolean verifierDisponibilite(Long id, int nombrePlaces);
        // Réserve des places (retourne false si pas assez de places)
    boolean reserverPlaces(Long id, int nombrePlaces);

    // Libère des places (annulation / suppression d'une réservation)
    void libererPlaces(Long id, int nombrePlaces);
}