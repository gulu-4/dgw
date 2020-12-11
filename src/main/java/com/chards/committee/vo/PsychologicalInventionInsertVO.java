package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author LiuSu
 * @create 2020/12/11 17:19
 */
@Data
public class PsychologicalInventionInsertVO {
    @NotBlank(message = "学生学号不能为空")
    private String stuNum;

    @NotBlank(message = "干预情况")
    private String invention;

    private Date inventionTime;
}
