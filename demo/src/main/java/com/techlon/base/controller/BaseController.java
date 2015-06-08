package com.techlon.base.controller;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.techlon.base.util.date.DateEditor;

/**
 * <p>文件名称 : BaseController.java</p>
 * <p>文件描述 : 控制器父类 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年6月5日 下午3:34:17</p>
 * @author 李文军
 */
public class BaseController {
	
	/**
	 * <p>方法描述 : 表单参数日期类型数据绑定 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年6月5日 下午3:36:07</p>
	 * @author 李文军
	 * @param binder
	 * @throws Exception
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}
