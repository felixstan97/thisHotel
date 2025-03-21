package com.thishotel.repository;

import com.thishotel.enums.RoomStatus;
import com.thishotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(String roomNumber);
    List<Room> findAllByRoomStatus(RoomStatus status);
    boolean existsByCleanerIdAndRoomStatus(Long cleanerId, RoomStatus status);
    List<Room> findAllByRoomStatusInAndCleanerIdIsNullOrCleanerId(List<RoomStatus>statuses, Long cleanerId);
}
