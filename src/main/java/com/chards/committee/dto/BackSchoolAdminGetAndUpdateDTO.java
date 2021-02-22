package com.chards.committee.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: chards
 * @Date: 22:11 2020/8/18
 */
@Data
public class BackSchoolAdminGetAndUpdateDTO {
    private AdminWorkDTO adminWorkDTO;
    private List<UserDataScope> userDataScopeList;
    private Integer pass;

    private String stuNum; //学号

    private String department;  // 学院

    private String grade; // 年级

    private String beginDate; //开始时间

    private String endDate; //结束时间

    //    增加审核时间
    private LocalDateTime reviewedTime;
    //    增加审核人
    private String reviewedBy;

    private String province; // 出发地省份，在loc里进行模糊搜索
}
