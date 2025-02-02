package com.admin_service.demo.repositories;

import com.admin_service.demo.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
