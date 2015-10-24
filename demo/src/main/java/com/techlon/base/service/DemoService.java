package com.techlon.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techlon.base.mappers.DemoMapper;
import com.techlon.base.model.DemoModel;

/**
 * <p>文件名称 : DemoService.java</p>
 * <p>文件描述 : 无 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年6月1日 下午2:17:54</p>
 * @author 李文军
 */
@Service
public class DemoService {
	@Resource
	DemoMapper demoMapper;
	public List<DemoModel> findAll() {
		// TODO Auto-generated method stub
		return demoMapper.findAll();
	}
}
