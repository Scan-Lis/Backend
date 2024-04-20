package com.udea.lis.scan.service.computadorservice;

import com.udea.lis.scan.error.ComputadorNotFoundException;
import com.udea.lis.scan.error.ComputadorOperationException;
import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.repository.ComputadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ComputadorServiceTest {

    @Mock
    private ComputadorRepository computadorRepository;

    @Mock
    private ComputadorMapper computadorMapper;

    @InjectMocks
    private ComputadorService computadorService;

    private String sala1;
    private String sala2;
    private ArrayList<Computador> computadores;
    private Computador computador;
    private ComputadorDTO computadorDTO;
    private ArrayList<ComputadorDTO> computadoresDTO;

    @BeforeEach
    void setUp() {
        sala1 = "Sala1";
        sala2 = "Sala2";
        computadores = new ArrayList<>();
        computador = new Computador();
        computador.setId("Sala1Pc01");
        computador.setEstado("Encendido");
        computador.setSala("Sala1");
        computadores.add(computador);
        computadorDTO = new ComputadorDTO(1, EEstado.Funcionando, ESala.Sala1);
        computadoresDTO = new ArrayList<>();
        computadoresDTO.add(computadorDTO);
    }
    @Test
    void testGetComputadoresBySala() {
        // Arrange
        when(computadorRepository.findBySala(sala1)).thenReturn(computadores);
        when(computadorRepository.findBySala(sala2)).thenReturn(new ArrayList<>());
        when(computadorMapper.toComputadoresDTOList(computadores)).thenReturn(computadoresDTO);
        // Act
        Iterable<ComputadorDTO> result = computadorService.getComputadoresBySala(sala1);
        // Assert
        assertNotNull(result);
        assertEquals(computadoresDTO, result);

        //Assert exception
        assertThrows(ComputadorNotFoundException.class, () -> computadorService.getComputadoresBySala(sala2));
    }

    @Test
    void getComputadores() {

        // Arrange
        when(computadorRepository.findAll()).thenReturn(computadores);
        when(computadorMapper.toComputadoresDTOList(computadores)).thenReturn(computadoresDTO);
        // Act
        Iterable<ComputadorDTO> result = computadorService.getComputadores();
        // Assert
        assertNotNull(result);
        assertEquals(computadoresDTO, result);

        //exception
        //arrange
        when(computadorRepository.findAll()).thenReturn(new ArrayList<>());
        //Assert and act
        assertThrows(ComputadorNotFoundException.class, () -> computadorService.getComputadores());
    }

    @Test
    void saveComputador() {
// Arrange
        when(computadorMapper.toComputador(computadorDTO)).thenReturn(computador);
        when(computadorRepository.save(computador)).thenReturn(computador);
        when(computadorRepository.existsById(computador.getId())).thenReturn(true);
        when(computadorMapper.toComputadorDTO(computador)).thenReturn(computadorDTO);
        // Act
        ComputadorDTO result = computadorService.saveComputador(computadorDTO);
        // Assert
        assertNotNull(result);
        assertEquals(computadorDTO, result);

        //exception
        //arrange
        when(computadorRepository.existsById(computador.getId())).thenReturn(false);
        //Assert and act
        assertThrows(ComputadorOperationException.class, () -> computadorService.saveComputador(computadorDTO));
    }

    @Test
    void deleteComputador() {
        int numeroPc = computadorDTO.getNumeroPc();
        //Arrange
        when(computadorRepository.existsById(computador.getId())).thenReturn(true);
        //Act
        computadorService.deleteComputador(computador.getSala(), numeroPc);

        //Assert
        //exception
        //arrange
        when(computadorRepository.existsById(computador.getId())).thenReturn(false);
        //Assert and act
        assertThrows(ComputadorNotFoundException.class, () -> computadorService.deleteComputador(computador.getSala(), numeroPc));

    }

    @Test
    void getComputadorByNumeroPcAndSala() {
        // Arrange
        when(computadorRepository.findById(computador.getId())).thenReturn(java.util.Optional.of(computador));
        when(computadorMapper.toComputadorDTO(computador)).thenReturn(computadorDTO);
        // Act
        ComputadorDTO result = computadorService.getComputadorByNumeroPcAndSala(computador.getSala(), computadorDTO.getNumeroPc());
        // Assert
        assertNotNull(result);
        assertEquals(computadorDTO, result);
        //exception
        //arrange
        when(computadorRepository.findById(computador.getId())).thenReturn(java.util.Optional.empty());
        //Assert and act
        assertThrows(ComputadorNotFoundException.class, () -> computadorService.getComputadorByNumeroPcAndSala(computador.getSala(), computadorDTO.getNumeroPc()));
    }
}