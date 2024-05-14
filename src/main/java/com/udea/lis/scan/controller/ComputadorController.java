package com.udea.lis.scan.controller;

import com.udea.lis.scan.error.ComputadorNotFoundException;
import com.udea.lis.scan.error.ComputadorOperationException;
import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.service.computadorservice.ComputadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/computador")
@AllArgsConstructor
public class ComputadorController {

    private ComputadorService computadorService;

    @Operation(summary = "Obtener los computadores de una sala", description = "Obtener una lista de computadores por el nombre de la sala", responses = {
            @ApiResponse(responseCode = "200", description = "lista de computadores")})
    @GetMapping("/sala/{sala}")
    public ResponseEntity<Page<ComputadorDTO>> getComputadoresBySala(@PathVariable ESala sala, Pageable pageable) {
        try {
            return ResponseEntity.ok(computadorService.getComputadoresBySala(sala.name(), pageable));
        } catch (ComputadorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "Obtener un computador", description = "Obtener un computador por el nombre de la  sala en la que se encuentra y el numero del pc", responses = {
            @ApiResponse(responseCode = "200", description = "Computador encontrado", content = @Content(schema = @Schema(implementation = ComputadorDTO.class ))),
            @ApiResponse(responseCode = "404", description = "Computador no encontrado", content = @Content(schema = @Schema(implementation = String.class))) })
    @GetMapping("/{sala}/{numeroPc}")
    public ResponseEntity<Object> getComputadorByNumeroPcAndSala(@PathVariable ESala sala, @PathVariable Integer numeroPc) {
        try {
            return ResponseEntity.ok(computadorService.getComputadorByNumeroPcAndSala(sala.name(), numeroPc));
        } catch (ComputadorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener la lista de todos los computadores", description = "Obtener una lista de todos los computadores existentes", responses = {
            @ApiResponse(responseCode = "200", description = "Computadores encontrados")})
    @GetMapping("/all")
    public ResponseEntity<Page<ComputadorDTO>> getComputadores(Pageable pageable) {
        try {
            return ResponseEntity.ok(computadorService.getComputadores(pageable));
        } catch (ComputadorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "guardar un computador", description = "Guardar un computador", responses = {
            @ApiResponse(responseCode = "200", description = "Computador creado", content = @Content(schema = @Schema(implementation = ComputadorDTO.class ))),
            @ApiResponse(responseCode = "400", description = "Sala, numero de pc o estado invalido", content = @Content(schema = @Schema(implementation = String.class))) })
    @PostMapping
    public ResponseEntity<Object> saveComputador(@RequestBody ComputadorDTO computadorDTO) {
        try {
            return ResponseEntity.ok(computadorService.saveComputador(computadorDTO));
        } catch (ComputadorOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un computador", description = "eliminar comptador a traves de la sala y el numero del computador", responses = {
            @ApiResponse(responseCode = "200", description = "Computador eliminado", content = @Content(schema = @Schema(implementation = String.class ))),
            @ApiResponse(responseCode = "404", description = "Computadores no encontrados", content = @Content(schema = @Schema(implementation = String.class))) })
    @DeleteMapping("/{sala}/{numeroPc}")
    public ResponseEntity<String> deleteComputador(@PathVariable ESala sala, @PathVariable Integer numeroPc) {
        try {
            computadorService.deleteComputador(sala.name(), numeroPc);
            return ResponseEntity.ok().build();
        } catch ( ComputadorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
