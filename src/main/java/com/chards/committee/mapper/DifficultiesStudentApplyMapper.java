package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.DifficultiesStudentApply;
import com.chards.committee.util.DataScope;
import com.chards.committee.vo.DifficultiesPassVO;
import com.chards.committee.vo.DifficultiesStudentGetParamVO;
import com.chards.committee.vo.DifficultiesStudentGetVO;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiuSu
 * @since 2021-05-19
 */
public interface DifficultiesStudentApplyMapper extends BaseMapper<DifficultiesStudentApply> {

    List<DifficultiesStudentApply> getListWithParam(@Param("param")DifficultiesStudentGetParamVO difficultiesStudentGetParamVO);

    @DataScope()
    Page<DifficultiesStudentGetVO> getFirstList(@Param("param") DifficultiesStudentGetParamVO difficultiesStudentGetParamVO,
                                                 @Param("page") Page<DifficultiesStudentGetVO> page);
    @DataScope()
    Page<DifficultiesStudentGetVO> getSecondList(@Param("param") DifficultiesStudentGetParamVO difficultiesStudentGetParamVO,
                                                @Param("page") Page<DifficultiesStudentGetVO> page);

    Integer check(@Param("param") DifficultiesPassVO difficultiesPassVO);
}
