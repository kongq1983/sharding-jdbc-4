package com.kq.sharding.entity;

import java.util.Date;

/**
 * @author kq
 * @date 2021-05-29 9:12
 * @since 2020-0630
 */
public class Account {

    private Long id;
    private String username;
    private String phone;
    private String province;
    private Date createTime;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", createTime=" + createTime +
                ", address='" + address + '\'' +
                '}';
    }
}
