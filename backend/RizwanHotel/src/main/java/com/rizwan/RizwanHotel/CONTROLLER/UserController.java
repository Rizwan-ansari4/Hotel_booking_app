package com.rizwan.RizwanHotel.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rizwan.RizwanHotel.SERVICE.INTERFACE.IUserService;
import com.rizwan.RizwanHotel.DTO.Resposne;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	 private IUserService userService;
	
	@GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Resposne> getAllUsers() {
		Resposne response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@GetMapping("/get-by-id/{userId}")
    public ResponseEntity<Resposne> getUserById(@PathVariable("userId") String userId) {
		Resposne response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	  @DeleteMapping("/delete/{userId}")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<Resposne> deleteUSer(@PathVariable("userId") String userId) {
		  	Resposne response = userService.deleteUser(userId);
	        return ResponseEntity.status(response.getStatusCode()).body(response);
	    }
	  
	  
	  @GetMapping("/get-logged-in-profile-info")
	    public ResponseEntity<Resposne> getLoggedInUserProfile() {

	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String email = authentication.getName();
	        Resposne response = userService.getMyInfo(email);
	        return ResponseEntity.status(response.getStatusCode()).body(response);
	    }
	  
	  @GetMapping("/get-user-bookings/{userId}")
	    public ResponseEntity<Resposne> getUserBookingHistory(@PathVariable("userId") String userId) {
		  	Resposne response = userService.getUserBookingHistory(userId);
	        return ResponseEntity.status(response.getStatusCode()).body(response);
	    }
	  
	  
	  
	  
	  
	  
	  
	  
	
}
