package com.jason.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jason.demo.api.ApiResponse;
import com.jason.demo.service.LoginService;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestParam String username, @RequestParam String password) {
        if (loginService.login(username, password)) {
            return new ApiResponse<>(200, "登录成功", null);
        } else {
            return new ApiResponse<>(401, "验证失败", null);
        }
    }
}
