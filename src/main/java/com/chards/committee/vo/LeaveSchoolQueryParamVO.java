package com.chards.committee.vo;

import com.chards.committee.dto.AdminWorkDTO;
import lombok.Data;

/**
 * @author LiuSu
 * @create 2021/1/12 13:32
 * @Param 审核状态、学院、年级、学号、起止时间（离校日期的）
 */
@Data
public class LeaveSchoolQueryParamVO {
    private AdminWorkDTO adminWorkDTO;
    private Integer status; // 审核状态

    private String stuNum; //学号

    private String department;  // 学院

    private String grade; // 年级

    private String beginDate; //开始时间

    private String endDate; //结束时间
}
