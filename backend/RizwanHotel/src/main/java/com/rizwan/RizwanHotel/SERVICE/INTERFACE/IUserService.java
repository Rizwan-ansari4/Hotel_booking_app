package com.rizwan.RizwanHotel.SERVICE.INTERFACE;

import com.rizwan.RizwanHotel.DTO.LoginRequest;
import com.rizwan.RizwanHotel.DTO.Resposne;
import com.rizwan.RizwanHotel.ENTITY.User;

public interface IUserService {

	Resposne register(User user);

	Resposne login(LoginRequest loginRequest);

	Resposne getAllUsers();

	Resposne getUserBookingHistory(String userId);

	Resposne deleteUser(String userId);

	Resposne getUserById(String userId);

	Resposne getMyInfo(String email);
	
}
