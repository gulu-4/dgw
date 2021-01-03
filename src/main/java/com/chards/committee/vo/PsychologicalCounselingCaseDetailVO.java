package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chards.committee.domain.PsychologicalCounsellingCase;
import com.chards.committee.domain.PsychologicalTestRecord;
import com.chards.committee.domain.StuInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * @author LiuSu
 * @create 2020/12/11 15:16
 */
@Data
public class PsychologicalCounselingCaseDetailVO {

    private String stuNum;

    private Date counsellingTime;  //咨询记录  前端传递

    private String basicJudgment;

    private String type;

    private String referral;

    private Boolean hasDiagnosis;

    private String diagnosisAndAdvice;

    private String counselor;   // 咨询师

    private String attentionLevel;

    private Boolean isFirstTime;

    private Boolean isFinished;

    private String result;

    private String recorder;  // 填写者，工号

    private CoreAdminBasicVO recorders; // 填写者，工号和姓名

    private LocalDateTime recordedTime;

    //    学生基本信息
    private String name;

    private String gender;

    private String grade;

    private String department;

    private String classes;

    private String phone;

//    private StuInfo stuInfo;

//    学生心理测评记录
    private List<PsychologicalTestRecord> psychologicalTestRecordList;
}