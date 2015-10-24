package com.techlon.base.model;

import java.util.Date;

public class User {
    private Integer userId;

    private String name;

    private Integer roleId;

    private String password;

    private Date lastLoginTime;

    private String username;

    private Integer sex;

    private Integer status;

    private Date updateTime;

    private Date createTime;

    public User(Integer userId, String name, Integer roleId, String password, Date lastLoginTime, String username, Integer sex, Integer status, Date updateTime, Date createTime) {
        this.userId = userId;
        this.name = name;
        this.roleId = roleId;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.username = username;
        this.sex = sex;
        this.status = status;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}