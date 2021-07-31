package com.chards.committee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chards.committee.domain.ComprehensiveAssessment;

import java.util.List;


public interface ComprehensiveAssessmentMapper extends BaseMapper<ComprehensiveAssessment> {


    int importBatch(List<ComprehensiveAssessment> list);

    /*@DataScope
    int removeAll();*/

    /*@DataScope
    public int updateBacth(List<ComprehensiveAssessmentVO> list);*/

    //@DataScope
    Page<ComprehensiveAssessment> getInfoSeniorSearch(Page<ComprehensiveAssessment> page, String department, String create_time);


    //@DataScope
    List<ComprehensiveAssessment> exportAllInfo(String year);

    /*@DataScope
    public int deleteById(@Param("id")Long id); */
}
