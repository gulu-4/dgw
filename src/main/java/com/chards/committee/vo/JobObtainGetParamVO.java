package com.chards.committee.vo;

import lombok.Data;

/**
 * @author LiuSu
 * @create 2021/6/12 21:35
 */
@Data
public class JobObtainGetParamVO {
    // 学院，年级，开始时间，结束时间，就业状况，
    private String department;
    private String grade;
    private String state;
    // 开始时间
    private String startTime;

    // 结束时间
    private String endTime;
}
