package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @author 远 chards_
 * @FileName:LeaveBackPassVO
 * @date: 2020-09-25 21:04
 */
@Data
public class LeaveBackPassVO {
	@NotNull(message = "学生id不能为空")
	private String stuNum;
	@Digits(fraction = 0, integer = 2, message = "审核状态只能在0.1.2")
	private int pass;
}
