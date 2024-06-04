package com.kq.sharding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kq.sharding.entity.InOutRecord;
import com.kq.sharding.service.IInOutRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: kq
 * @date: 2024-05-23 10:35:06
 * @since: 1.0.0
 * @description:
 */
@RestController
public class InOutRecordController {

    @Resource
    private IInOutRecordService inOutRecordService;

    /**
     * http://localhost:10000/in/out/list?startDate=2024-03-01&endDate=2024-05-31
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/in/out/list")
    public List<InOutRecord> list(@RequestParam(value = "startDate", required = false) Date startDate
                    , @RequestParam(value = "endDate", required = false) Date endDate) {

        LambdaQueryWrapper<InOutRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(startDate!=null) {
            lambdaQueryWrapper.ge(InOutRecord::getInOutTime, startDate);
        }

        if(endDate!=null) {
            lambdaQueryWrapper.le(InOutRecord::getInOutTime, endDate);
        }

        return inOutRecordService.list(lambdaQueryWrapper);

    }


    /**
     * http://localhost:10000/in/out/group?startDate=2024-03-01&endDate=2024-05-31
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/in/out/group")
    public List<InOutRecord> listGroup(@RequestParam(value = "startDate", required = false) Date startDate
            , @RequestParam(value = "endDate", required = false) Date endDate) {

        LambdaQueryWrapper<InOutRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(startDate!=null) {
            lambdaQueryWrapper.ge(InOutRecord::getInOutTime, startDate);
        }

        if(endDate!=null) {
            lambdaQueryWrapper.le(InOutRecord::getInOutTime, endDate);
        }

        lambdaQueryWrapper.groupBy(InOutRecord::getInOutTime).groupBy(InOutRecord::getUserId);

        return inOutRecordService.list(lambdaQueryWrapper);

    }


}
