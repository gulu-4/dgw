package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.mapper.PsychologicalCounsellingCaseMapper;
import com.chards.committee.mapper.PsychologicalTestRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsychologicalTestRecordService extends ServiceImpl<PsychologicalCounsellingCaseMapper, PsychologicalCounsellingCase> {

    @Autowired
    private PsychologicalTestRecordMapper psychologicalTestRecordMapper;

}
