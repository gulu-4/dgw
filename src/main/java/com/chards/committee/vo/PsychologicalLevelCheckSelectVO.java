package com.chards.committee.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2020/12/25 11:26
 */
@Data
public class PsychologicalLevelCheckSelectVO {

    private Long id;

    private String stuNum;

    private String learningProblem;

    private String economicProblem;

    private String abnormalBehaviorProblem;

    private String lifeEventProblem;

    private String personalityProblem;

    private String epidemicPsychologicalProblem;

    private String level;

    private String initLevel;

    private String initTime;

    private String basis;

    private String remark;

    private String recorder;

    private CoreAdminBasicVO recorders;

    private LocalDateTime recordedTime;

    private Integer checkStatus;  // 0 -1 1

    private String reviewer;

    private CoreAdminBasicVO reviewers;

    private String instruction;

    private LocalDateTime checkTime;

    //新增字段判断是否是第一次进行关爱
    private Boolean isFirstCare;

    /**
     * 下面是学生的基本信息
     */
    private String department;  // 学院

    private String classes;   // 班级

    private String name;  // 姓名

    private String gender;  // 性别

    private String grade; //年级
}
