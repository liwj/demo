package com.techlon.base.model;

import java.util.Date;

/**
 * <p>文件名称 : DemoModel.java</p>
 * <p>文件描述 : 无 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年6月1日 下午2:17:46</p>
 * @author 李文军
 */
public class DemoModel {
	private Integer id;
	private String demoName;
	private String demoDesc;
	private Date demoDt;
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDemoName() {
		return demoName;
	}
	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}
	public String getDemoDesc() {
		return demoDesc;
	}
	public void setDemoDesc(String demoDesc) {
		this.demoDesc = demoDesc;
	}
	public Date getDemoDt() {
		return demoDt;
	}
	public void setDemoDt(Date demoDt) {
		this.demoDt = demoDt;
	}
	public DemoModel(Integer id, String demoName, String demoDesc, Date demoDt) {
		super();
		this.id = id;
		this.demoName = demoName;
		this.demoDesc = demoDesc;
		this.demoDt = demoDt;
	}
	public DemoModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
