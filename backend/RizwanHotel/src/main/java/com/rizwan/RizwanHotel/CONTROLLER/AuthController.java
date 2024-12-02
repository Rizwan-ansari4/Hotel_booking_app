package com.rizwan.RizwanHotel.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizwan.RizwanHotel.DTO.LoginRequest;
import com.rizwan.RizwanHotel.DTO.Resposne;
import com.rizwan.RizwanHotel.ENTITY.User;
import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IUserService userService;
	
	@PostMapping("/register")
    public ResponseEntity<Resposne> register(@RequestBody User user) {
		Resposne response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PostMapping("/login")
    public ResponseEntity<Resposne> login(@RequestBody LoginRequest loginRequest) {
		Resposne response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
