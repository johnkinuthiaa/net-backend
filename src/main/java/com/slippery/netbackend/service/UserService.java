package com.slippery.netbackend.service;

import com.slippery.netbackend.dto.UserDto;
import com.slippery.netbackend.models.Users;

public interface UserService {
    UserDto register(Users userDetails);
    UserDto login(Users userDetails);
    UserDto deleteUserAccount(Long userId);
}
