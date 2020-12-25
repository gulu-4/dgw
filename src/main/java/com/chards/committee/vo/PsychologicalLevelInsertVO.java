package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LiuSu
 * @create 2020/12/11 19:01
 */
@Data
public class PsychologicalLevelInsertVO {
    @NotBlank(message = "学生学号不能为空")
    private String stuNum;

    private String learningProblem;

    private String economicProblem;

    private String abnormalBehaviorProblem;

    private String lifeEventProblem;

    private String personalityProblem;

    private String epidemicPsychologicalProblem;

    @NotBlank(message = "当前级别不能为空")
    private String level;

    @NotBlank(message = "初定级别不能为空")
    private String initLevel;

    @NotNull(message = "初定时间不能为空")
    private Date initTime;

    @NotBlank(message = "分级依据不能为空")
    private String basis;

    private String remark;
}
