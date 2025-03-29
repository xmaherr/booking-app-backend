package com.maher.bookingapp.repository;

import com.maher.bookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
