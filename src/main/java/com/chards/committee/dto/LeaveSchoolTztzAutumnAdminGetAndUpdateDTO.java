package com.chards.committee.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/1/4 16:13
 */
@Data
public class LeaveSchoolTztzAutumnAdminGetAndUpdateDTO {
    private AdminWorkDTO adminWorkDTO;
    private Integer pass;

//    增加审核时间
    private LocalDateTime reviewedTime;
//    增加审核人
    private String reviewedBy;
}
