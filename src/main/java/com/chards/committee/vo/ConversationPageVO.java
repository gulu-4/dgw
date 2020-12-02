package com.chards.committee.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ConversationPageVO {
    private Long id;
    private String stuNum;
    private String content;
    private Date datetime;
    private String number;
    private String traits;
    private String adminName;
    private String stuName;
    private String stuGrade;
    private String stuEducationBackground;
}
