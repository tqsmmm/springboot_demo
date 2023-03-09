package com.jason.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.RowMapper;

import com.jason.demo.common.User;

@Service
public class UserService {

    public static final class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("yhid"));
            user.setName(rs.getString("yhxm"));
            user.setPassword(rs.getString("yhmm"));
            user.setCode(rs.getString("yhmc"));
            return user;
        }
    }
}