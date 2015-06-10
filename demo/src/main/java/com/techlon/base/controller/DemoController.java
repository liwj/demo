package com.techlon.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.techlon.base.service.DemoService;

/**
 * <p>文件名称 : DemoController.java</p>
 * <p>文件描述 : 加载数据demo </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年6月5日 下午2:12:59</p>
 * @author 李文军
 */
@Controller
@RequestMapping(value="demo/")
public class DemoController extends BaseController{
	
	private final DemoService demoService;
	
	@Autowired
	private DemoController(DemoService demoService) {
		this.demoService = demoService;
	}
	
	/**
	 * <p>方法描述 : 反馈视图和对象 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年6月1日 下午2:37:52</p>
	 * @author 李文军
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="demo_page_ftl",method=RequestMethod.GET)
	public ModelAndView toIndexPage(){
		System.out.println("--------------------------");
		ModelAndView mv = new ModelAndView("demo");
		mv.addObject("title", "加载新的视图页面，加载数据");
		mv.addObject("datas", demoService.findAll());
		return mv;
	}
	/**
	 * <p>方法描述 : 反馈json </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年6月5日 下午2:15:49</p>
	 * @author 李文军
	 */
	@ResponseBody
	@RequestMapping(value="get_json",method=RequestMethod.GET,consumes = {"text/plain", "application/json"})
	public void getJsonData(){

	}
	
	/**
	 * <p>方法描述 : 无 </p>
	 * <p>其他说明 : 无</p>
	 * <p>完成日期 : 2015年6月8日 上午10:11:57</p>
	 * @author 李文军
	 * @param status
	 * @return
	 */
	@RequestMapping(value="{status}")
	public String get(String status) {
		if ("1".equals(status)) {
			return "demo/status";
		} else {
			return "redirect:/demo/other";
		}
	}
}
