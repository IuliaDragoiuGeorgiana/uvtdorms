package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Room;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoomRepository extends JpaRepository<Room, UUID> {
    @Transactional
    public Optional<Room> getRoomByRoomNumber(String roomNumber);

    @Transactional
    public List<Room> findByDormDormName(String dormName);

    @Transactional
    public Optional<Room> findByDormAndRoomNumber(Dorm dorm, String roomNumber);

    @Transactional
    public Optional<Room> findByDormDormNameAndRoomNumber(String dormName, String roomNumber);
}
