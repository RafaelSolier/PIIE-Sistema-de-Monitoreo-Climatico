package com.example.piie.medicion.application;

import com.example.piie.medicion.domain.MedicionService;
import com.example.piie.medicion.dto.MedicionCreateDTO;
import com.example.piie.medicion.dto.MedicionDto;
import com.example.piie.medicion.dto.MedicionFilterDTO;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mediciones")
@RequiredArgsConstructor
@Slf4j
public class MedicionController {

    private final MedicionService medicionService;
    private final ModelMapper modelMapper;

    // Últimas mediciones por nodo (agrupadas por parámetro)
    @GetMapping("/nodo/{idNodo}/last")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> getUltimasMedicionesPorNodo(@PathVariable Long idNodo) {
        List<MedicionDto> mediciones = medicionService.getMedicionesByNodo(idNodo);

        return ResponseEntity.ok(mediciones);
    }

    // Todas las mediciones de un nodo
    @GetMapping("/nodo/{idNodo}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> getMedicionesPorNodo(@PathVariable Long idNodo) {
        List<MedicionDto> mediciones = medicionService.getMedicionesByNodo(idNodo);
        return ResponseEntity.ok(mediciones);
    }

    // Detalle de una medición específica
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<MedicionDto> getMedicionById(@PathVariable Long id) {
        MedicionDto medicion = medicionService.getMedicionById(id);
        return ResponseEntity.ok(medicion);
    }

    // Filtrar mediciones (POST recomendado para filtros complejos)
    @PostMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> filtrarMediciones(@Valid @RequestBody MedicionFilterDTO filter) {
        List<MedicionDto> mediciones = medicionService.getMedicionesByFilters(filter);
        return ResponseEntity.ok(mediciones);
    }

    // Alternativa GET para filtros (opcional)
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<List<MedicionDto>> filtrarMedicionesGet(@Valid @RequestBody MedicionFilterDTO filter) {

        List<MedicionDto> mediciones = medicionService.getMedicionesByFilters(filter);
        return ResponseEntity.ok(mediciones);
    }

    // Registrar nueva medición (acceso solo para módulos externos)
    @PostMapping("/sercrete/endpoint")
    public ResponseEntity<String> handleIoTCore(@RequestBody Map<String, Object> payload) {

        // Verificar si es mensaje de confirmación
        if (payload.containsKey("messageType") && "DestinationConfirmation".equals(payload.get("messageType"))) {
            return handleDestinationConfirmation(payload);
        }

        // Si no es confirmación, procesar como medición de IoT
        return handleMedicionFromIoT(payload);
    }
    private ResponseEntity<String> handleDestinationConfirmation(Map<String, Object> payload) {
        try {
            String confirmationToken = (String) payload.get("confirmationToken");
            String enableUrl = (String) payload.get("enableUrl");
            String arn = (String) payload.get("arn");

            log.info("Destination confirmation received:");
            log.info("Token: {}", confirmationToken);
            log.info("Enable URL: {}", enableUrl);
            log.info("ARN: {}", arn);

            // Auto-confirmar el endpoint
            confirmDestination(enableUrl);

            return ResponseEntity.ok("Destination confirmed successfully");

        } catch (Exception e) {
            log.error("Error handling destination confirmation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error confirming destination");
        }
    }

    private ResponseEntity<String> handleMedicionFromIoT(Map<String, Object> payload) {
        try {
            // Convertir el payload de IoT a tu DTO
            MedicionCreateDTO medicionDto = convertIoTPayloadToMedicion(payload);

            // Guardar la medición
            medicionService.createMedicion(medicionDto);

            log.info("Medición creada con IdNodo: {}", medicionDto.getIdNodo());
            return ResponseEntity.ok("Medición procesada correctamente");

        } catch (Exception e) {
            log.error("Error processing IoT medición", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing medición");
        }
    }

    private void confirmDestination(String enableUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(enableUrl, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Destination confirmed successfully");
                log.info("Confirmation: {} con codigo {}", response.getBody(), response.getStatusCode());
            } else {
                log.warn("Destination confirmation returned status: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error confirming destination: {}", e.getMessage());
        }
    }

    private MedicionCreateDTO convertIoTPayloadToMedicion(Map<String, Object> payload) {

        MedicionCreateDTO dto = new MedicionCreateDTO();

        // Ejemplo de mapeo - ajusta según tu estructura
        if (payload.containsKey("idNodo")) {
            dto.setIdNodo(((Number) payload.get("idNodo")).longValue());
        }
        if (payload.containsKey("temperatura")) {
            dto.setTemperatura(Double.valueOf(payload.get("temperatura").toString()));
        }
        if (payload.containsKey("humedad")) {
            dto.setHumedad(Double.valueOf(payload.get("humedad").toString()));
        }
        if (payload.containsKey("presion")) {
            dto.setPresion(Double.valueOf(payload.get("presion").toString()));
        }
        if (payload.containsKey("fecha")) {
            dto.setFecha(LocalDateTime.parse(payload.get("fecha").toString()));
        }
        if (payload.containsKey("token")) {
            dto.setToken(payload.get("token").toString());
        }
        return dto;
    }
}
