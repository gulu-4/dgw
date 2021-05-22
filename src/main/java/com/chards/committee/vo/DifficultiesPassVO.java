package com.chards.committee.vo;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/5/21 20:06
 */
@Data
public class DifficultiesPassVO {
    // 申请表审核的id
    @NotNull(message = "申请信息id不能为空")
    private Long id;
    @Digits(fraction = 0, integer = 2, message = "审核状态只能在0,1,-1")
    private int firstCheck;
    @Digits(fraction = 0, integer = 2, message = "审核状态只能在0,1,-1")
    private int secondCheck;

    private String level;

    private String money;

    private LocalDateTime firstReviewTime;
    private String firstReviewer;
    private LocalDateTime secondReviewTime;
    private String secondReviewer;
}
