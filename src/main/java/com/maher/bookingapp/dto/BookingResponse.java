package com.maher.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private Long userId;
    private Long roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private Double totalPrice;

    // getters & setters
}
