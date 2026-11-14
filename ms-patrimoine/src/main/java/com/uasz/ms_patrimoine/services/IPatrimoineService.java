package com.uasz.ms_patrimoine.services;

import com.uasz.ms_patrimoine.entities.Patrimoine;
import java.util.List;
import java.util.Optional;

public interface IPatrimoineService {

    // CREATE
    Patrimoine savePatrimoine(Patrimoine patrimoine);

    // READ ALL
    List<Patrimoine> getAllPatrimoines();

    // READ ONE
    Optional<Patrimoine> getByIdPatrimoine(Long id);

    // UPDATE
    Patrimoine updatePatrimoine(Long id, Patrimoine newPatrimoine);

    // DELETE
    void deletePatrimoine(Long id);
}