package com.chards.committee.vo;

import com.chards.committee.domain.ParentsInfo;
import com.chards.committee.domain.StuInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: chards
 * @Date: 20:56 2020/8/18
 */
@Data
public class StuInfoUpdateVO {
    @NotNull(message = "学生不能为空")
    private StuInfo stuInfo;
    @NotNull(message = "学生父母信息不能为空")
    private ParentsInfo parentsInfo;
}
