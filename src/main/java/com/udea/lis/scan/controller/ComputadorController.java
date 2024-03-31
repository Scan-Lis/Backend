package com.udea.lis.scan.controller;

import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.entity.Computador;
import com.udea.lis.scan.service.ComputadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/computador")
@AllArgsConstructor
public class ComputadorController {
    private final ComputadorService computadorService;
    @GetMapping("/sala/{sala}")
    public ResponseEntity<Iterable<ComputadorDTO>> getComputadoresBySala(String sala) {
        return ResponseEntity.ok(computadorService.getComputadoresBySala(sala));
    }
    @GetMapping("/all")
    public ResponseEntity<Iterable<ComputadorDTO>> getComputadores() {
        return ResponseEntity.ok(computadorService.getComputadores());
    }

    @PostMapping
    public ResponseEntity<ComputadorDTO> saveComputador(@RequestBody ComputadorDTO computadorDTO) {
        return ResponseEntity.ok(computadorService.saveComputador(computadorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ComputadorDTO> deleteComputador(@PathVariable Integer id) {
        if (computadorService.deleteComputador(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(computadorService.deleteComputador(id));
    }

}
