package com.rizwan.RizwanHotel.SERVICE.IMPL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rizwan.RizwanHotel.DTO.Resposne;
import com.rizwan.RizwanHotel.DTO.RoomDto;
import com.rizwan.RizwanHotel.ENTITY.Room;
import com.rizwan.RizwanHotel.EXCEPTION.OurException;
import com.rizwan.RizwanHotel.REPO.BookingRepository;
import com.rizwan.RizwanHotel.REPO.RoomRepository;
import com.rizwan.RizwanHotel.SERVICE.AwsS3Service;
import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IRoomService;
import com.rizwan.RizwanHotel.UTILS.Utils;

@Service
public class RoomService implements IRoomService{
	
	  	@Autowired
	    private RoomRepository roomRepository;
	    @Autowired
	    private BookingRepository bookingRepository;
	    @Autowired
	    private AwsS3Service awsS3Service;

	@Override
	public Resposne addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
		Resposne response = new Resposne();
		try {
			String imageUrl = awsS3Service.saveImageToS3(photo);
			Room room = new Room();
			
			room.setRoomPhotoUrl(imageUrl);
			room.setRoomType(roomType);
			room.setRoomPrice(roomPrice);
			room.setRoomDescription(description);
			
			Room savedRoom = roomRepository.save(room);
			RoomDto roomDto = Utils.mapRoomEntityToRoomDto(savedRoom);
			response.setStatusCode(200);
			response.setMessage("Successful");
			response.setRoom(roomDto);
			
		}catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error saving a room  " + e.getMessage());
		}
		
		return response;
	}

	@Override
	public List<String> getAllRoomTypes() {
		List<String> roomTypeList = roomRepository.findDistinctRoomTypes();
		return roomTypeList;
	}

	@Override
	public Resposne getAllRooms() {
		Resposne response = new Resposne();
		
		try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDto> roomDtoList = Utils.mapRoomListEntiyToRoomListDto(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDtoList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
	}

	@Override
	public Resposne deleteRoom(Long roomId) {
		Resposne response = new Resposne();
		
		
		  try {
	            roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
	            roomRepository.deleteById(roomId);
	            response.setStatusCode(200);
	            response.setMessage("successful");

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());
	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error saving a room " + e.getMessage());
	        }
	        return response;
	}

	@Override
	public Resposne updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
		Resposne response = new Resposne();
		
		 try {
	            String imageUrl = null;
	            if (photo != null && !photo.isEmpty()) {
	                imageUrl = awsS3Service.saveImageToS3(photo);
	            }
	            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
	            if (roomType != null) room.setRoomType(roomType);
	            if (roomPrice != null) room.setRoomPrice(roomPrice);
	            if (description != null) room.setRoomDescription(description);
	            if (imageUrl != null) room.setRoomPhotoUrl(imageUrl);

	            Room updatedRoom = roomRepository.save(room);
	            RoomDto roomDTO = Utils.mapRoomEntityToRoomDto(updatedRoom);

	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setRoom(roomDTO);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());
	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error saving a room " + e.getMessage());
	        }
	        return response;
	}

	@Override
	public Resposne getRoomById(Long roomId) {
		Resposne response = new Resposne();
		
		  try {
	            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
	            RoomDto roomDto = Utils.mapRoomEntityToRoomDtoPlusBookings(room);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setRoom(roomDto);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());
	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error saving a room " + e.getMessage());
	        }
	        return response;
	}

	@Override
	public Resposne getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		Resposne response = new Resposne();
		
		  try {
	            List<Room> availableRooms = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
	            List<RoomDto> roomDtoList = Utils.mapRoomListEntiyToRoomListDto(availableRooms);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setRoomList(roomDtoList);

	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error saving a room " + e.getMessage());
	        }
	        return response;
	}

	@Override
	public Resposne getAllAvailableRooms() {
		Resposne response = new Resposne();
		
		 try {
	            List<Room> roomList = roomRepository.getAllAvailableRoom();
	            List<RoomDto> roomDtoList = Utils.mapRoomListEntiyToRoomListDto(roomList);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setRoomList(roomDtoList);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());
	        } catch (Exception e) {
	            response.setStatusCode(500);
	            response.setMessage("Error saving a room " + e.getMessage());
	        }
	        return response;
	}

}
