package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.FieldOrder;
import com.chards.committee.vo.FieldOrderGetParamVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
public interface FieldOrderMapper extends BaseMapper<FieldOrder> {

    Page<FieldOrder> getList(@Param("page") Page<FieldOrder> page,
                             @Param("param") FieldOrderGetParamVO fieldOrderGetParamVO);

    FieldOrder getOrderById(@Param("param") FieldOrderGetParamVO fieldOrderGetParamVO);
}
