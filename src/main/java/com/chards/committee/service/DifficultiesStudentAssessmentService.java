package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.DifficultiesStudentAssessment;
import com.chards.committee.mapper.DifficultiesStudentAssessmentMapper;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author LiuSu
 * @create 2021/5/19 22:37
 */
@Service("difficultiesStudentAssessmentService")
public class DifficultiesStudentAssessmentService extends ServiceImpl<DifficultiesStudentAssessmentMapper, DifficultiesStudentAssessment> {
    public DifficultiesStudentAssessment getByApplyId(Serializable applyId) {
        return baseMapper.getByApplyId(applyId);
    }

    public Integer deleteByApplyId(Serializable applyId){
        return baseMapper.deleteByApplyId(applyId);
    }
}
