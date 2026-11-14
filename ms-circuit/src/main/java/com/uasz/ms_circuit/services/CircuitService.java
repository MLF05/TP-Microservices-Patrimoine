package com.uasz.ms_circuit.services;

import com.uasz.ms_circuit.entities.Circuit;
import com.uasz.ms_circuit.repositories.CircuitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CircuitService implements ICircuitService {

    @Autowired
    private CircuitRepository circuitRepository;

    @Override
    public Circuit saveCircuit(Circuit circuit) {
        return circuitRepository.save(circuit);
    }

    @Override
    public List<Circuit> getAllCircuits() {
        return circuitRepository.findAll();
    }

    @Override
    public Optional<Circuit> getByIdCircuit(Long id) {
        return circuitRepository.findById(id);
    }

    @Override
    public Circuit updateCircuit(Long id, Circuit newCircuit) {
        return circuitRepository.findById(id).map(circuit -> {
            circuit.setNom(newCircuit.getNom());
            circuit.setDescription(newCircuit.getDescription());
            circuit.setDureeEstimee(newCircuit.getDureeEstimee());
            circuit.setDifficulte(newCircuit.getDifficulte());
            circuit.setDistance(newCircuit.getDistance());
            circuit.setEtapes(newCircuit.getEtapes());
            circuit.setPointsInteret(newCircuit.getPointsInteret());
            return circuitRepository.save(circuit);
        }).orElseThrow(() -> new RuntimeException("Circuit non trouvé avec l'id : " + id));
    }

    @Override
    public void deleteCircuit(Long id) {
        circuitRepository.deleteById(id);
    }
}