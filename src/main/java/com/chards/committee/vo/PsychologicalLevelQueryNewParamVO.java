package com.chards.committee.vo;

import lombok.Data;

import java.util.ArrayList;


/**
 * @author LiuSu
 * @create 2020/12/26 10:25
 * 学院
 * 年级
 * 类别与线索
 * 级别
 */
@Data
public class PsychologicalLevelQueryNewParamVO {

    private String department;  // 学院

    private String grade; // 年级

    private Boolean learningProblem;   // 以下六个类别，只要由内容就可以作为筛选条件

    private Boolean economicProblem;

    private Boolean abnormalBehaviorProblem;

    private Boolean lifeEventProblem;

    private Boolean personalityProblem;

    private Boolean epidemicPsychologicalProblem;

    private ArrayList<String> clues;   //线索可以是多条,用逗号进行隔开，然后对齐进行循环，循环的时候六个类别都需要进行比对

    private String level;
}
