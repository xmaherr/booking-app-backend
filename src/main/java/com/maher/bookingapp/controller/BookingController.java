package com.maher.bookingapp.controller;

import com.maher.bookingapp.dto.BookingRequest;
import com.maher.bookingapp.dto.BookingResponse;
import com.maher.bookingapp.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest bookingDTO) {
        return bookingService.createBooking(bookingDTO);
    }

    @GetMapping
    public List<BookingResponse> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public BookingResponse updateBooking(@PathVariable Long id, @Valid @RequestBody BookingRequest bookingDTO) {
        return bookingService.updateBooking(id, bookingDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
