package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 通过学号、起止时间来查询、筛选所有咨询记录的参数接收VO
 * @author LiuSu
 * @create 2020/12/25 10:20
 */
@Data
public class PsychologicalCounsellingCaseSelectVO {
//    @NotBlank(message = "学生学号不能为空")
    private String stuNum;

    private String counselor;

    private Date startTime;

    private Date endTime;
}
