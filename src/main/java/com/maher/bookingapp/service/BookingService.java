package com.maher.bookingapp.service;

import com.maher.bookingapp.Exceptions.RoomNotFoundException;
import com.maher.bookingapp.Exceptions.UserNotFoundException;
import com.maher.bookingapp.dto.BookingRequest;
import com.maher.bookingapp.dto.BookingResponse;
import com.maher.bookingapp.entity.Booking;
import com.maher.bookingapp.entity.Room;
import com.maher.bookingapp.entity.User;
import com.maher.bookingapp.repository.BookingRepository;
import com.maher.bookingapp.repository.RoomRepository;
import com.maher.bookingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    public BookingResponse createBooking(BookingRequest bookingDTO) {
        Booking booking = mapToEntity(bookingDTO);
        Booking saved = bookingRepository.save(booking);
        return mapToResponse(saved);
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToResponse(booking);
    }

    public BookingResponse updateBooking(Long id, BookingRequest bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setTotalPrice(bookingDTO.getTotalPrice());

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        booking.setUser(user);
        booking.setRoom(room);

        Booking updated = bookingRepository.save(booking);
        return mapToResponse(updated);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private Booking mapToEntity(BookingRequest bookingDTO) {
        Booking booking = new Booking();
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setTotalPrice(bookingDTO.getTotalPrice());

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        booking.setUser(user);
        booking.setRoom(room);

        return booking;
    }

    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setUserId(booking.getUser().getId());
        response.setRoomId(booking.getRoom().getId());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setTotalPrice(booking.getTotalPrice());
        return response;
    }
}
