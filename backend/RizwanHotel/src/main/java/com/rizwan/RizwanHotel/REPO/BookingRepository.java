package com.rizwan.RizwanHotel.REPO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rizwan.RizwanHotel.ENTITY.Booking;

public interface BookingRepository  extends JpaRepository<Booking, Long>{

	
	List<Booking> findByRoomId(long roomId);
	Optional<Booking> findByBookingConfirmationCode(String confirmationCode);
	List<Booking> findByUserId(Long userId);
}
