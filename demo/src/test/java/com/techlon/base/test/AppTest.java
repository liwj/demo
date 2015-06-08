package com.techlon.base.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.techlon.base.service.DemoService;

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
		String[] str = new String[]
				{
				"src/main/webapp/WEB-INF/spring/spring-context.xml",
				"src/main/webapp/WEB-INF/spring/spring-database.xml"};
		applicationContext = new FileSystemXmlApplicationContext(str);
	}
	
	@Test
	public void test1(){
		DemoService service = (DemoService) applicationContext.getBean("demoService");
		System.out.println(service.findAll().get(0).getDemoName());
//		SqlSessionFactory factory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
//		SqlSession session = factory.openSession();
//		DemoDao dao = session.getMapper(DemoDao.class);
//		System.out.println(dao.findAll().get(0).getDemoName());
	}
	
	@After
	public void after(){
		
	}
}
