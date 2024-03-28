package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
    public Optional<Room> getRoomByRoomNumber(String roomNumber);

    public List<Room> findByDormDormName(String dormName);

    public Optional<Room> findByDormAndRoomNumber(Dorm dorm, String roomNumber);

    public Optional<Room> findByDormDormNameAndRoomNumber(String dormName, String roomNumber);
}
