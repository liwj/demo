package com.techlon.base.mappers;

import java.util.List;

import com.techlon.base.model.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<Menu> findAll();

	List<Menu> findMenusByRole(Integer roleId);
}