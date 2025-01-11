package com.slippery.netbackend.controller;

import com.slippery.netbackend.dto.UserDto;
import com.slippery.netbackend.models.Users;
import com.slippery.netbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    /*
    --user methods
    UserDto register(Users userDetails);
    UserDto login(Users userDetails);
    UserDto deleteUserAccount(Long userId);
 */

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody Users userDetails){
        return ResponseEntity.ok(service.register(userDetails));
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody Users userDetails){
        return ResponseEntity.ok(service.login(userDetails));
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<UserDto> deleteUserAccount(@RequestParam @PathVariable Long userId){
        return ResponseEntity.ok(service.deleteUserAccount(userId));
    }


}
