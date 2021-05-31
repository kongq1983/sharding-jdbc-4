package com.kq.sharding.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author kq
 * @date 2021-05-25 16:35
 * @since 2020-0630
 */

public class Order {

    private String id;
    private String userId;
    private String code;
    private Date saleDate;
    private Date createTime;

//     `id` varchar(32) DEFAULT NULL,
//  `user_id` varchar(32) NOT NULL,
//  `code` varchar(32) DEFAULT NULL,
//  `sale_date` date NOT NULL,
//            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", code='" + code + '\'' +
                ", saleDate=" + saleDate +
                ", createTime=" + createTime +
                '}';
    }
}
