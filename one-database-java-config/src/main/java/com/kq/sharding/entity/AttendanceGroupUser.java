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
 * 考勤组用户
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_attendance_group_user")
public class AttendanceGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 考勤组ID
     */
    private Long attendanceGroupId;

    /**
     * 用户ID
     */
    private Long userId;

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
