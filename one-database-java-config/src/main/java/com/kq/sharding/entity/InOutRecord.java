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
 * 出入记录表
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ad_in_out_record")
public class InOutRecord implements Serializable {

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
     * 出入时间-刷卡时间
     */
    private Date inOutTime;

    /**
     * 方向  0: 未知   1:出   2:入
     */
    private Integer direction;

    /**
     * 出入门设备号
     */
    private String deviceNumber;

    /**
     * 门设备名称
     */
    private String deviceName;

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

    /**
     * 来源ID
     */
    private Long sourceId;


}
