package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class PsychologicalCounsellingCaseInsertVO {

    @NotBlank(message = "学生学号不能为空")
    private String stuNum;

    private Date counsellingTime;  //咨询记录  前端传递

    @NotBlank(message = "基本判断不能为空")
    private String basicJudgment;

    private String type;

    private String referral;

    private Boolean hasDiagnosis;

    private String diagnosisAndAdvice;

    @NotBlank(message = "咨询师姓名不能为空")
    private String counselor;   // 咨询师

    @NotBlank(message = "关注级别不能为空")
    private String attentionLevel;

    private Boolean isFirstTime;

    private Boolean isFinished;

    @NotBlank(message = "咨询结果不能为空")
    private String result;

}
