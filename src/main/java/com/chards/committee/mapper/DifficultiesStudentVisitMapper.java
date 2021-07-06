package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chards.committee.domain.DifficultiesStudentVisit;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.DifficultiesStudentVisitGetParamVO;
import com.chards.committee.vo.DifficultiesStudentVisitGetVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-07-06
 */
public interface DifficultiesStudentVisitMapper extends BaseMapper<DifficultiesStudentVisit> {

    @DataScope()
    List<DifficultiesStudentVisitGetVO> getStudentsVisits(@Param(value = "param") DifficultiesStudentVisitGetParamVO difficultiesStudentVisitGetParamVO);
}
