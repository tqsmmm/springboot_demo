package com.jason.demo.service;

import org.springframework.stereotype.Service;

@Service
public class MenuService {
    public class MenuRowMapper implements RowMapper<Menu> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("mlid"));
            category.setName(rs.getString("mlmc"));
            category.setPath(rs.getString("mllj"));
            category.setParentId(rs.getInt("sjmlid"));
            return category;
        }
    }

}
