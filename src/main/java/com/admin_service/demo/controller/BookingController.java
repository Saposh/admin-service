package com.admin_service.demo.controller;

import com.admin_service.demo.Service.BookingService;
import com.admin_service.demo.dto.BookingStatusUpdateRequest;
import com.admin_service.demo.models.Booking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Получение списка бронирований
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Обновление статуса бронирования
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long bookingId,
                                                       @RequestBody BookingStatusUpdateRequest request) {
        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, request.getStatus());
        return ResponseEntity.ok(updatedBooking);
    }

    // Получение бронирования по ID
    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    // Создание нового бронирования (если требуется)
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }
}
