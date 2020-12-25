package com.chards.committee.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.domain.StuInfo;
import com.chards.committee.mapper.PsychologicalCounsellingCaseMapper;
import com.chards.committee.mapper.PsychologicalTestRecordMapper;
import com.chards.committee.vo.PsychologicalCounsellingCaseAndTestRecordGetAllVO;
import com.chards.committee.vo.PsychologicalCounsellingCaseSelectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PsychologicalCounsellingCaseService extends ServiceImpl<PsychologicalCounsellingCaseMapper, PsychologicalCounsellingCase> {

    @Autowired
    private PsychologicalCounsellingCaseMapper psychologicalCounsellingCaseMapper;

    @Autowired
    private PsychologicalTestRecordMapper psychologicalTestRecordMapper;

    @Autowired
    private StuInfoService stuInfoService;

    /**
     * 获取所有咨询记录和测试记录  学生学号
     */
    public PsychologicalCounsellingCaseAndTestRecordGetAllVO getAll(PsychologicalCounsellingCaseSelectVO psychologicalCounsellingCaseSelectVO) {
        String stuNum = psychologicalCounsellingCaseSelectVO.getStuNum();
        PsychologicalCounsellingCaseAndTestRecordGetAllVO psychologicalAllVO = new PsychologicalCounsellingCaseAndTestRecordGetAllVO();

        // 获取学生相关信息
        StuInfo stuInfo = stuInfoService.getById(stuNum);
        BeanUtils.copyProperties(stuInfo,psychologicalAllVO);

        // 通过学号获取咨询记录
        String startTime = timeTransfer(psychologicalCounsellingCaseSelectVO.getStartTime());
        String endTime = timeTransfer(psychologicalCounsellingCaseSelectVO.getEndTime());
        List<PsychologicalCounsellingCase> caseList = psychologicalCounsellingCaseMapper.getAllByStuNum(stuNum,startTime,endTime);

        // 通过学号获取测试记录
        List<PsychologicalTestRecord> recordList = psychologicalTestRecordMapper.getAllByStuNum(stuNum);

        psychologicalAllVO.setPsychologicalCounsellingCaseList(caseList);
        psychologicalAllVO.setPsychologicalTestRecordList(recordList);
        return psychologicalAllVO;
    }

    // 时间转换
    public String timeTransfer(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (d != null){
            return formatter.format(d);
        }
        return null;
    }



}
