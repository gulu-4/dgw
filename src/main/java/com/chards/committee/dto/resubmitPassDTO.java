package com.chards.committee.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 重新·提交返校申请（状态）
 *
 * @author 远 chards_
 * @FileName:resubmitPassDTO
 * @date: 2020-08-18 13:23
 */
@Data
public class resubmitPassDTO implements Serializable {
	@NotBlank(message = "学号不能为空")
	private String stuNum;

	private int pass;

}
