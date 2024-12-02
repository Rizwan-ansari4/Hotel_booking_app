package com.rizwan.RizwanHotel.SERVICE.IMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rizwan.RizwanHotel.DTO.LoginRequest;
import com.rizwan.RizwanHotel.DTO.Resposne;
import com.rizwan.RizwanHotel.DTO.UserDto;
import com.rizwan.RizwanHotel.ENTITY.User;
import com.rizwan.RizwanHotel.EXCEPTION.OurException;
import com.rizwan.RizwanHotel.REPO.UserRepository;
import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IUserService;
import com.rizwan.RizwanHotel.UTILS.JWTUtils;
import com.rizwan.RizwanHotel.UTILS.Utils;

@Service
public class UserService implements IUserService{
	
	 	@Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    @Autowired
	    private JWTUtils jwtUtils;
	    @Autowired
	    private AuthenticationManager authenticationManager;
	
	
	

	@Override
	public Resposne register(User user) {
		Resposne response = new Resposne();
		try {
			if (user.getRole() == null || user.getRole().isBlank()) {
				user.setRole("USER");
			}
			if (userRepository.existsByEmail(user.getEmail())) {
				throw new OurException(user.getEmail() + "Already exist");
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = userRepository.save(user);
			UserDto userDto = Utils.mapUserEntityToUserDto(savedUser);
			response.setStatusCode(200);
			response.setUser(userDto);
			
		}catch (OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occurred Suring User Registration " + e.getMessage());
		}
		
		return response;
	}

	@Override
	public Resposne login(LoginRequest loginRequest) {
		Resposne response = new Resposne();
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("user Not found"));
			
			var token = jwtUtils.generateToken(user);
			response.setStatusCode(200);
			response.setToken(token);
			response.setRole(user.getRole());
			response.setExpirationTime("7 Days");
			response.setMessage("Successful");
			
		}catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occurred Suring User Login " + e.getMessage());
		}
		
		
		return response;
	}

	@Override
	public Resposne getAllUsers() {
		Resposne response = new Resposne();
		
		try {
			List<User> userList = userRepository.findAll();
			List<UserDto> userDtoList = Utils.mapUserListEntiyToUserListDto(userList);
			
			response.setStatusCode(200);
			response.setMessage("Successful");
			response.setUserList(userDtoList);
			
		}catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Getting All Users " + e.getMessage());
		}
		
		return response;
	}

	@Override
	public Resposne getUserBookingHistory(String userId) {
		Resposne response = new Resposne();
		try {
			User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
			UserDto userDto = Utils.mapUserEntityToUserDtoPlusUserBookingAndRoom(user);
			
			response.setStatusCode(200);
			response.setMessage("Successful");
			response.setUser(userDto);
			
		}catch (OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Occurred Suring User Login " + e.getMessage());
		}
		
		return response;
	}

	@Override
	public Resposne deleteUser(String userId) {
		
		Resposne response = new Resposne();

	        try {
	            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
	            userRepository.deleteById(Long.valueOf(userId));
	            response.setStatusCode(200);
	            response.setMessage("successful");

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {

	            response.setStatusCode(500);
	            response.setMessage("Error getting all users " + e.getMessage());
	        }
	        return response;
	}

	@Override
	public Resposne getUserById(String userId) {
		Resposne response = new Resposne();

	        try {
	            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
	            UserDto userDto = Utils.mapUserEntityToUserDto(user);
	            response.setStatusCode(200);
	            response.setMessage("successful");
	            response.setUser(userDto);

	        } catch (OurException e) {
	            response.setStatusCode(404);
	            response.setMessage(e.getMessage());

	        } catch (Exception e) {

	            response.setStatusCode(500);
	            response.setMessage("Error getting all users " + e.getMessage());
	        }
	        return response;
	}

	@Override
	public Resposne getMyInfo(String email) {
		Resposne response = new Resposne();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
	}

}
