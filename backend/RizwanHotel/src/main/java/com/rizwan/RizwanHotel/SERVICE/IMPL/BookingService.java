package com.rizwan.RizwanHotel.SERVICE.IMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.rizwan.RizwanHotel.DTO.BookingDto;
import com.rizwan.RizwanHotel.DTO.Resposne;
import com.rizwan.RizwanHotel.ENTITY.Booking;
import com.rizwan.RizwanHotel.ENTITY.Room;
import com.rizwan.RizwanHotel.ENTITY.User;
import com.rizwan.RizwanHotel.EXCEPTION.OurException;
import com.rizwan.RizwanHotel.REPO.BookingRepository;
import com.rizwan.RizwanHotel.REPO.RoomRepository;
import com.rizwan.RizwanHotel.REPO.UserRepository;
import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IBookingService;
import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IRoomService;
import com.rizwan.RizwanHotel.UTILS.Utils;


@Service
public class BookingService implements IBookingService{
	
	@Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
	
	

	@Override
	public Resposne saveBooking(Long roomId, Long userId, Booking bookingRequest) {
		Resposne response = new Resposne();
		
		
		  try {
	            if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
	                throw new IllegalArgumentException("Check in date must come after check out date");
	            }
	            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
	            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));

	            List<Booking> existingBookings = room.getBookings();

	            if (!roomIsAvailable(bookingRequest, existingBookings)) {
	                throw new OurException("Room not Available for selected date range");
	            }

	            bookingRequest.setRoom(room);
	            bookingRequest.setUser(user);
	            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
	            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
	            bookingRepository.save(bookingRequest);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setBookingConfirmationCode(bookingConfirmationCode);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error Saving a booking: " + e.getMessage());

	        }
	        return response;
	}
	
	
	

	@Override
	public Resposne findBookingByConfirmationCode(String confirmationCode) {
		Resposne response = new Resposne();
		
		try {
			Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new OurException("Booking Not Found"));
            BookingDto bookingDto = Utils.mapBookingEntityToBookingDtoPlusUserBookedRooms(booking, true);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBooking(bookingDto);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Finding a booking: " + e.getMessage());

        }
        return response;
	}

	@Override
	public Resposne getAllBookings() {
		Resposne response = new Resposne();
		
		try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDto> bookingDtoList = Utils.mapBookingListEntiyToBookingListDto(bookingList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBookingList(bookingDtoList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return response;
	}

	@Override
	public Resposne cancelBooking(Long bookingId) {
		Resposne response = new Resposne();
		
		try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking Does Not Exist"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Cancelling a booking: " + e.getMessage());

        }
        return response;
	}

	
	 private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {

	        return existingBookings.stream()
	                .noneMatch(existingBooking ->
	                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
	                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
	                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
	                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
	                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

	                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
	                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

	                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

	                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
	                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

	                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
	                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
	                );
	    }
	 
	 
	 
	 
}
