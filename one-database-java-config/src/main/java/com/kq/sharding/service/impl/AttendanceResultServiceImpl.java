package com.kq.sharding.service.impl;

import com.kq.sharding.entity.AttendanceResult;
import com.kq.sharding.dao.AttendanceResultMapper;
import com.kq.sharding.service.IAttendanceResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考勤结果表 服务实现类
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Service
public class AttendanceResultServiceImpl extends ServiceImpl<AttendanceResultMapper, AttendanceResult> implements IAttendanceResultService {

}
