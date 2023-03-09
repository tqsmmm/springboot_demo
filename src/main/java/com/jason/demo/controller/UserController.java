package com.jason.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.RowMapper;

import com.jason.demo.api.ApiResponse;
import com.jason.demo.common.User;
import com.jason.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{userId}")
    public ApiResponse<User> getUserById(@PathVariable("userId") int userId) {
        String query = "SELECT * FROM t_yhb WHERE yhid = ?";
        RowMapper<User> rowMapper = new UserService.UserRowMapper();
        User user = jdbcTemplate.queryForObject(query, rowMapper, userId);

        if (user != null) {
            return new ApiResponse<>(200, "查询成功", user);
        } else {
            return new ApiResponse<>(404, "用户不存在", null);
        }
    }

    @GetMapping("/")
    public ApiResponse<List<User>> getAllUsers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code) {
        String query = "SELECT * FROM t_yhb";
        List<Object> params = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            query += " WHERE yhxm LIKE ?";
            params.add("%" + name + "%");
        }

        if (code != null) {
            if (params.isEmpty()) {
                query += " WHERE yhmc = ?";
            } else {
                query += " AND yhmc = ?";
            }
            params.add(code);
        }
        RowMapper<User> rowMapper = new UserService.UserRowMapper();
        List<User> users = jdbcTemplate.query(query, rowMapper, params.toArray());

        if (!users.isEmpty()) {
            return new ApiResponse<List<User>>(200, "查询成功", users);
        } else {
            return new ApiResponse<List<User>>(404, "没有符合条件的用户", null);
        }
    }

    @PutMapping("/{userId}")
    public ApiResponse<User> updateUserById(@PathVariable("userId") int userId, @RequestBody User user) {
        String query = "UPDATE t_yhb SET yhxm = ?, yhmm = ? WHERE yhid = ?";
        int rowsAffected = jdbcTemplate.update(query, user.getName(), user.getPassword(), userId);

        if (rowsAffected > 0) {
            user.setId(userId);
            return new ApiResponse<>(200, "更新成功", user);
        } else {
            return new ApiResponse<>(404, "用户不存在或更新失败", null);
        }
    }

    @PostMapping("/")
    public ApiResponse<User> addUser(@RequestBody User user) {
        String query = "INSERT INTO t_yhb(yhmc, yhmm, yhxm) VALUES(?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(query, user.getCode(), user.getPassword(), user.getName());

        if (rowsAffected > 0) {
            // int userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            // user.setId(userId);
            return new ApiResponse<>(200, "添加成功", user);
        } else {
            return new ApiResponse<>(500, "添加失败", null);
        }
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUserById(@PathVariable("userId") int userId) {
        String query = "DELETE FROM t_yhb WHERE yhid = ?";
        int rowsAffected = jdbcTemplate.update(query, userId);

        if (rowsAffected > 0) {
            return new ApiResponse<>(200, "删除成功", null);
        } else {
            return new ApiResponse<>(404, "用户不存在", null);
        }
    }

}