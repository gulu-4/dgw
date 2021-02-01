package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.domain.PsychologicalLevel;
import com.chards.committee.vo.PsychologicalCounselingCaseDetailVO;
import com.chards.committee.vo.PsychologicalCounselingCaseExportVO;
import com.chards.committee.vo.PsychologicalCounsellingCaseAndTestRecordGetAllVO;
import com.chards.committee.vo.PsychologicalLevelCheckSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiuSu
 * @create 2020/12/11 16:56
 */
public interface PsychologicalCounsellingCaseMapper extends BaseMapper<PsychologicalCounsellingCase> {
//    Page<PsychologicalCounsellingCaseAndTestRecordGetAllVO> getAllByStuNum(@Param("page") Page<PsychologicalCounsellingCaseAndTestRecordGetAllVO> page,
//                                                      @Param("stuNum") String stuNum,
//                                                      @Param("startTime") String startTime,
//                                                      @Param("endTime") String endTime);

    /**
     * 通过学号、起止时间查询所有心理咨询记录
     * @param page 分页
     * @param stuNum 学号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    Page<PsychologicalCounselingCaseDetailVO> getAllCounselingCaseByParams(@Param("page") Page<PsychologicalCounselingCaseDetailVO> page,
                                                                           @Param("stuNum") String stuNum,
                                                                           @Param("startTime") String startTime,
                                                                           @Param("endTime") String endTime);

    List<PsychologicalCounselingCaseDetailVO> getAllCounselingCaseByParams1(@Param("stuNum") String stuNum,
                                                                            @Param("startTime") String startTime,
                                                                            @Param("endTime") String endTime);

    /**
     * 通过学号查询学生信息
     * @param stuNum 学号
     * @return
     */
    List<PsychologicalCounsellingCase> getAllByStuNum(@Param("stuNum") String stuNum);
}
