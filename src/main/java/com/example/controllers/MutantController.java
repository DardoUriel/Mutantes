package com.example.controllers;

import com.example.entities.Dto.MutantDto;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.services.Implements.MutantServiceImplements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/mutant")
@RequiredArgsConstructor
public class MutantController {
    @Autowired
    private final MutantServiceImplements mutantService;


    @PostMapping("")
    public ResponseEntity<?> sendDna(@RequestBody MutantDto mutantDto) {
        try {
            // Verificar que el ADN está presente en el DTO
            if (mutantDto.getAdn() == null || mutantDto.getAdn().length == 0) {
                return ResponseEntity.badRequest().body("El ADN no puede estar vacío.");
            }

            // Lógica para verificar si es un mutante
            boolean isMutant = mutantService.isMutant(mutantDto.getAdn());
            mutantDto.setIsMutant(isMutant); // Asegúrate de que tienes un setter para isMutant
            mutantService.Save(mutantDto);

            // Mensaje de respuesta basado en el resultado
            String message = isMutant
                    ? "El ADN corresponde a un mutante."
                    : "El ADN NO corresponde a un mutante.";
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable UUID id) {
        // Aquí podrías recuperar un registro específico usando el ID
        return ResponseEntity.ok().body(id);
    }
}
