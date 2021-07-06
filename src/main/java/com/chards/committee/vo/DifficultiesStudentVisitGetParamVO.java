package com.chards.committee.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/7/6 19:01
 */
@Data
public class DifficultiesStudentVisitGetParamVO {

    private String department;  //"学生所属学院",
    private String stuName;     //学生姓名-模糊查询",
    private String stuNum;      //"学生学号-可以通过这里查某一个学生的所有走访记录"
    private LocalDateTime startTime;   //"走访时间-开始时间",
    private LocalDateTime endTime;     //"走访时间-结束时间"
}
