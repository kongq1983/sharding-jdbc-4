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
 * 考勤结果表
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_attendance_result")
public class AttendanceResult implements Serializable {

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
     * 人员
     */
    private Long userId;

    /**
     * 考勤日期
     */
    private Date attendanceDate;

    /**
     * 考勤规则ID
     */
    private Long attendanceRuleId;

    /**
     * 考勤规则时段ID
     */
    private Long attendanceRulePeriodId;

    /**
     * 考勤规则时段明细ID
     */
    private Long attendanceRulePeriodDetailId;

    /**
     * 出入时间-刷卡时间
     */
    private Date inOutTime;

    /**
     * 状态  0:未刷卡   1:正常   2:迟到   3:早退
     */
    private Integer status;

    /**
     * 纠正人
     */
    private Long correctUserId;

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
