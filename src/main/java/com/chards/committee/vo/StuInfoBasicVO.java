package com.chards.committee.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StuInfoBasicVO implements Serializable {

    @TableId(value = "id")
    @ExcelProperty("学号")
    private String id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String gender;
    @ColumnWidth(15)
    @ExcelProperty("拼音")
    private String pinyin;
    @ExcelProperty("年级")
    private String grade;
    @ColumnWidth(20)
    @ExcelProperty("学院")
    private String department;
    @ColumnWidth(25)
    @ExcelProperty("专业")
    private String major;
    @ColumnWidth(30)
    @ExcelProperty("班级")
    private String classes;
    @ExcelProperty("民族")
    private String national;
    @ExcelProperty("学历")
    private String educationBackground;
    @ColumnWidth(20)
    @ExcelProperty("毕业学院")
    private String graduatedSchool;
    @ColumnWidth(25)
    @ExcelProperty("家庭住址")
    private String address;
    @ColumnWidth(13)
    @ExcelProperty("宿舍")
    private String dormitory;
    @ColumnWidth(25)
    @ExcelProperty("奖项")
    private String awards;
    @ExcelProperty("紧急联系人")
    private String emergencyContact;
    @ColumnWidth(13)
    @ExcelProperty("紧急联系电话")
    private String emergencyPhone;
    @ColumnWidth(25)
    @ExcelProperty("经济资助")
    private String aid;
    @ExcelProperty("辅导员姓名")
    private String counsellorName;
    @ExcelProperty("辅导员工号")
    private String counsellorNum;
    @ExcelIgnore
    private String counsellorPhone;
    @ExcelIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String priority;
    @ExcelProperty("政治面貌")
    private String politicalStatus;
    @ExcelProperty("生源地")
    private String originLocation;
    @ExcelProperty("学生在籍状态")
    private String state;
    @ExcelProperty("备注")
    private String note;
    @ExcelProperty("学生就读状态")
    private String statusOfCurrentStudents;

}
