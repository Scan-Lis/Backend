package com.udea.lis.scan.controller;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.service.computadorservice.IComputadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class ComputadorControllerTest {

    @Mock
    private IComputadorService computadorService;

    @InjectMocks

    private ComputadorController computadorController;
    private Iterable<ComputadorDTO> computadorDTOList;
    private ComputadorDTO computadorDTO;

    @BeforeEach
    void setUp() {
        computadorDTO = new ComputadorDTO();
        computadorDTO.setSala(ESala.Sala1);
        computadorDTO.setNumeroPc(1);
        computadorDTO.setEstado(EEstado.Funcionando);
        computadorDTOList = new ArrayList<>();
        ((ArrayList<ComputadorDTO>) computadorDTOList).add(computadorDTO);

    }

    @Test
    void getComputadoresBySala() {
        // Arrange
        ESala sala = ESala.Sala1;
        when(computadorService.getComputadoresBySala(sala.name())).thenReturn(computadorDTOList);
        // Act
        ResponseEntity<Iterable<ComputadorDTO>> computadores = computadorController.getComputadoresBySala(sala);

        // Assert
        assertEquals(computadores.getStatusCode(), ResponseEntity.ok().build().getStatusCode());
        assertEquals(computadorDTOList, computadores.getBody());

        // Arrange error
        when(computadorService.getComputadoresBySala(sala.name())).thenThrow(new RuntimeException());
        // act
        computadores = computadorController.getComputadoresBySala(sala);

        // Assert
        assertEquals(computadores.getStatusCode(), ResponseEntity.notFound().build().getStatusCode());

    }

    @Test
    void getComputadorByNumeroPcAndSala() {
// Arrange
        ESala sala = ESala.Sala1;
        Integer numeroPc = 1;
        when(computadorService.getComputadorByNumeroPcAndSala(sala.name(), numeroPc)).thenReturn(computadorDTO);
        // Act
        ResponseEntity<ComputadorDTO> computador = computadorController.getComputadorByNumeroPcAndSala(sala, numeroPc);

        // Assert
        assertEquals(computador.getStatusCode(), ResponseEntity.ok().build().getStatusCode());
        assertEquals(computadorDTO, computador.getBody());

        // Arrange error
        when(computadorService.getComputadorByNumeroPcAndSala(sala.name(), numeroPc)).thenThrow(new RuntimeException());
        // act
        computador = computadorController.getComputadorByNumeroPcAndSala(sala, numeroPc);

        // Assert
        assertEquals(computador.getStatusCode(), ResponseEntity.notFound().build().getStatusCode());
    }

    @Test
    void getComputadores() {
        // Arrange
        when(computadorService.getComputadores()).thenReturn(computadorDTOList);
        // Act
        ResponseEntity<Iterable<ComputadorDTO>> computadores = computadorController.getComputadores();

        // Assert
        assertEquals(computadores.getStatusCode(), ResponseEntity.ok().build().getStatusCode());
        assertEquals(computadorDTOList, computadores.getBody());

        // Arrange error
        when(computadorService.getComputadores()).thenThrow(new RuntimeException());
        // act
        computadores = computadorController.getComputadores();

        // Assert
        assertEquals(computadores.getStatusCode(), ResponseEntity.notFound().build().getStatusCode());
    }

    @Test
    void saveComputador() {
        // Arrange
        when(computadorService.saveComputador(computadorDTO)).thenReturn(computadorDTO);
        // Act
        ResponseEntity<ComputadorDTO> computador = computadorController.saveComputador(computadorDTO);

        // Assert
        assertEquals(computador.getStatusCode(), ResponseEntity.ok().build().getStatusCode());
        assertEquals(computadorDTO, computador.getBody());

        // Arrange error
        when(computadorService.saveComputador(computadorDTO)).thenThrow(new RuntimeException());
        // act
        computador = computadorController.saveComputador(computadorDTO);

        // Assert
        assertEquals(computador.getStatusCode(), ResponseEntity.badRequest().build().getStatusCode());
    }

    @Test
    void deleteComputador() {
        // Arrange
        ESala sala = ESala.Sala1;
        Integer numeroPc = 1;
        // Act
        ResponseEntity<String> computador = computadorController.deleteComputador(sala, numeroPc);
        // Assert
        assertEquals(computador.getStatusCode(), ResponseEntity.ok().build().getStatusCode());

    }
}