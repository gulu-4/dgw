package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.domain.DifficultiesStudentAssessment;
import com.chards.committee.util.DataScope;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-05-19
 */
public interface DifficultiesStudentAssessmentMapper extends BaseMapper<DifficultiesStudentAssessment> {

    @DataScope()
    DifficultiesStudentAssessment getByApplyId(@Param("applyId") Serializable applyId);

    Integer deleteByApplyId(@Param("applyId") Serializable applyId);
}
