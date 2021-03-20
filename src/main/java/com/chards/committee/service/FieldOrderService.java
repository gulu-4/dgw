package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.FieldOrder;
import com.chards.committee.dto.UserInfo;
import com.chards.committee.mapper.FieldOrderMapper;
import com.chards.committee.vo.FieldOrderGetParamVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
@Service("fieldOrderService")
public class FieldOrderService extends ServiceImpl<FieldOrderMapper,FieldOrder> {

    public Page<FieldOrder> getList(Page<FieldOrder> page,FieldOrderGetParamVO fieldOrderGetParamVO) {
        return baseMapper.getList(page,fieldOrderGetParamVO);
    }

    public FieldOrder getOrderById(FieldOrderGetParamVO fieldOrderGetParamVO) {
        return baseMapper.getOrderById(fieldOrderGetParamVO);
    }
}
