package com.chards.committee.vo;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/5/21 19:24
 */
@Data
public class DifficultiesStudentGetVO {
    /**
     *     学号、姓名、班级、手机、国家扶贫库信息、特殊困难情况（申请表里有字段）、
     *     每月生活支出、申请年份、量化评估分、可信度预判、上期建档、级别选择（A档、B档、C档、无）、
     *     量化评估问卷详情、贫困认定表详情、上报情况（未上报、审核中、已通过）
     *
     *     学院、学号、姓名、班级、手机、建档等级、量化评估分、可信度预判、
     *     上期建档、量化评估问卷详情、贫困认定表详情、操作（查看、通过、拒绝）
     */
    //id 修改、删除、更新有用
    private Long id;
    private Integer stage; //期数 申请年份
    private String stuNum; //学号
    private String stuName;  //姓名
    private String department;  //学院
    private String classes;  // 班级
    private String phone;  // 手机
    private Boolean fileAndRegister; //特殊困难情况1
    private Boolean minimumLivingSecurity;  // 特殊困难情况2
    private Boolean extremelyPoor;  // 特殊困难情况3
    private Boolean orphanAndDisabled;  //特殊困难情况4
    private Boolean martyr; // 特殊困难情况5
    private Boolean other;  // 特殊困难情况6
    private String fromImport;  // 国家扶贫库信息
    private String level;   // 级别选择

    private String lastLevel;  //上期建档级别
    private Long lastId;  //上期建档id  用于查看详情获取详细信息用

    private LocalDateTime firstReviewTime;
    private String firstReviewer;
    private Integer firstCheck;   // 辅导员上报情况
    private LocalDateTime secondReviewTime;
    private String secondReviewer;
    private Integer secondCheck;  //学工处审核情况

    private String field21; //每月生活支出
    private String score;  // 量化评估分
    private String fakeScore;  // 测谎分 可信度评判

    private String address;
    private String postalCode;
    private String parentPhone;
    private String familyMember;
    private String gender;
    private String birthday;
    private String school;
    private String major;
    private String grade;
    private String originLocation;
    private String idCard;
    private Integer familyNum;
    private String influenceInfo;
    private String personalContent;
    private String sign;
}
