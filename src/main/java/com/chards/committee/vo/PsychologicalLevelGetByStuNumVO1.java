package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author LiuSu
 * @create 2020/12/14 17:11
 */
@Data
public class PsychologicalLevelGetByStuNumVO1 {

    private Long id;

    @ExcelProperty("学号")
    private String stuNum;  // 学号

    @ExcelProperty("学院")
    private String department;  // 学院

    @ExcelProperty("班级")
    private String classes;   // 班级

    @ExcelProperty("姓名")
    private String name;  // 姓名

    @ExcelProperty("性别")
    private String gender;  // 性别

    @ExcelProperty("学业问题")
    private String learningProblem;  // 学业问题
    @ExcelProperty("经济问题")
    private String economicProblem;  // 经济问题
    @ExcelProperty("行为异常")
    private String abnormalBehaviorProblem;  // 行为异常
    @ExcelProperty("生活问题")
    private String lifeEventProblem;  // 生活问题
    @ExcelProperty("个性心理")
    private String personalityProblem;  // 个性心理

    @ExcelProperty("疫情心理")
    private String epidemicPsychologicalProblem;  // 疫情心理

    @ExcelProperty("当前级别")
    private String level;   // 当前级别

    @ExcelProperty("记录人")
    private String recorder;

    @ExcelProperty("审核人")
    private String reviewer;

    @ExcelProperty("初定级别时间")
    private Date initTime;   // 初定级别时间
    @ExcelProperty("初定级别")
    private String initLevel;  // 初定级别
    @ExcelProperty("分级依据")
    private String basis;  // 分级依据
    @ExcelProperty("备注")
    private String remark;  // 备注
    @ExcelProperty("当前级别定级时间")
    private Date recordedTime; // 当前级别定级时间

}
