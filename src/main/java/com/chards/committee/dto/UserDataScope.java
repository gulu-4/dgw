package com.chards.committee.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 记录用户（管理员和具有管理员权限的学生）的权限范围
 */
@Data
@ToString
public class UserDataScope implements Serializable {
    private String department;
    private String educationBackground;
    private String grade;
    private String major;
    private String classes;
    private Integer isActive;
}
