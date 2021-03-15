package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TeachingStaffResumeCheckVO {
    @NotNull(message = "教师工号不能为空")
    private String staffId;
    @NotNull(message = "状态信息不能为空")
    private int checkStatus;
}
