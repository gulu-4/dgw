package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/1/26 12:49
 */
@Data
public class JobObtainGetInfoVO {
    @ExcelIgnore
    private Long id;

    @ExcelProperty("学号")
    private String stuNum;

    // 返回学生基本信息
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("学院")
    private String department;
    @ExcelProperty("年级")
    private String grade;
    @ExcelProperty("班级")
    private String classes;
    @ExcelProperty("就业状况")
    private String state;
    @ExcelProperty("就业意向")
    private String intention;
    @ExcelProperty("就业进度")
    private String progress;
    @ExcelProperty("就业帮扶")
    private String helpType;

    //这里返回填写者名字
    @ExcelProperty("记录人")
    private String recorder;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("记录时间")
    private String recordedTime;
}
