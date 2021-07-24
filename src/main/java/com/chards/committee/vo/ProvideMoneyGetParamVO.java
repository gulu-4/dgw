package com.chards.committee.vo;

import lombok.Data;

/**
 * @author LiuSu
 * @create 2021/7/24 18:26
 */
@Data
public class ProvideMoneyGetParamVO {
    //学生学院
    private String department;

    //学生学号
    private String stuNum;

    //期数，年份
    private String stage;

    //类型
    private String type;

    //辅导员审核状态
    private Integer firstCheck;

    //学工处审核状态
    private Integer secondCheck;

}
