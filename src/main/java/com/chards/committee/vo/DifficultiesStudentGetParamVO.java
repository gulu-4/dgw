package com.chards.committee.vo;

import lombok.Data;

/**
 * @author LiuSu
 * @create 2021/5/20 17:25
 */
@Data
public class DifficultiesStudentGetParamVO {

    //学生学院
    private String department;

    //学生学号
    private String stuNum;

    //期数，年份
    private Integer stage;

    //评定等级
    private String level;

    //辅导员审核状态
    private Integer firstCheck;

    //学工处审核状态
    private Integer secondCheck;
}
