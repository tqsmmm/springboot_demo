package com.jason.demo.service;

import org.springframework.stereotype.Service;

import com.jason.demo.common.User;

@Service
public class LoginService {

    public boolean login(String username, String password) {
        User user = new User();

        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
}
