package com.chards.committee.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 远 chards_
 * @FileName:UserPwdVO
 * @date: 2020-07-26 20:14
 */
@Data
public class UserPwdVO {

	@NotNull(message = "原密码不能为空")
	private String oldpwd;
	@NotNull(message = "重置密码不能为空")
	private String newpwd;

}
