package com.techlon.base.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.techlon.base.model.Menu;
import com.techlon.base.service.MenuService;

/**
 * <p>文件名称 : AppTest.java</p>
 * <p>文件描述 : 无 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年6月1日 下午4:39:22</p>
 * @author 李文军
 */
public class AppTest {
	ApplicationContext applicationContext = null;
	@Before
	public void before(){
//		String[] str = new String[]
//				{
//				"src/main/webapp/WEB-INF/spring/spring-context.xml",
//				"src/main/webapp/WEB-INF/spring/spring-database.xml"};
//		applicationContext = new FileSystemXmlApplicationContext(str);
	}
	
	@Test
	public void test1(){
//		DemoService service = (DemoService) applicationContext.getBean("demoService");
//		MenuService menuService = (MenuService) applicationContext.getBean("menuService");
//		Assert.assertNull("is null", menuService);
//		System.out.println(1);
//		menuService.loadMenu();
//		List<Menu> list = menuService.getMenuByRole(1);
		long a = 1438902000965L;
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date(a)));
	}
	
	@After
	public void after(){
		
	}
}
