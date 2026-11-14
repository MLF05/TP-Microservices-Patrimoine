package com.uasz.ms_circuit.services;

import com.uasz.ms_circuit.entities.Circuit;
import java.util.List;
import java.util.Optional;

public interface ICircuitService {

    // CREATE
    Circuit saveCircuit(Circuit circuit);

    // READ ALL
    List<Circuit> getAllCircuits();

    // READ ONE
    Optional<Circuit> getByIdCircuit(Long id);

    // UPDATE
    Circuit updateCircuit(Long id, Circuit newCircuit);

    // DELETE
    void deleteCircuit(Long id);
}