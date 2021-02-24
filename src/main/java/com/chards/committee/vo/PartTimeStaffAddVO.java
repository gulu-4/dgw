package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiuSu
 * @create 2021/2/24 22:05
 */
@Data
public class PartTimeStaffAddVO {

    @NotBlank(message = "兼职教职工学号不能为空")
    private String staffId;

    // 角色id，可以为空，默认设置为四
    private Long roleId;
}
