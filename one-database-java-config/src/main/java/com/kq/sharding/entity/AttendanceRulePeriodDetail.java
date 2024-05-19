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
 * 考勤规则时段明细表
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_attendance_rule_period_detail")
public class AttendanceRulePeriodDetail implements Serializable {

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
     * 出入规则ID
     */
    private Long attendanceRuleId;

    /**
     * 考勤规则时段ID
     */
    private Long attendanceRulePeriodId;

    /**
     * 名称
     */
    private String name;

    /**
     * 0:开始  1:结束
     */
    private Integer type;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 迟到早退时间  根据type(0:开始  1:结束)来决定是迟到还是早退
     */
    private Integer earlyLaterTime;

    /**
     * 搜索开始时间
     */
    private Date searchStartTime;

    /**
     * 搜索结束时间
     */
    private Date searchEndTime;

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
