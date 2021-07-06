package com.chards.committee.vo.stayInSchoolDuringTheHolidays;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StayInSchoolDuringTheHolidaysCheckVO {
    @NotNull(message = "学号不能为空")
    private String stuNum;
    @NotNull(message = "状态不能为空")
    private int pass;
}
