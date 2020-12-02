package com.chards.committee.vo;

import lombok.Data;

/**
 * @Author: chards
 * @Date: 0:56 2020/8/20
 */
@Data
public class StuInfoSeniorVO {
    // 学院
    private String department = "";
    // 年级
    private String grade = "";
    // 辅导员Id
    private String counsellorNum = "";
    // 班级
    private String classes = "";
    // 名族
    private String national = "";
    //  学历
    private String educationBackground = "";
    //  宿舍
    private String dormitory = "";
}
