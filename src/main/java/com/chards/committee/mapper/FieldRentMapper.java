package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.domain.FieldRent;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-03-19
 */
public interface FieldRentMapper extends BaseMapper<FieldRent> {

    Integer getIsManager(@Param("userId") String userId);

    Integer getIsRentManager(@Param("userId") String userId,
                         @Param("rentId") String rentId);
}
