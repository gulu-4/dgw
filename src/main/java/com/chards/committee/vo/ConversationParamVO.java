package com.chards.committee.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author LiuSu
 * @create 2021/4/12 19:49
 */
@Data
public class ConversationParamVO {

    private String department;  // 学院

    private Date startTime;  // 填写开始时间

    private Date endTime;   // 填写结束时间

    private String number;  // 填写人工号

    private String adminName;   // 填写人姓名

    private String traits;  // 类别

}
