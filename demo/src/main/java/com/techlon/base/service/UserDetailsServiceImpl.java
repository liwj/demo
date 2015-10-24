package com.techlon.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techlon.base.mappers.UserMapper;
import com.techlon.base.model.LoginUser;
import com.techlon.base.model.Menu;
import com.techlon.base.model.User;
import com.techlon.base.util.Constant;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Resource
	private UserMapper userMapper; 
	
	@Resource
	private MenuService menuService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByName(username);
		Assert.assertNotNull(username + Constant.USERNAME_NOT_FOUND, user);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<Menu> menus = menuService.getMenuByRole(user.getRoleId());
		for (Menu menu : menus) {
			authorities.add(new SimpleGrantedAuthority(menu.getMenuUrl()));
		}
		LoginUser loginUser = new LoginUser(username, user.getPassword(), true, true, true, true, authorities);
		loginUser.setName(user.getName());
		loginUser.setRoleId(user.getRoleId());
		loginUser.setUserId(user.getUserId());
		return loginUser;
	}
}
