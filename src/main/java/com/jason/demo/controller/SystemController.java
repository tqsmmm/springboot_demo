package com.jason.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jason.demo.response.ApiResponse;

@RestController
@RequestMapping("/system")
public class SystemController {
    @GetMapping("/get_menu")
    public ApiResponse<List<>> getMenu() {
        String query="SELECT mlid, mlmc, mllj, sjmlid FROM t_mlb WHERE en = 1 ORDER BY mlid, mlpx";

        RowMapper<> rowMapper = new 
        
        return new ApiResponse<>(500, "查询失败", null);

        return new ApiResponse<>(200, "查询成功", null);
    }
}
