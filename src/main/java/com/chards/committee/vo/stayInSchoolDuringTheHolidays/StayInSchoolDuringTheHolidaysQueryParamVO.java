package com.chards.committee.vo.stayInSchoolDuringTheHolidays;

import lombok.Data;

@Data
public class StayInSchoolDuringTheHolidaysQueryParamVO {
    private Integer pass; // 审核状态，0未审，1拒绝，2批准

    private String stuNum; //学号

    private String department;  // 学院

    private String grade; // 年级

    private String startDate; //开始时间

    private String endDate; //结束时间
}