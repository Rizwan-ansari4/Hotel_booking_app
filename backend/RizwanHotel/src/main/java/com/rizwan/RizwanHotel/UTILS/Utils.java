package com.rizwan.RizwanHotel.UTILS;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.rizwan.RizwanHotel.DTO.BookingDto;
import com.rizwan.RizwanHotel.DTO.RoomDto;
import com.rizwan.RizwanHotel.DTO.UserDto;
import com.rizwan.RizwanHotel.ENTITY.Booking;
import com.rizwan.RizwanHotel.ENTITY.Room;
import com.rizwan.RizwanHotel.ENTITY.User;

public class Utils {

	private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final SecureRandom securerandom = new SecureRandom();
	
	
	public static String generateRandomConfirmationCode(int length) {
		StringBuffer stringBuilder = new StringBuffer();
		for(int i=0; i<length; i++) {
			int randomIndex = securerandom.nextInt(ALPHANUMERIC_STRING.length());
			char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
			stringBuilder.append(randomChar);
		}
		return stringBuilder.toString();
	}
	
	public static UserDto mapUserEntityToUserDto(User user) {
		UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRole());
		
		return userDto;
	}

	
	public static RoomDto mapRoomEntityToRoomDto(Room room) {
		RoomDto roomDto = new RoomDto();
		
		roomDto.setId(room.getId());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomPrice(room.getRoomPrice());
		roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
		roomDto.setRoomDescription(room.getRoomDescription());
		
		return roomDto;
	}
	
	public static BookingDto mapBookingEntityToBookingDto(Booking booking) {
		BookingDto bookingDto = new BookingDto();
		
		bookingDto.setId(booking.getId());
		bookingDto.setCheckInDate(booking.getCheckInDate());
		bookingDto.setCheckOutDate(booking.getCheckOutDate());
		bookingDto.setNumOfAdults(booking.getNumOfAdults());
		bookingDto.setNumOfChildren(booking.getNumOfChildren());
		bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());
		bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		
		return bookingDto;
	}
	
	

	public static RoomDto mapRoomEntityToRoomDtoPlusBookings(Room room) {
		RoomDto roomDto = new RoomDto();
		
		roomDto.setId(room.getId());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomPrice(room.getRoomPrice());
		roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
		roomDto.setRoomDescription(room.getRoomDescription());
		
		if (room.getBookings() != null) {
			roomDto.setBookings(room.getBookings().stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList()));
		}
		
		return roomDto;
	}
	
	
	
	public static UserDto mapUserEntityToUserDtoPlusUserBookingAndRoom(User user) {
		UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRole());
		
		if (!user.getBookings().isEmpty()) {
			userDto.setBookings(user.getBookings().stream().map(booking -> mapBookingEntityToBookingDtoPlusUserBookedRooms(booking,false)).collect(Collectors.toList()));
		}
		
		return userDto;
	}
	
	public static BookingDto mapBookingEntityToBookingDtoPlusUserBookedRooms(Booking booking, boolean mapUser) {
		BookingDto bookingDto = new BookingDto();
		
		bookingDto.setId(booking.getId());
		bookingDto.setCheckInDate(booking.getCheckInDate());
		bookingDto.setCheckOutDate(booking.getCheckOutDate());
		bookingDto.setNumOfAdults(booking.getNumOfAdults());
		bookingDto.setNumOfChildren(booking.getNumOfChildren());
		bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());
		bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		
		if (mapUser) {
			bookingDto.setUser(Utils.mapUserEntityToUserDto(booking.getUser()));
		}
		if (booking.getRoom() != null) {
			RoomDto roomDto = new RoomDto();
			
			roomDto.setId(booking.getRoom().getId());
			roomDto.setRoomType(booking.getRoom().getRoomType());
			roomDto.setRoomPrice(booking.getRoom().getRoomPrice());
			roomDto.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
			roomDto.setRoomDescription(booking.getRoom().getRoomDescription());
			
			bookingDto.setRoom(roomDto);
		}
		return bookingDto;
	}
	
	
	public static List<UserDto> mapUserListEntiyToUserListDto(List<User> userList){
		return userList.stream().map(Utils::mapUserEntityToUserDto).collect(Collectors.toList());
	}
	
	public static List<RoomDto> mapRoomListEntiyToRoomListDto(List<Room> roomList){
		return roomList.stream().map(Utils::mapRoomEntityToRoomDto).collect(Collectors.toList());
	}
	
	public static List<BookingDto> mapBookingListEntiyToBookingListDto(List<Booking> bookingList){
		return bookingList.stream().map(Utils::mapBookingEntityToBookingDto).collect(Collectors.toList());
	}
	
	
	
	
	
	
	
}
