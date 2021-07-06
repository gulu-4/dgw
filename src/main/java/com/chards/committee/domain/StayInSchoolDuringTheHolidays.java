package com.chards.committee.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("stay_in_school_during_the_holidays")
public class StayInSchoolDuringTheHolidays extends Model<StayInSchoolDuringTheHolidays> {
    private static final long serialVersionUID = -3708995319731660451L;
    private String stuNum;
    private Date startDate;
    private Date endDate;
    private String tel;
    private String emergencyCaller;
    private String emergencyTel;
    private String address;
    private String reason;
    private int pass; // 0未审，1拒绝，2通过
    private String checked_by;
    private LocalDateTime created_time;
    private LocalDateTime checked_time;
}
