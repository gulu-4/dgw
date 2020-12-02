package com.chards.committee.vo;

import lombok.Data;

/**
 * @author 远 chards_
 * @FileName:UserLoginRespVO
 * @date: 2020-08-20 17:24
 */
@Data
public class UserLoginRespVO {
	private String token;
	private String name;
	private String oldIp;
	private String newIp;
}
