package com.techlon.base.model;

import java.util.List;

/**
 * <p>文件名称 : TreeData.java</p>
 * <p>文件描述 : 菜单树 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年8月6日 下午3:28:45</p>
 * @author 李文军
 */
public class TreeData {
	private String text;// 显示文本
	private boolean leaf = true; // 是否是叶子
	private Integer id;// id
	private boolean expanded = true; // 是否展开
	private String icon;// 树图片
	private String href;// 链接
	private List<TreeData> children;// 下一级树
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public List<TreeData> getChildren() {
		return children;
	}
	public void setChildren(List<TreeData> children) {
		this.children = children;
	}
}
