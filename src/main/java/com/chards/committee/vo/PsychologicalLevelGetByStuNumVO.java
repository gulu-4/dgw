package com.chards.committee.vo;

import com.chards.committee.domain.PsychologicalInvention;
import lombok.Data;

import java.util.List;
import java.util.Date;

/**
 * @author LiuSu
 * @create 2020/12/14 17:11
 */
@Data
public class PsychologicalLevelGetByStuNumVO {

    private Long id;

    private String stuNum;  // 学号

    private String department;  // 学院

    private String classes;   // 班级

    private String name;  // 姓名

    private String gender;  // 性别

    private String learningProblem;  // 学业问题

    private String economicProblem;  // 经济问题

    private String abnormalBehaviorProblem;  // 行为异常

    private String lifeEventProblem;  // 生活问题

    private String personalityProblem;  // 个性心理

    private String epidemicPsychologicalProblem;  // 疫情心理

    private String level;   // 当前级别

    private Date updateTime;   // 当前级别时间

    private Date initTime;   // 初定级别时间

    private String initLevel;  // 初定级别

    private String basis;  // 分级依据

    private String remark;  // 备注

    private List<PsychologicalInvention> psychologicalInventionList;  // 学院干预情况

}
