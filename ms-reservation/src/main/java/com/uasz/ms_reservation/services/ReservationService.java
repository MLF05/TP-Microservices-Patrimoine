package com.uasz.ms_reservation.services;

import com.uasz.ms_reservation.entities.Reservation;
import com.uasz.ms_reservation.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    // URLs logiques des autres microservices (résolues par Eureka)
    private static final String CIRCUIT_URL = "http://ms-circuit/api/circuits/";
    private static final String EVENEMENT_URL = "http://ms-evenement/api/evenements/";

    @Override
    public Reservation creerReservation(Reservation reservation) {

        if (!"CIRCUIT".equals(reservation.getTypeCible())
                && !"EVENEMENT".equals(reservation.getTypeCible())) {
            throw new RuntimeException("typeCible doit être CIRCUIT ou EVENEMENT");
        }

        boolean dispo = verifierDisponibilite(
                reservation.getTypeCible(),
                reservation.getReferenceCible(),
                reservation.getNombrePlaces()
        );

        if (!dispo) {
            throw new RuntimeException("Pas assez de places disponibles");
        }

        if ("EVENEMENT".equals(reservation.getTypeCible())) {
            try {
                restTemplate.put(EVENEMENT_URL + reservation.getReferenceCible()
                        + "/reserver?nombrePlaces=" + reservation.getNombrePlaces(), null);
            } catch (Exception e) {
                throw new RuntimeException("Pas assez de places disponibles");
            }
        }

        reservation.setDateReservation(LocalDate.now());
        reservation.setStatut("EN_ATTENTE");
        return reservationRepository.save(reservation);
    }

    private boolean verifierDisponibilite(String typeCible, Long id, int nombrePlaces) {
        try {
            if ("EVENEMENT".equals(typeCible)) {
                String url = EVENEMENT_URL + id + "/disponibilite?nombrePlaces=" + nombrePlaces;
                Boolean dispo = restTemplate.getForObject(url, Boolean.class);
                return Boolean.TRUE.equals(dispo);
            } else if ("CIRCUIT".equals(typeCible)) {
                restTemplate.getForObject(CIRCUIT_URL + id, Object.class);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private void libererPlaces(Reservation r) {
        if ("EVENEMENT".equals(r.getTypeCible()) && !"ANNULEE".equals(r.getStatut())) {
            try {
                restTemplate.put(EVENEMENT_URL + r.getReferenceCible()
                        + "/liberer?nombrePlaces=" + r.getNombrePlaces(), null);
            } catch (Exception e) {
                // ms-evenement injoignable : on n'empêche pas l'opération
            }
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> getByIdReservation(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Reservation confirmerReservation(Long id) {
        return reservationRepository.findById(id).map(r -> {
            r.setStatut("CONFIRMEE");
            return reservationRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Réservation introuvable : " + id));
    }

    @Override
    public Reservation annulerReservation(Long id) {
        return reservationRepository.findById(id).map(r -> {
            libererPlaces(r);
            r.setStatut("ANNULEE");
            return reservationRepository.save(r);
        }).orElseThrow(() -> new RuntimeException("Réservation introuvable : " + id));
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.findById(id).ifPresent(this::libererPlaces);
        reservationRepository.deleteById(id);
    }
}