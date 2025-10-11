package com.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.LocalUser;
import com.app.repository.LocalUserRepository;

@Service
public class LocalUserService {

    @Autowired
    private LocalUserRepository localUserRepository;

    public LocalUser registerUser(LocalUser user) {
        return localUserRepository.save(user);
    }

    public LocalUser findByPhoneNumber(String phoneNumber) {
        return localUserRepository.findByPhoneNumber(phoneNumber);
    }

}
