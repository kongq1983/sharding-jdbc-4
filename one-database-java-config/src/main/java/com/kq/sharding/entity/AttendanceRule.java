package com.kq.sharding.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_attendance_rule")
public class AttendanceRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 学校ID
     */
    private Long tenantId;

    /**
     * 名称
     */
    private String name;

    /**
     * 0:停用  1:启用
     */
    private Integer status;

    /**
     * 是否已使用 0:否 1:是
     */
    private Integer isUsing;

    /**
     * 创建用户
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改用户
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long editUser;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date editTime;


}
