package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author LiuSu
 * @create 2021/7/24 20:56
 */
@Data
public class ProvideMoneyPassVO {
    // 申请表的id
    @NotNull(message = "申请表id不能为空")
    private Long id;
    @Digits(fraction = 0, integer = 2, message = "审核状态只能在0,1,-1")
    private int firstCheck;
    @Digits(fraction = 0, integer = 2, message = "审核状态只能在0,1,-1")
    private int secondCheck;

    private LocalDateTime firstCheckTime;
    private String firstReviewer;
    private LocalDateTime secondReviewTime;
    private String secondReviewer;
}
