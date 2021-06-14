package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @Author: chards
 * @Date: 18:38 2020/8/26
 */
@Data
public class LeaceStatusUpdateVO {
    @NotNull(message = "请假记录id不能为空")
    private Long id;
    @Digits(fraction = 0, integer = 2, message = "审核状态只能在0.1.2.3")
    private int status;
}
