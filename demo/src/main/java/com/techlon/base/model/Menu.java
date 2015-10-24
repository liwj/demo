package com.techlon.base.model;

import java.util.List;

/**
 * <p>文件名称 : Menu.java</p>
 * <p>文件描述 : 菜单 </p>
 * <p>内容摘要 : 无</p>
 * <p>其他说明 : 无</p>
 * <p>完成日期 : 2015年8月6日 下午2:33:52</p>
 * @author 李文军
 */
public class Menu {
    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private Integer menuType;

    private Integer menuOrder;

    private Integer menuParent;

    private Integer menuLevel;

    private String btnType;

    private String btnImgUrl;

    private List<Menu> childrens;//子菜单
    
    public List<Menu> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Menu> childrens) {
		this.childrens = childrens;
	}

	public Menu(Integer menuId, String menuName, String menuUrl, Integer menuType, Integer menuOrder, Integer menuParent, Integer menuLevel, String btnType, String btnImgUrl) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuType = menuType;
        this.menuOrder = menuOrder;
        this.menuParent = menuParent;
        this.menuLevel = menuLevel;
        this.btnType = btnType;
        this.btnImgUrl = btnImgUrl;
    }

    public Menu() {
        super();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(Integer menuParent) {
        this.menuParent = menuParent;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getBtnType() {
        return btnType;
    }

    public void setBtnType(String btnType) {
        this.btnType = btnType == null ? null : btnType.trim();
    }

    public String getBtnImgUrl() {
        return btnImgUrl;
    }

    public void setBtnImgUrl(String btnImgUrl) {
        this.btnImgUrl = btnImgUrl == null ? null : btnImgUrl.trim();
    }
}