package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/7/24 18:50
 */
@Data
public class ProvideMoneyGetDetailVO {

    @ExcelProperty("序号")
    private Long id;   //申请表id


    @ExcelProperty("学号")
    private String stuNum;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("身份证号")
    private String idCard;
    @ExcelProperty("年级")
    private String grade;
    @ExcelProperty("学院")
    private String department;
    @ExcelProperty("专业")
    private String major;
    @ExcelProperty("班级")
    private String classes;
    @ExcelProperty("联系电话")
    private String phone;
    @ExcelProperty("政治面貌")
    private String politicalStatus;
    @ExcelProperty("民族")
    private String national;

    @ExcelProperty("申请表类型")
    private String type;

    @ExcelProperty("期数")
    private String stage;

    @ExcelProperty("出生年月")
    private String birthMonth;

    @ExcelProperty("入学时间")
    private String enterSchoolTime;

    @ExcelProperty("学制")
    private String schoolSystem;

    @ExcelProperty("学习情况")
    private String learningSituation;

    @ExcelProperty("获奖情况")
    private String awards;

    @ExcelProperty("推荐理由")
    private String recommendReason;

    @ExcelProperty("家庭经济情况")
    private String familyEconomics;

    @ExcelProperty("家庭成员情况")
    private String familyMember;

    @ExcelProperty("申请理由")
    private String applyReason;

    @ExcelProperty("院系意见")
    private String departmentOpinion;

    @ExcelProperty("学校意见")
    private String schoolOpinion;

    @ExcelProperty("辅导员审核")
    private int firstCheck;

    @ExcelProperty("审核人")
    private String firstReviewer;

    @ExcelProperty("辅导员审核时间")
    private LocalDateTime firstCheckTime;

    @ExcelProperty("学工处审核")
    private int secondCheck;

    @ExcelProperty("学工审核人")
    private String secondReviewer;

    @ExcelProperty("学工审核时间")
    private LocalDateTime secondReviewTime;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

}
