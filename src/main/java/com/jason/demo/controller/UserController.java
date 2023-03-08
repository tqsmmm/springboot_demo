package com.jason.demo.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import com.jason.demo.service.UserService;
import com.jason.demo.common.User;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (userService.login(username, password)) {
            return "登录成功";
        } else {
            return "验证失败";
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") int userId) {
        String query = "SELECT * FROM t_yhb WHERE yhid = ?";
        RowMapper<User> rowMapper = new UserRowMapper();
        User user = jdbcTemplate.queryForObject(query, rowMapper, userId);
        return user;
    }
 
    private static final class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("yhid"));
            user.setName(rs.getString("yhxm"));
            user.setPassword(rs.getString("yhmm"));
            return user;
        }
    }
}