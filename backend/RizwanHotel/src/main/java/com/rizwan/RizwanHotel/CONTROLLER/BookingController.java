package com.rizwan.RizwanHotel.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizwan.RizwanHotel.ENTITY.Booking;
import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IBookingService;
import com.rizwan.RizwanHotel.DTO.Resposne;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
    private IBookingService bookingService;
	
	@PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Resposne> saveBookings(@PathVariable Long roomId,
                                                 @PathVariable Long userId,
                                                 @RequestBody Booking bookingRequest) {


		Resposne response = bookingService.saveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	
	 @GetMapping("/all")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<Resposne> getAllBookings() {
		 	Resposne response = bookingService.getAllBookings();
	        return ResponseEntity.status(response.getStatusCode()).body(response);
	    }
	 
	 
	 @GetMapping("/get-by-confirmation-code/{confirmationCode}")
	    public ResponseEntity<Resposne> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
		 	Resposne response = bookingService.findBookingByConfirmationCode(confirmationCode);
	        return ResponseEntity.status(response.getStatusCode()).body(response);
	    }
	 
	 
	 
	 @DeleteMapping("/cancel/{bookingId}")
	    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	    public ResponseEntity<Resposne> cancelBooking(@PathVariable Long bookingId) {
		 	Resposne response = bookingService.cancelBooking(bookingId);
	        return ResponseEntity.status(response.getStatusCode()).body(response);
	    }
	 
	 
	 
	 
	 
}
