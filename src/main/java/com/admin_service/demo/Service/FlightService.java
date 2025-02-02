package com.admin_service.demo.Service;

import com.admin_service.demo.dto.FlightDTO;
import com.admin_service.demo.models.Flight;
import com.admin_service.demo.repositories.BookingRepository;
import com.admin_service.demo.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;

    public FlightService(FlightRepository flightRepository, BookingRepository bookingRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
    }

    // Преобразование сущности в DTO
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

    // Преобразование DTO в сущность
    public Flight toEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setOrigin(dto.getOrigin());
        flight.setDestination(dto.getDestination());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setAvailableSeats(dto.getAvailableSeats());
        flight.setPrice(dto.getPrice());
        // Обратите внимание: поле status может быть установлено по умолчанию при создании,
        // например, "ACTIVE"
        flight.setStatus("ACTIVE");
        return flight;
    }

    // Создание нового рейса
    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = toEntity(flightDTO);
        Flight createdFlight = flightRepository.save(flight);
        return toDTO(createdFlight);
    }

    // Обновление рейса
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

    // Soft delete (деактивация) рейса
    public FlightDTO deleteFlight(Long flightId) {
        // Проверяем, есть ли бронирования для данного рейса
        if (bookingRepository.existsByFlightId(flightId)) {
            throw new RuntimeException("Flight cannot be deactivated because bookings exist");
        }
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        // Обновляем статус на "DEACTIVATED"
        flight.setStatus("DEACTIVATED");
        Flight updatedFlight = flightRepository.save(flight);
        return toDTO(updatedFlight);
    }

    // Получение списка всех рейсов
    public List<FlightDTO> getAllFlightsDTO() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Получение конкретного рейса по ID
    public FlightDTO getFlightByIdDTO(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return toDTO(flight);
    }
}
