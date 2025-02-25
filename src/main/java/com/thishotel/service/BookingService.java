package com.thishotel.service;

import com.thishotel.exception.UserNotFoundException;
import com.thishotel.model.Booking;
import com.thishotel.model.Room;
import com.thishotel.model.User;
import com.thishotel.repository.BookingRepository;
import com.thishotel.repository.RoomRepository;
import com.thishotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {
    @Autowired private BookingRepository bookingRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private UserRepository userRepository;

    public Booking createBooking(Long userId, Long roomId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utente non trovato"));

//        esempio utilizzo eccezione custom usernotfoundexception
        if ( userId == 0){
            throw new UserNotFoundException("User with id 0 dosen't exist", 1001);
        }

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Camera non trovata"));

//        if (!room.getAvailable()) {
//            throw new RuntimeException("La camera non è disponibile");
//        }
//
//        Booking booking = new Booking();
//        booking.setUser(user);
//        booking.setRoom(room);
//        booking.setStartDate(startDate);
//        booking.setEndDate(endDate);
//
//        room.setAvailable(false);
        roomRepository.save(room);

//        return bookingRepository.save(booking);
        return null;
    }
}

