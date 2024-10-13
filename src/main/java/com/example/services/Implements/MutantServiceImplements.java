package com.example.services.Implements;

import com.example.entities.Dto.MutantDto;
import com.example.entities.Mutant;
import com.example.repositories.MutantRepository;
import com.example.services.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MutantServiceImplements implements MutantService {

    private final MutantRepository mutantRepository;

    @Autowired
    public MutantServiceImplements(MutantRepository mutantRepository) {
        this.mutantRepository = mutantRepository;
    }

    @Override
    public boolean isMutant(String[] dna) {
        validateDnaArray(dna);
        return detectMutantSequences(dna);
    }

    @Override
    public void Save(MutantDto dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Mutant mutant = Mutant.builder()
                .name(dto.getName())
                .isMutant(dto.isMutant())
                .created_at(timestamp)
                .updated_at(timestamp)
                .build();
        mutantRepository.save(mutant);
    }

    @Override
    public MutantDto getAdn() {
        // Lógica adicional si se necesita devolver un MutantDto desde la BD
        return null;
    }

    private boolean detectMutantSequences(String[] dna) {
        int n = dna.length;
        int marcoSkip = 4;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Horizontal
                if (j <= n - marcoSkip && checkSequence(dna, i, j, 0, 1)) {
                    return true;
                }
                // Vertical
                if (i <= n - marcoSkip && checkSequence(dna, i, j, 1, 0)) {
                    return true;
                }
                // Diagonal ↘
                if (i <= n - marcoSkip && j <= n - marcoSkip && checkSequence(dna, i, j, 1, 1)) {
                    return true;
                }
                // Diagonal inversa ↙
                if (i <= n - marcoSkip && j >= marcoSkip - 1 && checkSequence(dna, i, j, 1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSequence(String[] dna, int row, int col, int rowDir, int colDir) {
        char initial = dna[row].charAt(col);
        int count = 0;
        for (int i = 1; i < 4; i++) {
            if (dna[row + i * rowDir].charAt(col + i * colDir) == initial) {
                count++;
            } else {
                return false;
            }
        }
        return count >= 3;
    }

    private void validateDnaArray(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El array de ADN no debe ser nulo o vacío");
        }
        int n = dna.length;
        for (String fila : dna) {
            if (fila == null || fila.length() != n || !fila.matches("[ACTG]+")) {
                throw new IllegalArgumentException("El ADN debe ser una matriz NxN con caracteres válidos (A, C, T, G)");
            }
        }
    }
}
