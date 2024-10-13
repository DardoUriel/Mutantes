package com.example;

import com.example.entities.Dto.MutantDto;
import com.example.entities.Mutant;
import com.example.repositories.MutantRepository;
import com.example.services.Implements.MutantServiceImplements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MutantServiceImplementsTests {

	@Mock
	private MutantRepository mutantRepository;  // Mock del repositorio

	@InjectMocks
	private MutantServiceImplements mutantService;  // Inyectar el mock en el servicio

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);  // Inicializar los mocks
	}

	@Test
	void testIsMutantWithValidDna() {
		String[] dna = {
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TCACTG"
		};
		boolean result = mutantService.isMutant(dna);
		assertTrue(result, "El ADN debería ser considerado mutante");
	}

	@Test
	void testIsMutantWithInvalidDna() {
		String[] dna = {
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCTTA",
				"TCACTG"
		};
		boolean result = mutantService.isMutant(dna);
		assertFalse(result, "El ADN no debería ser considerado mutante");
	}

	@Test
	void testSaveMutant() {
		MutantDto dto = new MutantDto();
		dto.setName("Mutante 1");
		dto.setIsMutant(true);

		mutantService.Save(dto);

		// Verificar que el repositorio fue llamado con el objeto correcto
		verify(mutantRepository, times(1)).save(any(Mutant.class));
	}

	@Test
	void testGetAdnReturnsNull() {
		MutantDto result = mutantService.getAdn();
		assertNull(result, "getAdn() debería devolver null en esta implementación");
	}

	@Test
	void testValidateDnaArrayThrowsExceptionForNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			mutantService.isMutant(null);
		});
		assertEquals("El array de ADN no debe ser nulo o vacío", exception.getMessage());
	}

	@Test
	void testValidateDnaArrayThrowsExceptionForEmptyArray() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			mutantService.isMutant(new String[]{});
		});
		assertEquals("El array de ADN no debe ser nulo o vacío", exception.getMessage());
	}

	@Test
	void testValidateDnaArrayThrowsExceptionForInvalidCharacters() {
		String[] dna = {
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TC1CTG" // Contiene un carácter no permitido '1'
		};
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			mutantService.isMutant(dna);
		});
		assertEquals("El ADN debe ser una matriz NxN con caracteres válidos (A, C, T, G)", exception.getMessage());
	}
}
