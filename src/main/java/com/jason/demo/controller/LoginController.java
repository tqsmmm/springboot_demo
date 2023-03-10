package com.jason.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import com.jason.demo.common.User;
import com.jason.demo.response.ApiResponse;
import com.jason.demo.service.UserService;

@RestController
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestParam String username, @RequestParam String password) {
        String query = "SELECT * FROM t_yhb WHERE yhmc = ? AND yhmm = ?";
        RowMapper<User> rowMapper = new UserService.UserRowMapper();
        User user;

        try {
            user = jdbcTemplate.queryForObject(query, rowMapper, username,
                    DigestUtils.md5DigestAsHex(("!@@!" + password + "xx").getBytes()).toUpperCase());
        } catch (EmptyResultDataAccessException e) {
            return new ApiResponse<>(401, "用户名或密码错误", null);
        }

        return new ApiResponse<>(200, "登录成功", user);
    }
}
