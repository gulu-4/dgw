package com.chards.committee.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 远 chards_
 * @FileName:AdminRoleDTO
 * @date: 2020-07-22 23:04
 * 管理员角色
 */
@Data
public class AdminRoleDTO implements Serializable {
	//工号
	private String id;
	private String name;
	private String enname;
}
