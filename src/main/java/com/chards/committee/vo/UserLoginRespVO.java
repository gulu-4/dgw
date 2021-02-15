package com.chards.committee.vo;

import com.chards.committee.dto.UserDataScope;

import lombok.Data;

import java.util.List;

/**
 * @author 远 chards_
 * @FileName:UserLoginRespVO
 * @date: 2020-08-20 17:24
 */
@Data
public class UserLoginRespVO {
	private String token;
	private String name;
	/*角色列表，在整个权限管理没完善之前，这些角色就是权限管理的全部根据*/
	private List<String> roleList;
	/*分管学生*/
	private List<UserDataScope> userDataScopeList;
	private String oldIp;
	private String newIp;
}
