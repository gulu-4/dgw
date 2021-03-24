package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author LiuSu
 * @create 2021/3/10 18:51
 */
@Data
public class AdminRoleVO {
    @NotBlank(message = "管理员工号或者学号不能为空")
    private String adminId;
    @NotBlank(message = "管理员角色id不能为空")
    private String roleIds;

    // 管理员名字
    private String name;
}
