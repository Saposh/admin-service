package com.admin_service.demo.controller;

import com.admin_service.demo.Service.FlightService;
import com.admin_service.demo.dto.FlightDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO createdFlight = flightService.createFlight(flightDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long flightId, @RequestBody FlightDTO flightDTO) {
        FlightDTO updatedFlight = flightService.updateFlight(flightId, flightDTO);
        return ResponseEntity.ok(updatedFlight);
    }

    // Soft delete: деактивировать рейс
    @DeleteMapping("/{flightId}")
    public ResponseEntity<?> deleteFlight(@PathVariable Long flightId) {
        try {
            FlightDTO flightDTO = flightService.deleteFlight(flightId);
            return ResponseEntity.ok(flightDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlightsDTO();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long flightId) {
        FlightDTO flightDTO = flightService.getFlightByIdDTO(flightId);
        return ResponseEntity.ok(flightDTO);
    }
}
