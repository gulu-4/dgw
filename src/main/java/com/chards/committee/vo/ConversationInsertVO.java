package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConversationInsertVO {
    @NotBlank(message = "学生学号不能为空")
    private String stuNum;
    @NotBlank(message = "谈话内容不能为空")
    private String content;
    private String traits;
}
