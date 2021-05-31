package com.kq.sharding.entity;

import java.util.Date;

/**
 * @author kq
 * @date 2021-05-27 11:23
 * @since 2020-0630
 */
public class DtoPage {

    /** 当前页 */
    protected int pageNow= 1;
    /**每页显示记录数*/
    protected int pageSize= 2;

    private Date startDate;
    private Date endDate;

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DtoPage{" +
                "pageNow=" + pageNow +
                ", pageSize=" + pageSize +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
