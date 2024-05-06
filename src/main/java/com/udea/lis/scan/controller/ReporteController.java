package com.udea.lis.scan.controller;

import com.udea.lis.scan.error.ComputadorNotFoundException;
import com.udea.lis.scan.error.ReporteNotFoundException;
import com.udea.lis.scan.error.ReporteOperationException;
import com.udea.lis.scan.model.dto.ComputadorDTO;
import com.udea.lis.scan.model.dto.ReporteDTO;
import com.udea.lis.scan.model.enums.EReporte;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.service.reporteservice.ReporteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reporte")
@AllArgsConstructor
public class ReporteController {

    private ReporteServiceImpl reporteService;


    @Operation(summary = "Guardar un reporte", description = "Guardar un reporte", responses = {
            @ApiResponse(responseCode = "200", description = "Reporte guardado", content = @Content(schema = @Schema(implementation = ReporteDTO.class ))),
            @ApiResponse(responseCode = "400", description = "Error al guardar el reporte", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No se encontro el computador especificado en el reporte", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<Object> saveReporte(@RequestBody ReporteDTO reporteDTO) {
        try {
            return ResponseEntity.ok(reporteService.saveReporte(reporteDTO));
        } catch (ReporteOperationException e) {
            return ResponseEntity.badRequest().body("Error al guardar el reporte");
        } catch ( ComputadorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @Operation(summary = "Obtener todos los reportes", description = "Obtener todos los reportes", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Aun no existen reportes", content = @Content(schema = @Schema(implementation = String.class))) })
    @GetMapping
    public ResponseEntity<Object> getReportes() {
        try {
            return ResponseEntity.ok(reporteService.getReportes());
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary ="obtener reporte por id",description = "Obtener un reporte por id", responses = {
            @ApiResponse(responseCode = "200", description = "Reporte obtenido", content = @Content(schema = @Schema(implementation = ReporteDTO.class ))),
            @ApiResponse(responseCode = "404", description = "No se encontro el reporte especificado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getReporte(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(reporteService.getReporte(id));
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener una lista de reportes por sala", description = "Obtener una reporte lista de reportes por sala", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reportes en la sala", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/sala/{sala}")
    public ResponseEntity<Object> getReportesBySala(@PathVariable ESala sala) {
        try {
            return ResponseEntity.ok(reporteService.getReportesBySala(sala));
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener una lista de reportes de un pc", description = "Obtener una lista de reportes de un pc con la sala y el numero del pc", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron reportes en el pc", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("Computador/{sala}/{numeroPc}")
    public ResponseEntity<Object> getReportesByPc(@PathVariable ESala sala, @PathVariable Integer numeroPc) {
        try {
            return ResponseEntity.ok(reporteService.getReportesByPc(sala, numeroPc));
        } catch (ComputadorNotFoundException | ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Operation(summary = "Obtener una lista reporte de reportes por tipo", description = "Obtener una reporte lista de reportes por tipo", responses = {
            @ApiResponse(responseCode = "200", description = "Reportes encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron los reportes ", content = @Content(schema = @Schema(implementation = String.class))) })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Object> getReportesByTipo(@PathVariable EReporte tipo) {
        try {
            return ResponseEntity.ok(reporteService.getReportesByTipo(tipo));
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @Operation(summary = "Almacenar o desalmacenar un reporte", description = "Almacenar o desalmacenar un reporte", responses = {
            @ApiResponse(responseCode = "200", description = "Reporte almacenado o desalmacenado", content = @Content(schema = @Schema(implementation = ReporteDTO.class ))),
            @ApiResponse(responseCode = "404", description = "No se encontraron los reportes ", content = @Content(schema = @Schema(implementation = String.class))) })
    @PutMapping("/{id}/{almacenado}")
    public ResponseEntity<Object> almacenarReporte(@PathVariable Integer id, @PathVariable Boolean almacenado) {
        try {
            return ResponseEntity.ok(reporteService.almacenarReporte(id, almacenado));
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar un reporte", description = "Eliminar un reporte", responses = {
            @ApiResponse(responseCode = "200", description = "Reporte eliminado", content = @Content(schema = @Schema(implementation = ResponseEntity.class ))),
            @ApiResponse(responseCode = "404", description = "No se encontro el reporte", content = @Content(schema = @Schema(implementation = String.class))) })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReporte(@PathVariable Integer id) {
        try {
            reporteService.deleteReporte(id);
            return ResponseEntity.ok("Reporte eliminado");
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Buscar todos los reportes almacenados o sin almacenar", description = "Busca todos los reportes almacenados o sin almacena ", responses = {
            @ApiResponse(responseCode = "200", description = "Reportes encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No se encontraron los reportes ", content = @Content(schema = @Schema(implementation = String.class))) })
    @GetMapping("/almacenado/{almacenado}")
    public ResponseEntity<Object> getReportesByAlmacenado(@PathVariable Boolean almacenado) {
        try {
            return ResponseEntity.ok(reporteService.getReporteByAlmacenado(almacenado));
        } catch (ReporteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}