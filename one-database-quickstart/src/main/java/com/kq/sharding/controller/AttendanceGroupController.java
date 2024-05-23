package com.kq.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kq.sharding.entity.AttendanceGroup;
import com.kq.sharding.entity.InOutRecord;
import com.kq.sharding.service.IAttendanceGroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: kq
 * @date: 2024-05-23 11:05:18
 * @since: 1.0.0
 * @description:
 */
@RestController
public class AttendanceGroupController {

    @Resource
    private IAttendanceGroupService groupService;

    /**
     * http://localhost:10000/group/list?startDate=2024-03-01&endDate=2024-05-31

     * @return
     */
    @GetMapping("/group/list")
    public List<AttendanceGroup> list() {

        LambdaQueryWrapper<AttendanceGroup> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        return groupService.list(lambdaQueryWrapper);

    }

}
