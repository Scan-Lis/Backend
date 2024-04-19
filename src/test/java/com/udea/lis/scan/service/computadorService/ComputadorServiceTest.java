package com.udea.lis.scan.service.computadorService;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.model.enums.EEstado;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.model.mapper.ComputadorMapper;
import com.udea.lis.scan.model.repository.ComputadorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ComputadorServiceTest {
    @Mock
    private ComputadorRepository computadorRepository;

    @Mock
    private ComputadorMapper computadorMapper;

    @InjectMocks
    private ComputadorService computadorService;

    @Test
    public void testGetComputadoresBySala_Success() {
        // Arrange
        String sala = "Sala1";
        ArrayList<Computador> computadores = new ArrayList<>();
        Computador computador = new Computador();
        computador.setId("Sala1Pc01");
        computador.setEstado("Encendido");
        computador.setSala("Sala1");
        computadores.add(computador);
        // Simulamos el comportamiento del repositorio para devolver la lista de computadores
        when(computadorRepository.findBySala(sala)).thenReturn(computadores);

        ArrayList<ComputadorDTO> computadoresDTO = new ArrayList<>();
        ComputadorDTO computadorDTO = new ComputadorDTO(1, EEstado.Funcionando, ESala.Sala1);
        computadoresDTO.add(computadorDTO);
        // Simulamos el comportamiento del mapper para devolver la lista de DTO
        when(computadorMapper.toComputadoresDTOList(computadores)).thenReturn(computadoresDTO);

        // Act
        Iterable<ComputadorDTO> result = computadorService.getComputadoresBySala(sala);
        // Assert
        assertNotNull(result);
    }
}