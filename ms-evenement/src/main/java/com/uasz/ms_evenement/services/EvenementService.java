package com.uasz.ms_evenement.services;

import com.uasz.ms_evenement.entities.Evenement;
import com.uasz.ms_evenement.repositories.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvenementService implements IEvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    @Override
    public Evenement saveEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }

    @Override
    public Optional<Evenement> getByIdEvenement(Long id) {
        return evenementRepository.findById(id);
    }

    @Override
    public Evenement updateEvenement(Long id, Evenement newEvenement) {
        return evenementRepository.findById(id).map(evenement -> {
            evenement.setNom(newEvenement.getNom());
            evenement.setDescription(newEvenement.getDescription());
            evenement.setDateDebut(newEvenement.getDateDebut());
            evenement.setDateFin(newEvenement.getDateFin());
            evenement.setHoraire(newEvenement.getHoraire());
            evenement.setLieu(newEvenement.getLieu());
            evenement.setCapaciteMaximale(newEvenement.getCapaciteMaximale());
            evenement.setPlacesReservees(newEvenement.getPlacesReservees());
            return evenementRepository.save(evenement);
        }).orElseThrow(() -> new RuntimeException("Evenement non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteEvenement(Long id) {
        evenementRepository.deleteById(id);
    }

    @Override
    public boolean verifierDisponibilite(Long id, int nombrePlaces) {
        return evenementRepository.findById(id).map(ev -> {
            int placesRestantes = ev.getCapaciteMaximale() - ev.getPlacesReservees();
            return placesRestantes >= nombrePlaces;
        }).orElse(false);
    }
        @Override
    public boolean reserverPlaces(Long id, int nombrePlaces) {
        return evenementRepository.findById(id).map(ev -> {
            if (ev.getCapaciteMaximale() - ev.getPlacesReservees() < nombrePlaces) {
                return false;
            }
            ev.setPlacesReservees(ev.getPlacesReservees() + nombrePlaces);
            evenementRepository.save(ev);
            return true;
        }).orElse(false);
    }

    @Override
    public void libererPlaces(Long id, int nombrePlaces) {
        evenementRepository.findById(id).ifPresent(ev -> {
            ev.setPlacesReservees(Math.max(0, ev.getPlacesReservees() - nombrePlaces));
            evenementRepository.save(ev);
        });
    }
}