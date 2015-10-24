package com.techlon.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.techlon.base.mappers.MenuMapper;
import com.techlon.base.mappers.RoleMapper;
import com.techlon.base.model.LoginUser;
import com.techlon.base.model.Menu;
import com.techlon.base.model.Role;
import com.techlon.base.util.CommonUtil;


/**
 * <p>文件名称 : MenuService.java</p>
 * <p>文件描述 : 无 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年8月6日 上午10:22:20</p>
 * @author 李文军
 */
@Service
@Scope(value="singleton")
public class MenuService {
	
	private static Map<Integer,List<Menu>> roleMenus;
	
	private static List<Menu> menus;
	
	@Resource
	private MenuMapper menuMapper;
	
	@Resource
	private RoleMapper roleMapper;
	
	
	/**
	 * <p>方法描述 : 加载菜单数据 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月6日 下午2:28:59</p>
	 * @author 李文军
	 */
	@PostConstruct
	public void loadMenu() {
		this.getMenus();
		if (null == roleMenus || roleMenus.isEmpty()) {
			List<Role> roles = null;
			// 判断角色
			if (null == roles || roles.isEmpty()) {
				roles = roleMapper.findAll();
			}
			roleMenus = new ConcurrentHashMap<Integer, List<Menu>>();
			for (Role role : roles) {
				List<Menu> menus = menuMapper.findMenusByRole(role.getId());
				roleMenus.put(role.getId(), menus);
			}
		}
	}
	/**
	 * <p>方法描述 : 用户菜单树 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月6日 下午3:32:28</p>
	 * @author 李文军
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenuChildrensByRole(Integer roleId) {
		List<Menu> menus = roleMenus.get(roleId);
		List<Menu> userMenu = new ArrayList<>();
		List<Menu> result = new ArrayList<>();
		for (Menu menu : menus) {
			if (menu.getMenuParent().intValue() == 0) {// 表示为一级菜单
				userMenu.add(menu);
			}
		}
		for (Menu menu : userMenu) {
			menu.setChildrens(this.findChildMenu(menus, menu.getMenuId()));
			result.add(menu);
		}
		return result;
	}

	/**
	 * <p>方法描述 : 子菜单 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月6日 下午4:35:03</p>
	 * @author 李文军
	 * @param menus
	 * @param parentId
	 * @return
	 */
	private List<Menu> findChildMenu(List<Menu> menus, int parentId) {
		List<Menu> result = null;
		if (null != menus && !menus.isEmpty()) {
			result = new ArrayList<>();
			for (Menu menu : menus) {
				if (menu.getMenuParent().intValue() == parentId) {
					menu.setChildrens(this.findChildMenu(menus, menu.getMenuId()));
					result.add(menu);
				}
			}
		}
		return result;
	}

	/**
	 * <p>方法描述 : 菜单数据 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月6日 下午4:49:15</p>
	 * @author 李文军
	 * @return
	 */
	public List<Menu> getMenus() {
		if (null == menus || menus.isEmpty()) {
			menus = menuMapper.findAll();
		}
		return menus;
	}
	
	/**
	 * <p>方法描述 : 根据菜单ID获取按钮信息 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月6日 下午4:51:54</p>
	 * @author 李文军
	 * @param menuId 菜单ID
	 * @return 菜单拥有的按钮数据，以逗号分割
	 */
	public String getBtnByMenuId(Integer menuId) {
		LoginUser user = CommonUtil.getLoginUser();
		List<Menu> menus = roleMenus.get(user.getRoleId());
		List<Menu> btns = this.findChildMenu(menus, menuId);
		StringBuilder builder = new StringBuilder();
		for (Menu menu : btns) {
			builder.append(menu.getBtnType() + ",");
		}
		return builder.toString();
	}
	/**
	 * <p>方法描述 : 用户菜单 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月6日 下午5:16:27</p>
	 * @author 李文军
	 * @param roleId
	 * @return
	 */
	public List<Menu> getMenuByRole(Integer roleId) {
		return roleMenus.get(roleId);
	}
}
