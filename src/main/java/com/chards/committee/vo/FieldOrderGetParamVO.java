package com.chards.committee.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/3/20 13:18
 */
@Data
public class FieldOrderGetParamVO {
    // 主键id
    private Long id;

    // 租借场地id
    private Long rentId;

    // 学生学号
    private String stuNumber;

    // 审核状态
    private Integer status;

    // 开始时间
    private LocalDateTime startTime;

    // 结束时间
    private LocalDateTime endTime;

    // 管理员工号或者学号
    private String manager;
}
