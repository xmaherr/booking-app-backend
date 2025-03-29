package com.maher.bookingapp.dto;

//import lombok.Getter;
//import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    private Long id;
    private String name;
    private String description;
    private Double pricePerNight;
    private Integer capacity;
    private Boolean available;
}
