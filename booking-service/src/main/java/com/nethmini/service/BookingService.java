package com.nethmini.service;

import com.nethmini.domain.BookingStatus;
import com.nethmini.dto.BookingRequest;
import com.nethmini.dto.SalonDTO;
import com.nethmini.dto.ServiceDTO;
import com.nethmini.dto.UserDTO;
import com.nethmini.modal.Booking;
import com.nethmini.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO ser,
                          SalonDTO salon,
                          Set<ServiceDTO> serviceDTOSet
    ) throws Exception;
    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;
    void deleteBooking(Long bookingId);
    List<Booking> getBookingsByDate(LocalDate date,Long salonId);
    SalonReport getSalonReport(Long salonId);
}
