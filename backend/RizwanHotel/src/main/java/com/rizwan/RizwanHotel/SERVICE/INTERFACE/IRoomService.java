package com.rizwan.RizwanHotel.SERVICE.INTERFACE;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rizwan.RizwanHotel.DTO.Resposne;

public interface IRoomService {

	Resposne addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Resposne getAllRooms();

    Resposne deleteRoom(Long roomId);

    Resposne updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo);

    Resposne getRoomById(Long roomId);

    Resposne getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    Resposne getAllAvailableRooms();
}
