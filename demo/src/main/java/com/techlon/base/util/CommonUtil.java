package com.techlon.base.util;

import org.junit.Assert;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techlon.base.model.LoginUser;

/**
 * <p>文件名称 : CommonUtil.java</p>
 * <p>文件描述 : 无 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年8月7日 下午3:58:30</p>
 * @author 李文军
 */
public class CommonUtil {
	
	/**
	 * <p>方法描述 : 获取当前登录用户 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月7日 下午3:58:35</p>
	 * @author 李文军
	 * @return
	 */
	public static LoginUser getLoginUser(){
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Assert.assertNotNull(object);
		LoginUser user = (LoginUser) object;
		return user;
	}
	
	/**
	 * <p>方法描述 : 当前登录用户角色ID </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年8月7日 下午4:03:59</p>
	 * @author 李文军
	 * @return
	 */
	public static Integer getRoleByLoginUser(){
		LoginUser user = getLoginUser();
		return user.getRoleId();
	}
}
