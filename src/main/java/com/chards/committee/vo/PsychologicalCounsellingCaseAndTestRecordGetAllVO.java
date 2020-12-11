package com.chards.committee.vo;

import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.domain.PsychologicalTestRecord;
import lombok.Data;

import java.util.List;


/**
 * @author LiuSu
 * @create 2020/12/11 15:16
 */
@Data
public class PsychologicalCounsellingCaseAndTestRecordGetAllVO {

    private String stuNum;  //学生学号

    private List<PsychologicalCounsellingCase> psychologicalCounsellingCaseList;

    private List<PsychologicalTestRecord> psychologicalTestRecordList;
}
