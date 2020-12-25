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

    private String name;  // 学生姓名

    private String gender; // 学生性别

    private String grade; // 年级

    private String department;   // 学院

    private String classes; //年级

    private String national; //民族

    private String phone; //手机号码

    private String awards; //奖项

    private String politicalStatus;  //身份

    private String originLocation; //省份

    private List<PsychologicalCounsellingCase> psychologicalCounsellingCaseList;

    private List<PsychologicalTestRecord> psychologicalTestRecordList;
}
