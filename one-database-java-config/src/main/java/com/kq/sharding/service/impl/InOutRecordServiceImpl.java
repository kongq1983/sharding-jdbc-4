package com.kq.sharding.service.impl;

import com.kq.sharding.entity.InOutRecord;
import com.kq.sharding.dao.InOutRecordMapper;
import com.kq.sharding.service.IInOutRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 出入记录表 服务实现类
 * </p>
 *
 * @author kq
 * @since 2024-05-19
 */
@Service
public class InOutRecordServiceImpl extends ServiceImpl<InOutRecordMapper, InOutRecord> implements IInOutRecordService {

}
