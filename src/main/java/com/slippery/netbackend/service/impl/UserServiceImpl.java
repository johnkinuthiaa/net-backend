package com.slippery.netbackend.service.impl;

import com.slippery.netbackend.dto.UserDto;
import com.slippery.netbackend.models.Users;
import com.slippery.netbackend.repository.UserRepository;
import com.slippery.netbackend.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final AuthenticationManager authentication;

    public UserServiceImpl(UserRepository repository, AuthenticationManager authentication) {
        this.repository = repository;

        this.authentication = authentication;
    }

    @Override
    public UserDto register(Users userDetails) {
        UserDto response =new UserDto();
        Users users =repository.findByUsername(userDetails.getUsername());
        if(users !=null){
            response.setMessage("User "+userDetails.getUsername()+"already exists");
            response.setStatusCode(500);
            return response;
        }
        if(userDetails.getEmail() !=null && userDetails.getPassword() !=null &&userDetails.getUsername() != null){
            userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            repository.save(userDetails);
            response.setMessage("User "+userDetails.getUsername()+" saved");
            response.setStatusCode(200);
            response.setUser(userDetails);
            return response;
        }
        response.setMessage("User "+userDetails.getUsername()+"not saved");
        response.setStatusCode(500);

        return response;
    }

    @Override
    public UserDto login(Users userDetails) {
        UserDto response =new UserDto();
        Authentication authentication1 =authentication.authenticate(new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),userDetails.getPassword()
        ));
        if(authentication1.isAuthenticated()){
            response.setMessage("User logged in");
            response.setStatusCode(200);
            return response;
        }
        response.setMessage("User not logged in");
        response.setStatusCode(500);
        return response;
    }

    @Override
    public UserDto deleteUserAccount(Long userId) {
        UserDto response =new UserDto();
        Optional<Users> user =repository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User not found");
            response.setStatusCode(404);
            return response;
        }
        repository.deleteById(userId);
        response.setMessage("User with id "+userId+" deleted successfully");
        response.setStatusCode(200);
        return response;
    }
}
