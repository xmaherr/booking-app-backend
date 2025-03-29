package com.maher.bookingapp.controller;

import com.maher.bookingapp.dto.RoomRequest;
import com.maher.bookingapp.dto.RoomResponse;
import com.maher.bookingapp.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public RoomResponse createRoom(@Valid @RequestBody RoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @PutMapping("/{id}")
    public RoomResponse updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return roomService.updateRoom(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
