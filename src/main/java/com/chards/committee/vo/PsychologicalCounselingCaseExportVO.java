package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.chards.committee.domain.PsychologicalTestRecord;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * @author LiuSu
 * @create 2020/12/11 15:16
 */
@Data
public class PsychologicalCounselingCaseExportVO {

    @ExcelProperty("学号")
    private String stuNum;

    //    学生基本信息
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("年级")
    private String grade;

    @ExcelProperty("学院")
    private String department;

    @ExcelProperty("班级")
    private String classes;

    @ExcelProperty("电话")
    private String phone;

    @ExcelProperty("咨询时间")
    private Date counsellingTime;  //咨询记录  前端传递

    @ExcelProperty("基本判断")
    private String basicJudgment;

    @ExcelProperty("问题类别")
    private String type;

    @ExcelProperty("转介情况")
    private String referral;

    @ExcelProperty("是否已有诊断")
    private String hasDiagnosis;

    @ExcelProperty("诊断结果及医嘱")
    private String diagnosisAndAdvice;

    @ExcelProperty("咨询师")
    private String counselor;   // 咨询师

    @ExcelProperty("关注级别")
    private String attentionLevel;

    @ExcelProperty("是否第一次咨询")
    private String isFirstTime;

    @ExcelProperty("是否结案")
    private String isFinished;

    @ExcelProperty("咨询结果")
    private String result;

    @ExcelProperty("记录者")
    private String recorder;  // 填写者，工号

    @ExcelProperty("记录时间")
    private Date recordedTime;

}