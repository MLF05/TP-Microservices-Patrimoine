package com.uasz.ms_patrimoine.services;

import com.uasz.ms_patrimoine.entities.Patrimoine;
import com.uasz.ms_patrimoine.repositories.PatrimoineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatrimoineService implements IPatrimoineService {

    @Autowired
    private PatrimoineRepository patrimoineRepository;

    // CREATE
    @Override
    public Patrimoine savePatrimoine(Patrimoine patrimoine) {
        return patrimoineRepository.save(patrimoine);
    }

    // READ ALL
    @Override
    public List<Patrimoine> getAllPatrimoines() {
        return patrimoineRepository.findAll();
    }

    // READ ONE
    @Override
    public Optional<Patrimoine> getByIdPatrimoine(Long id) {
        return patrimoineRepository.findById(id);
    }

    // UPDATE
    @Override
    public Patrimoine updatePatrimoine(Long id, Patrimoine newPatrimoine) {
        return patrimoineRepository.findById(id).map(patrimoine -> {
            patrimoine.setNom(newPatrimoine.getNom());
            patrimoine.setCode(newPatrimoine.getCode());
            patrimoine.setDescription(newPatrimoine.getDescription());
            patrimoine.setHistorique(newPatrimoine.getHistorique());
            patrimoine.setAdresse(newPatrimoine.getAdresse());
            patrimoine.setCapacite(newPatrimoine.getCapacite());
            return patrimoineRepository.save(patrimoine);
        }).orElseThrow(() -> new RuntimeException("Patrimoine non trouvé avec l'id : " + id));
    }

    // DELETE
    @Override
    public void deletePatrimoine(Long id) {
        patrimoineRepository.deleteById(id);
    }
}