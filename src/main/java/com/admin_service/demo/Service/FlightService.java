package com.admin_service.demo.Service;

import com.admin_service.demo.dto.FlightDTO;
import com.admin_service.demo.models.Flight;
import com.admin_service.demo.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Метод для преобразования сущности Flight в FlightDTO
    public FlightDTO toDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setOrigin(flight.getOrigin());
        dto.setDestination(flight.getDestination());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setAvailableSeats(flight.getAvailableSeats());
        dto.setPrice(flight.getPrice());
        return dto;
    }

    // Метод для преобразования FlightDTO в сущность Flight
    public Flight toEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setOrigin(dto.getOrigin());
        flight.setDestination(dto.getDestination());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setAvailableSeats(dto.getAvailableSeats());
        flight.setPrice(dto.getPrice());
        return flight;
    }

    // Создание нового рейса с использованием DTO
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = toEntity(flightDTO);
        Flight createdFlight = flightRepository.save(flight);
        return toDTO(createdFlight);
    }

    // Обновление существующего рейса с использованием DTO
    public FlightDTO updateFlight(Long flightId, FlightDTO flightDTO) {
        Flight updatedFlight = flightRepository.findById(flightId)
                .map(flight -> {
                    flight.setOrigin(flightDTO.getOrigin());
                    flight.setDestination(flightDTO.getDestination());
                    flight.setDepartureTime(flightDTO.getDepartureTime());
                    flight.setArrivalTime(flightDTO.getArrivalTime());
                    flight.setAvailableSeats(flightDTO.getAvailableSeats());
                    flight.setPrice(flightDTO.getPrice());
                    return flightRepository.save(flight);
                })
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return toDTO(updatedFlight);
    }

    // Удаление рейса
    public void deleteFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    // Получение списка всех рейсов в формате DTO
    public List<FlightDTO> getAllFlightsDTO() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Получение данных о конкретном рейсе в формате DTO
    public FlightDTO getFlightByIdDTO(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return toDTO(flight);
    }
}
