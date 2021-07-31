package com.chards.committee.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
@HeadRowHeight(30)
@ContentRowHeight(20)
@ColumnWidth(10)
public class ComprehensiveAssessment implements Serializable {

    @TableField(value = "id")
    @ExcelProperty(value = {"序号"})
    private Long id;

    @TableId(value = "stu_num")
    @ExcelProperty(value = {"学号"})
    private String stuNum;

    @TableField(value = "stu_name")
    @ExcelProperty(value = {"姓名"})
    private String stuName;

    @TableField(value = "department")
    @ExcelProperty(value = {"学院"})
    @ColumnWidth(25)
    private String department;

    @NumberFormat("0.00")
    @TableField(value = "ideology_and_morality")
    @ExcelProperty(value = {"思想品德素质测评得分G1"})
    private Double ideologyAndMorality;

    @NumberFormat("0.00")
    @TableField(value = "course_learning")
    @ExcelProperty(value = {"课程学习素质测评得分G2"})
    private Double courseLearning;

    @NumberFormat("0.00")
    @TableField(value = "physical_and_mental")
    @ExcelProperty(value = {"身心素质测评得分G3"})
    private Double physicalAndMental;

    @NumberFormat("0.00")
    @TableField(value = "basic_quality_score")
    @ExcelProperty(value = {"基本素质测评得分F1"})
    private Double basicQualityScore;

    @TableField(value = "basic_quality_level")
    @ExcelProperty(value = {"基本素质测评等级"})
    private String basicQualityLevel;

    @NumberFormat("0.00")
    @TableField(value = "learning_promotion")
    @ExcelProperty(value = {"学习提升素质加分G4"})
    private Double learningPromotion;

    @NumberFormat("0.00")
    @TableField(value = "innovative_undertaking")
    @ExcelProperty(value = {"创新创业素质加分G5"})
    private Double innovativeUndertaking;

    @NumberFormat("0.00")
    @TableField(value = "social_work")
    @ExcelProperty(value = {"社会工作素质加分G6"})
    private Double socialWork;

    @NumberFormat("0.00")
    @TableField(value = "social_practice")
    @ExcelProperty(value = {"社会实践能力加分G7"})
    private Double socialPractice;

    @NumberFormat("0.00")
    @TableField(value = "volunteer_service")
    @ExcelProperty(value = {"志愿服务素质加分G8"})
    private Double volunteerService;

    @NumberFormat("0.00")
    @TableField(value = "culture_and_art")
    @ExcelProperty(value = {"文化艺术体育素质加分G9"})
    private Double cultureAndArt;

    @NumberFormat("0.00")
    @TableField(value = "excellent_class")
    @ExcelProperty(value = {"优良学风班创建评选加分G10"})
    private Double excellentClass;

    @NumberFormat("0.00")
    @TableField(value = "good_dormitory")
    @ExcelProperty(value = {"星级宿舍创建评选加分G11"})
    private Double goodDormitory;

    @NumberFormat("0.00")
    @TableField(value = "development_evaluation")
    @ExcelProperty(value = {"发展素质测评得分F2"})
    private Double developmentEvaluation;

    @TableField(value = "create_time")
    @ExcelIgnore
    private String createTime;
}
