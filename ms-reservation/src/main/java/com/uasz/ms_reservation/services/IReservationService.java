package com.uasz.ms_reservation.services;

import com.uasz.ms_reservation.entities.Reservation;
import java.util.List;
import java.util.Optional;

public interface IReservationService {

    Reservation creerReservation(Reservation reservation);

    List<Reservation> getAllReservations();

    Optional<Reservation> getByIdReservation(Long id);

    Reservation confirmerReservation(Long id);

    Reservation annulerReservation(Long id);

    void deleteReservation(Long id);
}