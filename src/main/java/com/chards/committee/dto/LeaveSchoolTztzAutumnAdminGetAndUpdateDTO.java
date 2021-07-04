package com.chards.committee.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LiuSu
 * @create 2021/1/4 16:13
 */
@Data
public class LeaveSchoolTztzAutumnAdminGetAndUpdateDTO {
//    记录用户权限范围，以提供给Mapper层做查询
    private List<UserDataScope> userDataScopeList;
    private Integer pass;

//    增加审核时间
    private LocalDateTime reviewedTime;
//    增加审核人
    private String reviewedBy;
}
