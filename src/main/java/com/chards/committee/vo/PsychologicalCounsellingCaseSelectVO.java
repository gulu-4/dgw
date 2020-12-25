package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author LiuSu
 * @create 2020/12/25 10:20
 */
@Data
public class PsychologicalCounsellingCaseSelectVO {
    @NotBlank(message = "学生学号不能为空")
    private String stuNum;

    private Date startTime;

    private Date endTime;
}
