package com.rizwan.RizwanHotel.SERVICE.INTERFACE;

import com.rizwan.RizwanHotel.ENTITY.Booking;
import com.rizwan.RizwanHotel.DTO.Resposne;

public interface IBookingService {

	Resposne saveBooking(Long roomId, Long userId, Booking bookingRequest);

	Resposne findBookingByConfirmationCode(String confirmationCode);

	Resposne getAllBookings();

	Resposne cancelBooking(Long bookingId);
}
