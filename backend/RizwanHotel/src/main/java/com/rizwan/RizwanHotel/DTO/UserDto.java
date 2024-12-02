package com.rizwan.RizwanHotel.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rizwan.RizwanHotel.ENTITY.Booking;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	
	private long id;
	private String email;
	private String name;
	private String phoneNumber;
	private String role;
	private List<BookingDto> bookings = new ArrayList<>();
	
	
}
