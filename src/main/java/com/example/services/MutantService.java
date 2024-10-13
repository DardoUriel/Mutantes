package com.example.services;

import com.example.entities.Dto.MutantDto;

public interface MutantService {

    boolean isMutant(String[] dna);

    MutantDto getAdn();

    void Save(MutantDto dto);
}
