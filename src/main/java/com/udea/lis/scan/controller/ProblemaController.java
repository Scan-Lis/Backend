package com.udea.lis.scan.controller;

import com.udea.lis.scan.controller.requestModel.AsignarProblemaRequest;
import com.udea.lis.scan.controller.requestModel.ObservacionRequest;
import com.udea.lis.scan.error.ProblemaNotFoundException;
import com.udea.lis.scan.model.dto.ObservacionDTO;
import com.udea.lis.scan.model.dto.ProblemaDTO;
import com.udea.lis.scan.model.entity.Reporte;
import com.udea.lis.scan.model.enums.ESala;
import com.udea.lis.scan.service.observacionservice.ObservacionServiceImpl;
import com.udea.lis.scan.service.problemaservice.IProblemaService;
import com.udea.lis.scan.service.problemaservice.ProblemaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/problema")
@AllArgsConstructor
public class ProblemaController {

    private final ObservacionServiceImpl observacionServiceImpl;
    private IProblemaService problemaService;

    @Operation(summary = "Obtener todos los problemas", description = "Obtener todos los problemas", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping
    public ResponseEntity<Page<ProblemaDTO>> getAllProblemas(Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemas(pageable));
        } catch (ProblemaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "Obtener problema por ID", description = "Obtener un problema por su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Problema encontrado", content = @Content(schema = @Schema(implementation = ProblemaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Problema no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProblema(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(problemaService.getProblema(id));
        } catch (ProblemaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Eliminar problema por ID", description = "Eliminar un problema usando su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Problema eliminado"),
            @ApiResponse(responseCode = "404", description = "Problema no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProblema(@PathVariable Integer id) {
        try {
            problemaService.deleteProblema(id);
            return ResponseEntity.ok("Problema eliminado");
        } catch (ProblemaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Asignar un problema a un auxiliar", description = "Asignar un problema a un usuario auxiliar", responses = {
            @ApiResponse(responseCode = "200", description = "Problema asignado exitosamente", content = @Content(schema = @Schema(implementation = ProblemaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Problema no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/asignar")
    public ResponseEntity<Object> asignarProblema(@RequestBody AsignarProblemaRequest request) {
        try {
            if (request.getCorreoUsuario() == null || request.getCorreoUsuario().isEmpty()) {
                return ResponseEntity.badRequest().body("El correo del usuario no puede estar vacío");
            }
            if (request.getId() == null) {
                return ResponseEntity.badRequest().body("El ID del problema no puede estar vacío");
            }
            problemaService.asignarProblema(request.getId(), request.getCorreoUsuario());
            return ResponseEntity.ok("Problema asignado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener problemas por sala", description = "Obtener todos los problemas asociados a una sala", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping("/sala/{sala}")
    public ResponseEntity<Page<ProblemaDTO>> getProblemasBySala(@PathVariable ESala sala, Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemasBySala(sala, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "Obtener problemas por computador", description = "Obtener todos los problemas asociados a un computador específico", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping("/computador/{sala}/{numeroPc}")
    public ResponseEntity<Page<ProblemaDTO>> getProblemasByPc(@PathVariable ESala sala, @PathVariable Integer numeroPc, Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemasByPc(sala, numeroPc, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "Obtener problemas por estado de solución", description = "Obtener todos los problemas solucionados o no", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping("/estado/{solucionado}")
    public ResponseEntity<Page<ProblemaDTO>> getProblemasBySolucionado(@PathVariable Boolean solucionado, Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemasBySolucionado(solucionado, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "Obtener problemas por auxiliar asignado", description = "Obtener problemas según el auxiliar asignado", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping("/auxiliar/{usuario}")
    public ResponseEntity<Page<ProblemaDTO>> getProblemasByAuxiliarAsignado(@PathVariable String usuario, Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemasByAuxiliarAsignado(usuario, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }


    @Operation(summary = "Obtener problemas por fecha de creación", description = "Obtener problemas creados entre dos fechas", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping("/fechaCreacion")
    public ResponseEntity<Page<ProblemaDTO>> getProblemasByFechaCreacionBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaFin,
            Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemasByFechaCreacionBetween(fechaInicio, fechaFin, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    @Operation(summary = "Obtener problemas por fecha de terminación", description = "Obtener problemas terminados entre dos fechas", responses = {
            @ApiResponse(responseCode = "200", description = "Problemas encontrados")
    })
    @GetMapping("/fechaTerminacion")
    public ResponseEntity<Page<ProblemaDTO>> getProblemasByFechaTerminacionBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fechaFin,
            Pageable pageable) {
        try {
            return ResponseEntity.ok(problemaService.getProblemasByFechaTerminacionBetween(fechaInicio, fechaFin, pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

    //solucionarProblema
    @Operation(summary = "Solucionar problema", description = "Marcar un problema como solucionado", responses = {
            @ApiResponse(responseCode = "200", description = "Problema solucionado"),
            @ApiResponse(responseCode = "404", description = "Problema no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/solucionar/{id}")
    public ResponseEntity<Object> solucionarProblema(@PathVariable Integer id, @RequestBody String descripcionSolucion) {
        try {
            return ResponseEntity.ok(problemaService.solucionarProblema(id, true, descripcionSolucion));
        } catch (ProblemaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Agregar una observación a un problema", description = "Agregar una observación a un problema", responses = {
            @ApiResponse(responseCode = "200", description = "Observación agregada"),
            @ApiResponse(responseCode = "404", description = "Problema no encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/agregarObservacion/{id}")
    public ResponseEntity<Object> agregarObservacion(@PathVariable Integer id, @RequestBody ObservacionRequest observacionRequest) {
        try {
            return ResponseEntity.ok(problemaService.agregarObservacion(id, observacionRequest.getObservacion(), observacionRequest.getAutor()));
        } catch (ProblemaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //todas las observaciones de un problema
    @Operation(summary = "Obtener todas las observaciones de un problema", description = "Obtener todas las observaciones de un problema", responses = {
            @ApiResponse(responseCode = "200", description = "Observaciones encontradas"),
    })
    @GetMapping("/observaciones/{idProblema}")
    public ResponseEntity<Page<ObservacionDTO>> getObservacionesByProblema(@PathVariable Integer idProblema, Pageable pageable) {
        try {
            return ResponseEntity.ok(observacionServiceImpl.findByProblemaId(idProblema, pageable));
        } catch (ProblemaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(Page.empty());
        }
    }

}
