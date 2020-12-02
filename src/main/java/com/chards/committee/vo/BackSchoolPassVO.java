package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @Author: chards
 * @Date: 22:47 2020/8/18
 */
@Data
public class BackSchoolPassVO {
    @NotNull(message = "学生id不能为空")
    private String stuNum;
    @Digits(fraction = 0, integer = 2, message = "审核状态只能在0.1.2")
    private int pass;
}
