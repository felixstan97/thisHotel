package com.thishotel.controller;

import com.thishotel.model.Booking;
import com.thishotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{userId}/{roomId}")
    public ResponseEntity<Booking> bookRoom(
            @PathVariable Long userId,
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Booking booking = bookingService.createBooking(userId, roomId, startDate, endDate);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
}
