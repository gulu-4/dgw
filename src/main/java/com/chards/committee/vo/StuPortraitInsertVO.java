package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Data
public class StuPortraitInsertVO {
    @NotNull(message = "学生学号不能为空")
    private String stuNum;
    @Digits(fraction = 1, integer = 5, message = "画像分类数值只能在1-5")
    private int kind;
    @NotNull(message = "画像内容不能为空")
    private String content;
}
