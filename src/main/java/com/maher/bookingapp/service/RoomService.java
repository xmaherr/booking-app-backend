package com.maher.bookingapp.service;

import com.maher.bookingapp.dto.RoomRequest;
import com.maher.bookingapp.dto.RoomResponse;
import com.maher.bookingapp.entity.Room;
import com.maher.bookingapp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public RoomResponse createRoom(RoomRequest request) {
        Room room = mapToEntity(request);
        Room savedRoom = roomRepository.save(room);
        return mapToResponse(savedRoom);
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<RoomResponse> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(this::mapToResponse);
    }

    public RoomResponse updateRoom(Long id, RoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setPricePerNight(request.getPricePerNight());
        room.setCapacity(request.getCapacity());
        room.setAvailable(request.getAvailable());

        Room updatedRoom = roomRepository.save(room);
        return mapToResponse(updatedRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    private Room mapToEntity(RoomRequest request) {
        Room room = new Room();
        room.setName(request.getName());
        room.setDescription(request.getDescription());
        room.setPricePerNight(request.getPricePerNight());
        room.setCapacity(request.getCapacity());
        room.setAvailable(request.getAvailable());
        return room;
    }

    private RoomResponse mapToResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setName(room.getName());
        response.setDescription(room.getDescription());
        response.setPricePerNight(room.getPricePerNight());
        response.setCapacity(room.getCapacity());
        response.setAvailable(room.getAvailable());
        return response;
    }
}
