package com.chards.committee.dto;

import lombok.Data;

import java.util.List;

/**
 * 只能依赖不能继承
 */

@Data
public final class AdminWorkDTO {
    //身份  1为root 其他都不是
    private int identity;
    //记录存储 所选年级
    private List<String> works;
    //学院
    private String department;
}
