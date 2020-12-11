package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.mapper.PsychologicalCounsellingCaseMapper;
import com.chards.committee.mapper.PsychologicalTestRecordMapper;
import com.chards.committee.vo.PsychologicalCounsellingCaseAndTestRecordGetAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PsychologicalCounsellingCaseService extends ServiceImpl<PsychologicalCounsellingCaseMapper, PsychologicalCounsellingCase> {

    @Autowired
    private PsychologicalCounsellingCaseMapper psychologicalCounsellingCaseMapper;

    @Autowired
    private PsychologicalTestRecordMapper psychologicalTestRecordMapper;

    /**
     * 获取所有咨询记录和测试记录  学生学号
     */
    public PsychologicalCounsellingCaseAndTestRecordGetAllVO getAll(String stuNum) {
        PsychologicalCounsellingCaseAndTestRecordGetAllVO psychologicalAllVO = new PsychologicalCounsellingCaseAndTestRecordGetAllVO();

        // 通过学号获取咨询记录
        List<PsychologicalCounsellingCase> caseList = psychologicalCounsellingCaseMapper.getAllByStuNum(stuNum);

        // 通过学号获取测试记录
        List<PsychologicalTestRecord> recordList = psychologicalTestRecordMapper.getAllByStuNum(stuNum);

        psychologicalAllVO.setPsychologicalCounsellingCaseList(caseList);
        psychologicalAllVO.setPsychologicalTestRecordList(recordList);
        psychologicalAllVO.setStuNum(stuNum);
        return psychologicalAllVO;
    }

}
