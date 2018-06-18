package com.task.android.wangyiyun.bean;

/**
 * Create by 韩振峰 on
 */
public class UserInfo {
    private String ID;
    private String password;
    private String name;
    private String userAvatar;

    public UserInfo(){}

    public UserInfo(String ID, String password, String name, String userAvatar) {
        this.ID = ID;
        this.password = password;
        this.name = name;
        this.userAvatar = userAvatar;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}