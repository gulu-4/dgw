package com.chards.committee.mapper;

import com.chards.committee.domain.LoginIp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GY
 * @since 2020-07-25
 */
public interface LoginIpMapper extends BaseMapper<LoginIp> {
      int getCountAboutoday();
      int getCountAboutoday1();
      int getCountAboutoday2();
      int getCountAboutoday3();
      int getCountAboutoday4();
}

