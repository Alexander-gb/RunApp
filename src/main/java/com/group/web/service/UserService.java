package com.group.web.service;

import com.group.web.dto.RegistrationDto;
import com.group.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
