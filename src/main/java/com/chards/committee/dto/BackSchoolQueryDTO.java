package com.chards.committee.dto;

import lombok.Data;

@Data
public class BackSchoolQueryDTO {

    private String stuNum; //学号

    private String department;  // 学院

    private String grade; // 年级

    private String beginDate; // 开始时间

    private String endDate; // 结束时间

    private Integer pass; // 状态

    private String province; // 出发地省份，在loc里进行模糊搜索
}
