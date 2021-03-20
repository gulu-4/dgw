package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.FieldRent;
import com.chards.committee.mapper.FieldRentMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
@Service(value = "fieldRentService")
public class FieldRentService extends ServiceImpl<FieldRentMapper,FieldRent> {

}
