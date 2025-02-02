package com.admin_service.demo.Service;

import com.admin_service.demo.models.Booking;
import com.admin_service.demo.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Создание нового бронирования
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Обновление статуса бронирования
    public Booking updateBookingStatus(Long bookingId, String newStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    // Получение списка бронирований (при необходимости с фильтрами, можно добавить параметры)
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Получение конкретного бронирования
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}
