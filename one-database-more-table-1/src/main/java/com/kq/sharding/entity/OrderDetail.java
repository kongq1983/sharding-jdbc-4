package com.kq.sharding.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kq
 * @date 2021-05-27 9:02
 * @since 2020-0630
 */
public class OrderDetail {

    private String id;
    private String orderId;
    private String inventoryId;
    private Integer num;
    private BigDecimal unitPrice = new BigDecimal(0);
    private BigDecimal price = new BigDecimal(0) ;
    private Date createTime;
    private Date saleDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", inventoryId='" + inventoryId + '\'' +
                ", num=" + num +
                ", unitPrice=" + unitPrice +
                ", price=" + price +
                ", createTime=" + createTime +
                ", saleDate=" + saleDate +
                '}';
    }
}
