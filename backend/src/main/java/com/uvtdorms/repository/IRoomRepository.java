package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
    public Optional<Room> getRoomByRoomNumber(String roomNumber);
}
