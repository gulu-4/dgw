package com.chards.committee.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 远 chards_
 * @FileName:AdminPermissionDTO
 * @date: 2020-07-22 23:15
 *
 * 管理员权限
 */
@Data
public class AdminPermissionDTO implements Serializable {

	private Long id;
	private String name;
	private String permission;

}
